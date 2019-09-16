package com.nhbs.fenxiao.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.mine.activity.presenter.BindAlipayPresenter;
import com.nhbs.fenxiao.module.mine.activity.presenter.BindAlipayViewer;
import com.nhbs.fenxiao.module.view.ClearEditText;
import com.yu.common.countdown.RxCountDown;
import com.yu.common.countdown.RxCountDownAdapter;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.DelayClickTextView;


public class BindAliPayActivity extends BaseBarActivity implements BindAlipayViewer {

    @PresenterLifeCycle
    BindAlipayPresenter mPresenter = new BindAlipayPresenter(this);
    private DelayClickTextView tv_get_code;
    private RxCountDown countDown;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_bind_alipay_view);
    }

    @Override
    protected void loadData() {
        setTitle("绑定支付宝");
        countDown = new RxCountDown();
        ClearEditText et_ali = bindView(R.id.et_ali);
        ClearEditText et_phone = bindView(R.id.et_phone);
        ClearEditText et_code = bindView(R.id.et_code);
        tv_get_code = bindView(R.id.tv_get_code);
        tv_get_code.setOnClickListener(view -> {
            if (TextUtils.isEmpty(et_phone.getText())) {
                ToastUtils.show("手机号输入不能为空");
            } else if (!et_phone.getText().toString().startsWith("1") || et_phone.getText().toString().length() != 11) {
                ToastUtils.show("检查手机号输入是否正确");
            } else {
                tv_get_code.setClickable(false);
                mPresenter.sendVerCode(et_phone.getText().toString(), countDown, tv_get_code);
            }
        });

        bindView(R.id.tv_commit, view -> {
            if (TextUtils.isEmpty(et_ali.getText())) {
                ToastUtils.show("支付宝账号输入不能为空");
                return;
            }
            if (TextUtils.isEmpty(et_phone.getText())) {
                ToastUtils.show("手机号输入不能为空");
                return;
            }
            if (!et_phone.getText().toString().startsWith("1") || et_phone.getText().toString().length() != 11) {
                ToastUtils.show("检查手机号输入是否正确");
                return;
            }
            if (TextUtils.isEmpty(et_code.getText())) {
                ToastUtils.show("验证码不能为空");
                return;
            }

            mPresenter.boundAliAccount(et_phone.getText().toString(), et_code.getText().toString());
        });
    }

    @Override
    public void sendVerCodeSuccess() {
        countDown.start(60);
        countDown.setCountDownTimeListener(new RxCountDownAdapter() {

            @Override
            public void onStart() {
                super.onStart();
                tv_get_code.setClickable(false);
                tv_get_code.setText("60S");
            }

            @Override
            public void onNext(Integer time) {
                super.onNext(time);
                if (time == 0) {
                    tv_get_code.setClickable(true);
                    tv_get_code.setText("发送验证码");
                } else {
                    tv_get_code.setText(time + "S");
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                tv_get_code.setClickable(true);
                tv_get_code.setText("发送验证码");
            }

            @Override
            public void onComplete() {
                super.onComplete();
            }
        });
        ToastUtils.show("发送成功");
    }

    @Override
    public void boundAliAccountSuccess() {
        ToastUtils.show("绑定支付宝成功");
        setResult(1);
        finish();
    }
}
