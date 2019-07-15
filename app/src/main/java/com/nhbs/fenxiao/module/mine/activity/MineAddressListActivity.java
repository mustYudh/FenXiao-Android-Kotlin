package com.nhbs.fenxiao.module.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.mine.activity.presenter.MineAddressListPresenter;
import com.nhbs.fenxiao.module.mine.activity.presenter.MineAddressListViewer;
import com.nhbs.fenxiao.module.mine.adapter.MineAddressRvAdapter;
import com.nhbs.fenxiao.module.mine.bean.MineAddressBean;
import com.yu.common.mvp.PresenterLifeCycle;


public class MineAddressListActivity extends BaseBarActivity implements MineAddressListViewer {
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
            getLaunchHelper().startActivityForResult(MineRedactAddressActivity.class, 1);
        });
        mAddress = bindView(R.id.rv_address);
        mAddress.setLayoutManager(new LinearLayoutManager(getActivity()));

        mPresenter.getUserAddress();

    }

    @Override
    public void getUserAddressSuccess(MineAddressBean mineAddressBean) {
        if (mineAddressBean != null) {
            if (mineAddressBean.rows != null && mineAddressBean.rows.size() != 0) {
                MineAddressRvAdapter adapter = new MineAddressRvAdapter(R.layout.item_mine_address, mineAddressBean.rows, getActivity());
                mAddress.setAdapter(adapter);
                adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        switch (view.getId()) {
                            case R.id.tv_redact:
                                Intent intent = new Intent(getActivity(), MineRedactAddressActivity.class);
                                Gson gson = new Gson();
                                String json = gson.toJson(adapter.getData().get(position));
                                intent.putExtra("item", json);
                                startActivityForResult(intent, 1);
                                break;
                        }
                    }
                });
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1:
                mPresenter.getUserAddress();
                break;
        }
    }
}
