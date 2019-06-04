package com.nhbs.fenxiao.module.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseFragment;
import com.nhbs.fenxiao.module.home.fragment.presenter.HomeFragmentPresenter;
import com.nhbs.fenxiao.module.home.fragment.presenter.HomeFragmentViewer;
import com.yu.common.mvp.PresenterLifeCycle;


public class HomeFragment extends BaseFragment implements HomeFragmentViewer {

    @PresenterLifeCycle
    HomeFragmentPresenter presenter = new HomeFragmentPresenter(this);

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home_view;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {

    }
}
