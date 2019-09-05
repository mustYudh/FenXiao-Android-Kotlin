package com.nhbs.fenxiao.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.mine.activity.presenter.MineTeamActivityPresenter;
import com.nhbs.fenxiao.module.mine.activity.presenter.MineTeamActivityViewer;
import com.nhbs.fenxiao.module.mine.adapter.MineTeamRvAdapter;
import com.nhbs.fenxiao.module.mine.bean.MineGroupBean;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;


public class MineTeamActivity extends BaseBarActivity implements MineTeamActivityViewer {

    @PresenterLifeCycle
    MineTeamActivityPresenter mPresenter = new MineTeamActivityPresenter(this);
    private RecyclerView rv_team;
    private List<String> list = new ArrayList<>();
    private int pageNum = 1;
    private int pageSize = 10;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_team_view);
    }

    @Override
    protected void loadData() {
        setTitle("我的团队");
        LinearLayout ll_team_one = bindView(R.id.ll_team_one);
        LinearLayout ll_team_two = bindView(R.id.ll_team_two);
        TextView tv_team_one = bindView(R.id.tv_team_one);
        TextView tv_team_two = bindView(R.id.tv_team_two);
        View view_one = bindView(R.id.view_one);
        View view_two = bindView(R.id.view_two);
        rv_team = bindView(R.id.rv_team);
        rv_team.setLayoutManager(new LinearLayoutManager(getActivity()));


        for (int i = 0; i < 10; i++) {
            list.add("");
        }

        rv_team.setAdapter(new MineTeamRvAdapter(R.layout.item_mine_team, list, getActivity()));

        View.OnClickListener onClickListener = view -> {
            switch (view.getId()) {
                case R.id.ll_team_one:
                    tv_team_one.setTextColor(getResources().getColor(R.color.app_red));
                    tv_team_two.setTextColor(getResources().getColor(R.color.app_ADB6C4));
                    view_one.setVisibility(View.VISIBLE);
                    view_two.setVisibility(View.INVISIBLE);
                    rv_team.setAdapter(new MineTeamRvAdapter(R.layout.item_mine_team, list, getActivity()));
                    break;
                case R.id.ll_team_two:
                    tv_team_one.setTextColor(getResources().getColor(R.color.app_ADB6C4));
                    tv_team_two.setTextColor(getResources().getColor(R.color.app_red));
                    view_one.setVisibility(View.INVISIBLE);
                    view_two.setVisibility(View.VISIBLE);
                    rv_team.setAdapter(new MineTeamRvAdapter(R.layout.item_mine_team_generalize, list, getActivity()));
                    break;
            }

        };
        ll_team_one.setOnClickListener(onClickListener);
        ll_team_two.setOnClickListener(onClickListener);

        mPresenter.mineGroup(pageNum + "", pageSize + "");
    }

    @Override
    public void mineGroupSuccess(MineGroupBean mineGroupBean) {

    }
}
