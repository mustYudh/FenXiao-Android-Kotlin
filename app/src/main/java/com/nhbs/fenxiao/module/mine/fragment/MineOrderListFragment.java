package com.nhbs.fenxiao.module.mine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseFragment;
import com.nhbs.fenxiao.module.mine.adapter.MineOrderListRvAdapter;
import com.nhbs.fenxiao.module.mine.fragment.presenter.MineOrderListFragmentPresenter;
import com.nhbs.fenxiao.module.mine.fragment.presenter.MineOrderListFragmentViewer;
import com.nhbs.fenxiao.module.order.bean.MineOrderListBean;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;

import java.util.List;


public class MineOrderListFragment extends BaseFragment implements MineOrderListFragmentViewer {
    @PresenterLifeCycle
    MineOrderListFragmentPresenter mPresenter = new MineOrderListFragmentPresenter(this);
    private int order_type;
    private int pageNum = 1;
    private int pageSize = 10;
    private MineOrderListRvAdapter adapter;
    private RecyclerView rv_list;

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
        rv_list = bindView(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MineOrderListRvAdapter(R.layout.item_order_title, getActivity());
        rv_list.setAdapter(adapter);
        if ("-1".equals(order_type + "")) {
            mPresenter.getMineOrder(pageNum + "", pageSize + "", "2", "null");
        } else {
            mPresenter.getMineOrder(pageNum + "", pageSize + "", "2", order_type + "");
        }
    }

    @Override
    public void getMineOrderSuccess(MineOrderListBean mineOrderListBean) {
        if (mineOrderListBean != null && mineOrderListBean.rows != null && mineOrderListBean.rows.size() != 0) {
            if (pageNum > 1) {
                adapter.addData(mineOrderListBean.rows);
            } else {
                adapter.setNewData(mineOrderListBean.rows);
            }
            adapter.setOnItemChildClickListener((adapter, view, position) -> {
                switch (view.getId()) {
                    case R.id.tv_label8:
                        List<MineOrderListBean.RowsBean> data = adapter.getData();
                        MineOrderListBean.RowsBean rowsBean = data.get(position);
                        mPresenter.confirmGoods(rowsBean.id);
                        break;
                    case R.id.tv_label4:
                        ToastUtils.show("已提醒发货");
                        break;
                }
            });
            bindView(R.id.ll_empty, false);
            bindView(R.id.rv_list, true);
        } else {
            //空页面
            bindView(R.id.ll_empty, true);
            bindView(R.id.rv_list, false);
        }
    }

    @Override
    public void confirmGoodsSuccess() {
        ToastUtils.show("确认收货成功");
        pageNum = 1;
        if ("-1".equals(order_type + "")) {
            mPresenter.getMineOrder(pageNum + "", pageSize + "", "2", "null");
        } else {
            mPresenter.getMineOrder(pageNum + "", pageSize + "", "2", order_type + "");
        }
    }
}
