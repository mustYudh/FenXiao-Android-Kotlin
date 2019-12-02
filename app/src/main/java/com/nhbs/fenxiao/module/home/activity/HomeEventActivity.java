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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.utils.DensityUtil;

import java.util.List;


public class HomeEventActivity extends BaseBarActivity implements HomeEventViewer {
    @PresenterLifeCycle
    HomeEventPresenter mPresenter = new HomeEventPresenter(this);
    private RecyclerView rv_event;
    private int pageNum = 1;
    private int pageSize = 1000;
    private MineHomeEventRvAdapter adapter;
    private SmartRefreshLayout refreshLayout;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_event_view);
    }

    @Override
    protected void loadData() {
        setTitle("活动列表");
        rv_event = bindView(R.id.rv_event);
        refreshLayout = bindView(R.id.refresh);
        rv_event.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_event.addItemDecoration(new SpaceVerticalItemDecoration(DensityUtil.dip2px(this, 8)));
        adapter = new MineHomeEventRvAdapter(R.layout.item_home_event, getActivity());
        rv_event.setAdapter(adapter);
        mPresenter.getFindActivtyList(pageNum, pageSize);
        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()).setSpinnerStyle(SpinnerStyle.Translate));
        refreshLayout.setEnableOverScrollBounce(false);
        refreshLayout.setEnableAutoLoadMore(true);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(refreshLayout12 -> {
            pageNum = 1;
            mPresenter.getFindActivtyList(pageNum, pageSize);
        });
    }

    @Override
    public void getFindActivtyListSuccess(HomeFindActivtyListBean homeFindActivtyListBean) {
        if (refreshLayout != null) {
            refreshLayout.finishRefresh();
        }
        if (homeFindActivtyListBean != null && homeFindActivtyListBean.rows != null && homeFindActivtyListBean.rows.size() != 0) {
            adapter.setNewData(homeFindActivtyListBean.rows);
            bindView(R.id.ll_empty, false);
            bindView(R.id.rv_event, true);
            adapter.setOnItemChildClickListener((adapter, view, position) -> {
                List<HomeFindActivtyListBean.RowsBean> data = adapter.getData();
                HomeFindActivtyListBean.RowsBean rowsBean = data.get(position);
                if (view.getId() == R.id.tv_do) {
                    if (rowsBean.isJoinStatus != null && rowsBean.isJoinStatus == 0) {
                        mPresenter.insertActivty(rowsBean.id, rowsBean);
                    }
                }
            });
        } else {
            //空页面
            bindView(R.id.ll_empty, true);
            bindView(R.id.rv_event, false);
        }
    }

    @Override
    public void getFindActivtyListFail() {
        if (refreshLayout != null) {
            refreshLayout.finishRefresh();
        }
    }

    @Override
    public void insertActivtySuccess(HomeFindActivtyListBean.RowsBean rowsBean) {
        ToastUtils.show("参加成功");
        rowsBean.isJoinStatus = 1;
        adapter.notifyDataSetChanged();
    }
}
