package com.nhbs.fenxiao.module.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.home.activity.presenter.HomeEventPresenter;
import com.nhbs.fenxiao.module.home.activity.presenter.HomeEventViewer;
import com.nhbs.fenxiao.module.home.bean.HomeFindActivtyListBean;
import com.yu.common.mvp.PresenterLifeCycle;


public class HomeEventActivity extends BaseBarActivity implements HomeEventViewer {

    @PresenterLifeCycle
    HomeEventPresenter mPresenter = new HomeEventPresenter(this);
    private RecyclerView rv_event;
    private int pageNum = 1;
    private int pageSize = 10;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_event_view);
    }

    @Override
    protected void loadData() {
        rv_event = bindView(R.id.rv_event);
        rv_event.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPresenter.getFindActivtyList(pageNum, pageSize);
    }

    @Override
    public void getFindActivtyListSuccess(HomeFindActivtyListBean homeFindActivtyListBean) {

    }
}
