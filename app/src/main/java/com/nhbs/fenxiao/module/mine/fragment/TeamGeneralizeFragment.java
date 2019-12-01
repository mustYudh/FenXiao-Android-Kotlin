package com.nhbs.fenxiao.module.mine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseFragment;
import com.nhbs.fenxiao.module.mine.adapter.MineTeamGeneralizeRvAdapter;
import com.nhbs.fenxiao.module.mine.bean.MineSpreadLogsListBean;
import com.nhbs.fenxiao.module.mine.fragment.presenter.TeamGeneralizePresenter;
import com.nhbs.fenxiao.module.mine.fragment.presenter.TeamGeneralizeViewer;
import com.yu.common.mvp.PresenterLifeCycle;


public class TeamGeneralizeFragment extends BaseFragment implements TeamGeneralizeViewer {

    @PresenterLifeCycle
    TeamGeneralizePresenter mPresenter = new TeamGeneralizePresenter(this);
    private RecyclerView rv_team;
    private int pageNum = 1;
    private int pageSize = 10;
    private MineTeamGeneralizeRvAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_team_generalize_view;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        rv_team = bindView(R.id.rv_team);
        rv_team.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MineTeamGeneralizeRvAdapter(R.layout.item_mine_team_generalize, getActivity());
        rv_team.setAdapter(adapter);

        mPresenter.querySpreadLogsList(pageNum + "", pageSize + "");
    }

    @Override
    public void querySpreadLogsListSuccess(MineSpreadLogsListBean spreadLogsListBean) {
        if (spreadLogsListBean != null && spreadLogsListBean.rows != null && spreadLogsListBean.rows.size() != 0) {
            bindView(R.id.ll_empty, false);
            bindView(R.id.rv_list, true);
            adapter.setNewData(spreadLogsListBean.rows);
        } else {
            //空页面
            bindView(R.id.ll_empty, true);
            bindView(R.id.rv_list, false);
        }
    }
}
