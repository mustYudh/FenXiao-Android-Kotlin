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
import com.nhbs.fenxiao.module.view.SpaceVerticalItemDecoration;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;


public class MineGeneralizeActivity extends BaseBarActivity implements GeneralizeViewer {
    private List<String> list = new ArrayList<>();
    @PresenterLifeCycle
    GeneralizePresenter presenter = new GeneralizePresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_generalize_view);
    }

    @Override
    protected void loadData() {
        setTitle("推广记录");
        for (int i = 0; i < 5; i++) {
            list.add("");
        }
        RecyclerView rv_list = bindView(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_list.addItemDecoration(new SpaceVerticalItemDecoration(10));
        MineGeneralizeRvAdapter adapter = new MineGeneralizeRvAdapter(R.layout.item_mine_generalize, list, getActivity());
        rv_list.setAdapter(adapter);
    }
}
