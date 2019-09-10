package com.nhbs.fenxiao.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.mine.activity.presenter.MineWithdrawPresenter;
import com.nhbs.fenxiao.module.mine.activity.presenter.MineWithdrawViewer;
import com.nhbs.fenxiao.module.view.ClearEditText;
import com.yu.common.mvp.PresenterLifeCycle;


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

            }
        });
    }
}
