package com.nhbs.fenxiao.module.mine.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseFragment;
import com.nhbs.fenxiao.im.custom.SessionHelper;
import com.nhbs.fenxiao.module.mine.adapter.MineOrderListRvAdapter;
import com.nhbs.fenxiao.module.mine.fragment.presenter.MineOrderListFragmentPresenter;
import com.nhbs.fenxiao.module.mine.fragment.presenter.MineOrderListFragmentViewer;
import com.nhbs.fenxiao.module.order.bean.MineOrderListBean;
import com.nhbs.fenxiao.module.order.bean.PayInfo;
import com.nhbs.fenxiao.module.store.activity.DeliveryInfoActivity;
import com.nhbs.fenxiao.module.store.bean.ExpInfoBean;
import com.nhbs.fenxiao.utils.DialogUtils;
import com.nhbs.fenxiao.utils.PayUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.yu.common.glide.ImageLoader;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.DelayClickTextView;

import java.util.List;


public class MineOrderListFragment extends BaseFragment implements MineOrderListFragmentViewer {
    @PresenterLifeCycle
    MineOrderListFragmentPresenter mPresenter = new MineOrderListFragmentPresenter(this);
    private int order_type;
    private int pageNum = 1;
    private int pageSize = 1000;
    private MineOrderListRvAdapter adapter;
    private RecyclerView rv_list;
    private DialogUtils payDialog, codeDialog;
    private SmartRefreshLayout refreshLayout;

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
        refreshLayout = bindView(R.id.refresh);
        rv_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MineOrderListRvAdapter(R.layout.item_order_title, getActivity());
        rv_list.setAdapter(adapter);
        if ("-1".equals(order_type + "")) {
            mPresenter.getMineOrder(pageNum + "", pageSize + "", "2", "null");
        } else {
            mPresenter.getMineOrder(pageNum + "", pageSize + "", "2", order_type + "");
        }

        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()).setSpinnerStyle(SpinnerStyle.Translate));
        refreshLayout.setEnableOverScrollBounce(false);
        refreshLayout.setEnableAutoLoadMore(true);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(refreshLayout12 -> {
            pageNum = 1;
            if ("-1".equals(order_type + "")) {
                mPresenter.getMineOrder(pageNum + "", pageSize + "", "2", "null");
            } else {
                mPresenter.getMineOrder(pageNum + "", pageSize + "", "2", order_type + "");
            }
        });
    }

    @Override
    public void getMineOrderSuccess(MineOrderListBean mineOrderListBean) {
        if (refreshLayout != null) {
            refreshLayout.finishRefresh();
        }
        if (mineOrderListBean != null && mineOrderListBean.rows != null && mineOrderListBean.rows.size() != 0) {
            if (pageNum > 1) {
                adapter.addData(mineOrderListBean.rows);
            } else {
                adapter.setNewData(mineOrderListBean.rows);
            }
            adapter.setOnItemChildClickListener((adapter, view, position) -> {
                List<MineOrderListBean.RowsBean> data = adapter.getData();
                MineOrderListBean.RowsBean rowsBean = data.get(position);
                switch (view.getId()) {
                    case R.id.tv_label8:
                        mPresenter.confirmGoods(rowsBean.id);
                        break;
                    case R.id.tv_label4:
                        ToastUtils.show("已提醒发货");
                        break;
                    case R.id.tv_label3:
                        //取消订单
                        mPresenter.cancelOrder(rowsBean.id);
                        break;
                    case R.id.tv_label7:
                        //查看提货码
                        if (!TextUtils.isEmpty(rowsBean.code)) {
                            showCodeDialog(rowsBean.code);
                        } else {
                            ToastUtils.show("获取提货码失败");
                        }
                        break;
                    case R.id.tv_label6:
                        //付款
                        showTypeDialog(rowsBean);
                        break;
                    case R.id.tv_label2:
                        //查看物流
                        if (!TextUtils.isEmpty(rowsBean.expressNumber)) {
                            mPresenter.findExp(rowsBean.expressNumber);
                        } else {
                            ToastUtils.show("物流信息出错");
                        }
                        break;
                    case R.id.tv_label1:
                        if (!TextUtils.isEmpty(rowsBean.userId)) {
                            SessionHelper.startP2PSession(getActivity(), rowsBean.userId);
                        } else {
                            ToastUtils.show("数据异常");
                        }
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
    public void getMineOrderFail() {
        if (refreshLayout != null) {
            refreshLayout.finishRefresh();
        }
    }


    private int type = 1;

    @SuppressLint("SetTextI18n")
    private void showTypeDialog(MineOrderListBean.RowsBean rowsBean) {
        payDialog = new DialogUtils.Builder(getActivity()).view(R.layout.dialog_pay)
                .gravity(Gravity.BOTTOM)
                .cancelTouchout(true)
                .addViewOnclick(R.id.iv_close, view -> {
                    if (payDialog.isShowing()) {
                        payDialog.dismiss();
                    }
                })
                .style(R.style.Dialog)
                .build();
        payDialog.show();

        RelativeLayout rl_ali = payDialog.findViewById(R.id.rl_ali);
        RelativeLayout rl_wx = payDialog.findViewById(R.id.rl_wx);
        ImageView iv_ali = payDialog.findViewById(R.id.iv_ali);
        ImageView iv_wx = payDialog.findViewById(R.id.iv_wx);
        TextView tv_price = payDialog.findViewById(R.id.tv_price);
        tv_price.setText("¥" + rowsBean.price);
        rl_ali.setOnClickListener(view -> {
            iv_ali.setImageResource(R.drawable.ic_circle_select);
            iv_wx.setImageResource(R.drawable.ic_circle_normal);
            type = 1;

        });

        rl_wx.setOnClickListener(view -> {
            iv_wx.setImageResource(R.drawable.ic_circle_select);
            iv_ali.setImageResource(R.drawable.ic_circle_normal);
            type = 2;
        });

        DelayClickTextView tv_commit = payDialog.findViewById(R.id.tv_commit);
        tv_commit.setOnClickListener(view -> mPresenter.userToPay(rowsBean.id, "2", type + ""));
    }

    private void showCodeDialog(String code) {
        codeDialog = new DialogUtils.Builder(getActivity()).view(R.layout.dialog_code)
                .gravity(Gravity.CENTER)
                .cancelTouchout(true)
                .style(R.style.Dialog_NoAnimation)
                .build();
        codeDialog.show();

        ImageView iv_code = codeDialog.findViewById(R.id.iv_code);
        ImageLoader.getInstance().displayImage(iv_code, code);

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

    @Override
    public void cancelOrderSuccess() {
        ToastUtils.show("取消订单成功");
        pageNum = 1;
        if ("-1".equals(order_type + "")) {
            mPresenter.getMineOrder(pageNum + "", pageSize + "", "2", "null");
        } else {
            mPresenter.getMineOrder(pageNum + "", pageSize + "", "2", order_type + "");
        }
    }

    @Override
    public void userToPaySuccess(PayInfo payInfo) {
        if (payDialog.isShowing()) {
            payDialog.dismiss();
        }
        PayUtils.getInstance().pay(getActivity(), type, payInfo)
                .getPayResult(new PayUtils.PayCallBack() {
                    @Override
                    public void onPaySuccess(int type) {
                        pageNum = 1;
                        if ("-1".equals(order_type + "")) {
                            mPresenter.getMineOrder(pageNum + "", pageSize + "", "2", "null");
                        } else {
                            mPresenter.getMineOrder(pageNum + "", pageSize + "", "2", order_type + "");
                        }
                    }

                    @Override
                    public void onFailed(int type) {
                        ToastUtils.show("支付失败，请重试");
                    }
                });
    }

    @Override
    public void findExpSuccess(ExpInfoBean expInfoBean) {
        if (expInfoBean != null) {
            Intent intent = new Intent(getActivity(), DeliveryInfoActivity.class);
            intent.putExtra("DELIVERY_INFO", expInfoBean);
            startActivity(intent);
        }
    }
}
