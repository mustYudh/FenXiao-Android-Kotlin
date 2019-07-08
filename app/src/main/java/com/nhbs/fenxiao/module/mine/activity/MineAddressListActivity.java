package com.nhbs.fenxiao.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.mine.activity.presenter.MineAddressListPresenter;
import com.nhbs.fenxiao.module.mine.activity.presenter.MineAddressListViewer;
import com.nhbs.fenxiao.module.mine.adapter.MineAddressRvAdapter;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;


public class MineAddressListActivity extends BaseBarActivity implements MineAddressListViewer {
    private List<String> list = new ArrayList<>();
    @PresenterLifeCycle
    MineAddressListPresenter mPresenter = new MineAddressListPresenter(this);
    private RecyclerView mAddress;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_address_list_view);
    }

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.activity_mine_address_list_view_bar;
    }

    @Override
    protected void loadData() {
        setTitle("管理收货地址");
        bindView(R.id.ll_add, view -> {
            getLaunchHelper().startActivity(MineRedactAddressActivity.class);
        });
        for (int i = 0; i < 4; i++) {
            list.add("");
        }
        mAddress = bindView(R.id.rv_address);
        mAddress.setLayoutManager(new LinearLayoutManager(getActivity()));

        MineAddressRvAdapter adapter = new MineAddressRvAdapter(R.layout.item_mine_address, list, getActivity());
        mAddress.setAdapter(adapter);

    }
}
