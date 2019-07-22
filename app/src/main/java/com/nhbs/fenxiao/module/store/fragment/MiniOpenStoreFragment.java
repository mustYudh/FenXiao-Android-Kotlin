package com.nhbs.fenxiao.module.store.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarFragment;
import com.nhbs.fenxiao.data.UserProfile;
import com.nhbs.fenxiao.module.store.fragment.presenter.MiniOpenStoreFragmentPresenter;
import com.nhbs.fenxiao.module.store.fragment.presenter.MiniOpenStoreFragmentViewer;
import com.nhbs.fenxiao.module.web.WebViewActivity;
import com.yu.common.mvp.PresenterLifeCycle;


public class MiniOpenStoreFragment extends BaseBarFragment implements MiniOpenStoreFragmentViewer {

    @PresenterLifeCycle
    MiniOpenStoreFragmentPresenter mPresenter = new MiniOpenStoreFragmentPresenter(this);

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_mini_open_store_view;
    }

    @Override
    public boolean isImmersionBar() {
        return true;
    }

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.action_bar_page_fragment_mini_store_layout;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }


    @Override
    protected void loadData() {
        bindView(R.id.tv_open_store, view -> {
            //调转开店webview
            getLaunchHelper().startActivity(WebViewActivity.callIntent(getActivity(), "", "http://139.180.218.55:8080/openstore?token=" + UserProfile.getInstance().getAppToken()));
            Log.e("token",UserProfile.getInstance().getAppToken());
        });
    }
}
