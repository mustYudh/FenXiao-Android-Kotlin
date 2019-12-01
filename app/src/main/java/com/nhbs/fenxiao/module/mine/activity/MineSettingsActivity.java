package com.nhbs.fenxiao.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.mine.activity.presenter.MineSettingsPresenter;
import com.nhbs.fenxiao.module.mine.activity.presenter.MineSettingsViewer;
import com.nhbs.fenxiao.module.mine.bean.UserAccountInfo;
import com.yu.common.mvp.PresenterLifeCycle;


public class MineSettingsActivity extends BaseBarActivity implements MineSettingsViewer {

    @PresenterLifeCycle
    MineSettingsPresenter mPresenter = new MineSettingsPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_settings_view);
    }

    @Override
    protected void loadData() {
        setTitle("设置");
        bindView(R.id.logout, v -> mPresenter.logout());
        mPresenter.getUserAccountInfo();
    }

    @Override
    public void getUserAccountInfoSuccess(UserAccountInfo accountInfo) {
//        if (accountInfo != null){
//            bindText(R.id.tv_mobile,accountInfo.userMobile);
//            if (accountInfo.aliStatus != null && accountInfo.aliStatus == )
//        }
    }
}
