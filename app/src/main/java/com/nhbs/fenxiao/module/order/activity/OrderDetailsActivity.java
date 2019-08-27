package com.nhbs.fenxiao.module.order.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.order.activity.presenter.OrderDetailsPresenter;
import com.nhbs.fenxiao.module.order.activity.presenter.OrderDetailsViewer;
import com.nhbs.fenxiao.module.order.bean.OrderDetailsBean;
import com.nhbs.fenxiao.utils.DateUtil;
import com.yu.common.glide.ImageLoader;
import com.yu.common.mvp.PresenterLifeCycle;


public class OrderDetailsActivity extends BaseBarActivity implements OrderDetailsViewer {

    @PresenterLifeCycle
    OrderDetailsPresenter mPresenter = new OrderDetailsPresenter(this);
    private ImageView iv_product;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_order_details_view);
    }

    @Override
    protected void loadData() {
        setTitle("订单详情");
        Bundle bundle = getIntent().getExtras();
        String order_id = bundle.getString("ORDER_ID");
        iv_product = bindView(R.id.iv_product);
        mPresenter.getOrderInfoDetail(order_id);
    }

    @Override
    public void getOrderInfoDetailSuccess(OrderDetailsBean orderDetailsBean) {
        if (orderDetailsBean != null) {
            //送货方式
            switch (orderDetailsBean.dealWay) {
                case "1":
                    //自提
                    break;
                case "2":
                    //送货上门
                    break;
                case "3":
                    //邮寄
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

            ImageLoader.getInstance().displayImage(iv_product, orderDetailsBean.goodsImage, R.drawable.ic_placeholder, R.drawable.ic_placeholder);

            bindText(R.id.tv_product_price, "¥" + orderDetailsBean.price);
            bindText(R.id.tv_product_num, "X" + orderDetailsBean.number);
            bindText(R.id.tv_product_info, "共" + orderDetailsBean.number + "件商品 合计：¥" + orderDetailsBean.totalPrice);
        }
    }
}
