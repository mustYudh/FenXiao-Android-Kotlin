package com.nhbs.fenxiao.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.mine.activity.presenter.MineWithdrawPresenter;
import com.nhbs.fenxiao.module.mine.activity.presenter.MineWithdrawViewer;
import com.nhbs.fenxiao.module.view.ClearEditText;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;


public class MineWithdrawActivity extends BaseBarActivity implements MineWithdrawViewer {

    @PresenterLifeCycle
    MineWithdrawPresenter mPresenter = new MineWithdrawPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_withdraw_view);
    }

    @Override
    protected void loadData() {
        setTitle("提现");
        Bundle bundle = getIntent().getExtras();
        int withdraw_type = bundle.getInt("WITHDRAW_TYPE");
        String withdraw_money = bundle.getString("WITHDRAW_MONEY");
        ClearEditText et_balance = bindView(R.id.et_balance);
        ClearEditText et_account = bindView(R.id.et_account);
        et_balance.setHint("余额¥" + withdraw_money);
        if (withdraw_type == 1) {
            //支付宝
            bindText(R.id.tv_type, "到账支付宝");
        } else {
            //微信
            bindText(R.id.tv_type, "到账微信");
        }
        bindView(R.id.tv_withdraw, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_account.getText().toString().trim())) {
                    ToastUtils.show("提现账号不能为空");
                    return;
                }

                if (TextUtils.isEmpty(et_balance.getText().toString().trim())) {
                    ToastUtils.show("提现金额不能为空");
                    return;
                }

                mPresenter.createUserWithdraw(et_balance.getText().toString().trim(), withdraw_type + "");
            }
        });
    }

    @Override
    public void createUserWithdrawSuccess() {
        ToastUtils.show("提现申请成功");
        Bundle bundle = new Bundle();
//        bundle.putString("");
        finish();
    }
}
