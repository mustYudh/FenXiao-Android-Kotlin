package com.nhbs.fenxiao.module.mine.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseFragment;
import com.nhbs.fenxiao.module.mine.activity.MineTeamActivity;
import com.nhbs.fenxiao.module.mine.adapter.MineTeamRvAdapter;
import com.nhbs.fenxiao.module.mine.bean.MineGroupBean;
import com.nhbs.fenxiao.module.mine.fragment.presenter.MineTeamPresenter;
import com.nhbs.fenxiao.module.mine.fragment.presenter.MineTeamViewer;
import com.yu.common.mvp.PresenterLifeCycle;


public class MineTeamFragment extends BaseFragment implements MineTeamViewer {

    @PresenterLifeCycle
    MineTeamPresenter mPresenter = new MineTeamPresenter(this);
    private RecyclerView rv_team;
    private int pageNum = 1;
    private int pageSize = 10;
    private MineTeamRvAdapter adapter;
    private MineTeamActivity activity;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_team_view;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }


    @Override
    protected void loadData() {
        activity = (MineTeamActivity) getActivity();
        rv_team = bindView(R.id.rv_team);
        rv_team.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MineTeamRvAdapter(R.layout.item_mine_team, getActivity());
        rv_team.setAdapter(adapter);

        mPresenter.mineGroup(pageNum + "", pageSize + "");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void mineGroupSuccess(MineGroupBean mineGroupBean) {
        if (mineGroupBean != null && mineGroupBean.userGroupTos != null && mineGroupBean.userGroupTos.size() != 0) {
            adapter.setNewData(mineGroupBean.userGroupTos);
            if (activity != null) {
                activity.tv_team_num.setText(mineGroupBean.divideMoney + "");
            }
            bindView(R.id.ll_empty, false);
            bindView(R.id.rv_team, true);
        } else {
            //空页面
            bindView(R.id.ll_empty, true);
            bindView(R.id.rv_team, false);
        }
    }
}
