package com.nhbs.fenxiao.module.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.home.activity.presenter.HomeEventPresenter;
import com.nhbs.fenxiao.module.home.activity.presenter.HomeEventViewer;
import com.nhbs.fenxiao.module.home.adapter.MineHomeEventRvAdapter;
import com.nhbs.fenxiao.module.home.bean.HomeFindActivtyListBean;
import com.nhbs.fenxiao.module.view.SpaceVerticalItemDecoration;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.utils.DensityUtil;


public class HomeEventActivity extends BaseBarActivity implements HomeEventViewer {
    @PresenterLifeCycle
    HomeEventPresenter mPresenter = new HomeEventPresenter(this);
    private RecyclerView rv_event;
    private int pageNum = 1;
    private int pageSize = 10;
    private MineHomeEventRvAdapter adapter;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_event_view);
    }

    @Override
    protected void loadData() {
        setTitle("活动列表");
        rv_event = bindView(R.id.rv_event);
        rv_event.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_event.addItemDecoration(new SpaceVerticalItemDecoration(DensityUtil.dip2px(this, 8)));
        adapter = new MineHomeEventRvAdapter(R.layout.item_home_event, getActivity());
        rv_event.setAdapter(adapter);
        mPresenter.getFindActivtyList(pageNum, pageSize);
    }

    @Override
    public void getFindActivtyListSuccess(HomeFindActivtyListBean homeFindActivtyListBean) {
        if (homeFindActivtyListBean != null && homeFindActivtyListBean.rows != null && homeFindActivtyListBean.rows.size() != 0) {
            adapter.setNewData(homeFindActivtyListBean.rows);
            bindView(R.id.ll_empty, false);
            bindView(R.id.rv_event, true);
        } else {
            //空页面
            bindView(R.id.ll_empty, true);
            bindView(R.id.rv_event, false);
        }
    }
}
