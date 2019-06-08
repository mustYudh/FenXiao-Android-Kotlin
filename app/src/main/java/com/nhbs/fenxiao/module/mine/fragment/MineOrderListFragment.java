package com.nhbs.fenxiao.module.mine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseFragment;
import com.nhbs.fenxiao.module.mine.adapter.MineOrderListRvAdapter;
import com.nhbs.fenxiao.module.mine.bean.MineLocalOrderBean;
import com.nhbs.fenxiao.module.mine.fragment.presenter.MineOrderListFragmentPresenter;
import com.nhbs.fenxiao.module.mine.fragment.presenter.MineOrderListFragmentViewer;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;


public class MineOrderListFragment extends BaseFragment implements MineOrderListFragmentViewer {
    private List<MineLocalOrderBean> list = new ArrayList<>();
    @PresenterLifeCycle
    MineOrderListFragmentPresenter presenter = new MineOrderListFragmentPresenter(this);
    private int order_type;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_mine_order_list_fragment_view;
    }

    public static MineOrderListFragment newInstance(int order_type) {
        MineOrderListFragment newFragment = new MineOrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("ORDER_TYPE", order_type);
        newFragment.setArguments(bundle);
        return newFragment;
    }


    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            order_type = bundle.getInt("ORDER_TYPE");
        }

        RecyclerView rv_list = bindView(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        for (int i = 0; i < order_type; i++) {
            MineLocalOrderBean mineLocalOrderTitleBean = new MineLocalOrderBean();

            mineLocalOrderTitleBean.itemType = 0;
            list.add(mineLocalOrderTitleBean);


            for (int j = 0; j < 3; j++) {
                MineLocalOrderBean mineLocalOrderGoodsBean = new MineLocalOrderBean();
                mineLocalOrderGoodsBean.itemType = 1;
                list.add(mineLocalOrderGoodsBean);
            }


            MineLocalOrderBean mineLocalOrderButtonBean = new MineLocalOrderBean();
            mineLocalOrderButtonBean.itemType = 2;
            list.add(mineLocalOrderButtonBean);
        }

        MineOrderListRvAdapter adapter = new MineOrderListRvAdapter(list, getActivity());
        rv_list.setAdapter(adapter);
    }
}
