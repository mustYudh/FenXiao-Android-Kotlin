package com.nhbs.fenxiao.module.order.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.order.activity.presenter.OrderDetailsPresenter;
import com.nhbs.fenxiao.module.order.activity.presenter.OrderDetailsViewer;
import com.nhbs.fenxiao.module.order.bean.OrderDetailsBean;
import com.nhbs.fenxiao.module.order.bean.PayInfo;
import com.nhbs.fenxiao.utils.DateUtil;
import com.nhbs.fenxiao.utils.DialogUtils;
import com.nhbs.fenxiao.utils.PayUtils;
import com.yu.common.glide.ImageLoader;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.CircleImageView;
import com.yu.common.ui.DelayClickTextView;


public class OrderDetailsActivity extends BaseBarActivity implements OrderDetailsViewer {

    @PresenterLifeCycle
    OrderDetailsPresenter mPresenter = new OrderDetailsPresenter(this);
    private ImageView iv_product;
    private CircleImageView iv_shop;
    private DialogUtils payDialog;
    private String order_id;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_order_details_view);
    }

    @Override
    protected void loadData() {
        setTitle("订单详情");
        Bundle bundle = getIntent().getExtras();
        order_id = bundle.getString("ORDER_ID");
        iv_product = bindView(R.id.iv_product);
        iv_shop = bindView(R.id.iv_shop);
        mPresenter.getOrderInfoDetail(order_id);
    }

    @Override
    public void getOrderInfoDetailSuccess(OrderDetailsBean orderDetailsBean) {
        if (orderDetailsBean != null) {
            //送货方式
            switch (orderDetailsBean.dealWay) {
                case "1":
                    //自提
                    bindView(R.id.tv_ziti, true);
                    bindText(R.id.tv_type, "请买家前往商家处取商品");
                    break;
                case "2":
                    //送货上门
                    bindView(R.id.tv_ziti, false);
                    bindText(R.id.tv_type, "交易成功");
                    break;
                case "3":
                    //邮寄
                    bindView(R.id.tv_ziti, false);
                    bindText(R.id.tv_type, "交易成功");
                    break;
            }
            //创建时间
            if (orderDetailsBean.createTime != null) {
                bindText(R.id.tv_create_time, "创建时间：" + DateUtil.formatTime(orderDetailsBean.createTime));
                bindView(R.id.tv_create_time, true);
            } else {
                bindView(R.id.tv_create_time, false);
            }
            //付款时间
            if (orderDetailsBean.updateTime != null) {
                bindText(R.id.tv_pay_time, "付款时间：" + DateUtil.formatTime(orderDetailsBean.updateTime));
                bindView(R.id.tv_pay_time, true);
            } else {
                bindView(R.id.tv_pay_time, false);
            }
            //发货时间
            if (orderDetailsBean.receivingTime != null) {
                bindText(R.id.tv_send_time, "发货时间：" + DateUtil.formatTime(orderDetailsBean.receivingTime));
                bindView(R.id.tv_send_time, true);
            } else {
                bindView(R.id.tv_send_time, false);
            }
            //商品信息
            bindText(R.id.tv_product, orderDetailsBean.title);
            bindText(R.id.tv_specification, orderDetailsBean.title);
            if (orderDetailsBean.tagTwo != null && !TextUtils.isEmpty(orderDetailsBean.tagTwo)) {
                bindText(R.id.tv_specification, "分类:" + orderDetailsBean.tagOne + ";" + orderDetailsBean.tagTwo);
            } else {
                bindText(R.id.tv_specification, "分类:" + orderDetailsBean.tagOne);
            }
            if (orderDetailsBean.goodsImage != null && !TextUtils.isEmpty(orderDetailsBean.goodsImage)) {
                String[] split = orderDetailsBean.goodsImage.split(",");
                ImageLoader.getInstance().displayImage(iv_product, split[0], R.drawable.ic_placeholder, R.drawable.ic_placeholder);
            }
            bindText(R.id.tv_product_price, "¥" + orderDetailsBean.price);
            bindText(R.id.tv_product_num, "X" + orderDetailsBean.number);
            bindText(R.id.tv_product_info, "共" + orderDetailsBean.number + "件商品 合计：¥" + orderDetailsBean.totalPrice);
            //店铺信息
            ImageLoader.getInstance().displayImage(iv_shop, orderDetailsBean.shopImage, R.drawable.ic_placeholder, R.drawable.ic_placeholder);
            //底部按钮
            switch (orderDetailsBean.status) {
                case "0":
                    bindText(R.id.tv_status, "待付款");
                    bindView(R.id.tv_label1, true);
                    bindView(R.id.tv_label6, true);
                    break;
                case "1":
                    bindText(R.id.tv_status, "待发货");
                    bindView(R.id.tv_label1, true);
                    bindView(R.id.tv_label2, true);
                    bindView(R.id.tv_label4, true);
                    break;
                case "2":
                    bindText(R.id.tv_status, "待签收");
                    bindView(R.id.tv_label1, true);
                    bindView(R.id.tv_label2, true);
                    bindView(R.id.tv_label8, true);
                    break;
                case "3":
                    bindText(R.id.tv_status, "待评论");
                    bindView(R.id.tv_label1, true);
                    bindView(R.id.tv_label5, true);
                    break;
                case "4":
                    bindText(R.id.tv_status, "已完成");
                    break;
                case "5":
                    bindText(R.id.tv_status, "售后/退货");
                    break;
            }
            //确认收货
            bindView(R.id.tv_label8, view -> mPresenter.confirmGoods(orderDetailsBean.id));
            //提醒发货
            bindView(R.id.tv_label4, view -> ToastUtils.show("已提醒发货"));
            //取消订单
            bindView(R.id.tv_label3, view -> mPresenter.cancelOrder(orderDetailsBean.id));
            //查看收货码
            bindView(R.id.tv_label7, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            //付款
            bindView(R.id.tv_label6, view -> showTypeDialog(orderDetailsBean));
        }
    }

    @Override
    public void confirmGoodsSuccess() {
        ToastUtils.show("确认收货成功");
        mPresenter.getOrderInfoDetail(order_id);
    }

    @Override
    public void cancelOrderSuccess() {
        ToastUtils.show("取消订单成功");
        mPresenter.getOrderInfoDetail(order_id);
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
                        mPresenter.getOrderInfoDetail(order_id);
                    }

                    @Override
                    public void onFailed(int type) {
                        ToastUtils.show("支付失败，请重试");
                    }
                });
    }

    private int type = 1;

    @SuppressLint("SetTextI18n")
    private void showTypeDialog(OrderDetailsBean orderDetailsBean) {
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
        tv_price.setText("¥" + orderDetailsBean.price);
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
        tv_commit.setOnClickListener(view -> mPresenter.userToPay(orderDetailsBean.id, "2", type + ""));
    }
}
