package com.nhbs.fenxiao.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.mine.activity.presenter.GeneralizePresenter;
import com.nhbs.fenxiao.module.mine.activity.presenter.GeneralizeViewer;
import com.nhbs.fenxiao.module.mine.adapter.MineGeneralizeRvAdapter;
import com.nhbs.fenxiao.module.mine.bean.MineSpreadLogsListBean;
import com.nhbs.fenxiao.module.view.SpaceVerticalItemDecoration;
import com.yu.common.mvp.PresenterLifeCycle;


public class MineGeneralizeActivity extends BaseBarActivity implements GeneralizeViewer {
    @PresenterLifeCycle
    GeneralizePresenter mPresenter = new GeneralizePresenter(this);
    private RecyclerView rv_list;
    private MineGeneralizeRvAdapter adapter;
    private int pageNum = 1;
    private int pageSize = 10;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_generalize_view);
    }

    @Override
    protected void loadData() {
        setTitle("推广记录");

        rv_list = bindView(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_list.addItemDecoration(new SpaceVerticalItemDecoration(10));
        adapter = new MineGeneralizeRvAdapter(R.layout.item_mine_generalize, getActivity());
        rv_list.setAdapter(adapter);
        mPresenter.querySpreadLogsList(pageNum + "", pageSize + "");
    }

    @Override
    public void querySpreadLogsListSuccess(MineSpreadLogsListBean spreadLogsListBean) {
        if (spreadLogsListBean != null && spreadLogsListBean.rows != null && spreadLogsListBean.rows.size() != 0) {
            adapter.setNewData(spreadLogsListBean.rows);
            bindView(R.id.ll_empty, false);
            bindView(R.id.rv_list, true);
        } else {
            //空页面
            bindView(R.id.ll_empty, true);
            bindView(R.id.rv_list, false);
        }
    }
}
