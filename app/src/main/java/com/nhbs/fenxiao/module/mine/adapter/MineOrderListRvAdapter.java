package com.nhbs.fenxiao.module.mine.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.module.order.bean.MineOrderListBean;
import com.yu.common.glide.ImageLoader;
import com.yu.common.ui.CircleImageView;

public class MineOrderListRvAdapter extends BaseQuickAdapter<MineOrderListBean.RowsBean, BaseViewHolder> {
    private Context context;

    public MineOrderListRvAdapter(int layoutResId, Context context) {
        super(layoutResId);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MineOrderListBean.RowsBean item) {
        helper.setText(R.id.tv_product_name, item.title);
        helper.setText(R.id.tv_user_name, item.userName);
        if (item.tagTwo != null && !TextUtils.isEmpty(item.tagTwo)) {
            helper.setText(R.id.tv_specification, item.tagOne + ";" + item.tagTwo);
        } else {
            helper.setText(R.id.tv_specification, item.tagOne + "");
        }
        helper.setText(R.id.tv_specification, item.userName);
        helper.setText(R.id.tv_num, "X" + item.number);
        helper.setText(R.id.tv_price, "¥" + item.price);
        helper.setText(R.id.tv_total, "共" + item.number + "件商品 合计：¥" + item.price);
        ImageView iv_product = helper.getView(R.id.iv_product);
        CircleImageView iv_headimg = helper.getView(R.id.iv_headimg);
        ImageLoader.getInstance().displayImage(iv_product, item.goodsImage, R.drawable.ic_placeholder, R.drawable.ic_placeholder);
        ImageLoader.getInstance().displayImage(iv_headimg, item.shopImage, R.drawable.ic_placeholder, R.drawable.ic_placeholder);
        helper.setGone(R.id.tv_label1, false);
        helper.setGone(R.id.tv_label2, false);
        helper.setGone(R.id.tv_label3, false);
        helper.setGone(R.id.tv_label4, false);
        helper.setGone(R.id.tv_label5, false);
        helper.setGone(R.id.tv_label6, false);
        helper.setGone(R.id.tv_label7, false);
        helper.setGone(R.id.tv_label8, false);
        switch (item.status) {
            case "0":
                helper.setText(R.id.tv_status, "待付款");
                helper.setGone(R.id.tv_label1, true);
                helper.setGone(R.id.tv_label6, true);
                break;
            case "1":
                helper.setText(R.id.tv_status, "待发货");
                helper.setGone(R.id.tv_label1, true);
                helper.setGone(R.id.tv_label2, true);
                helper.setGone(R.id.tv_label4, true);
                break;
            case "2":
                helper.setText(R.id.tv_status, "待签收");
                helper.setGone(R.id.tv_label1, true);
                helper.setGone(R.id.tv_label2, true);
                helper.setGone(R.id.tv_label8, true);
                break;
            case "3":
                helper.setText(R.id.tv_status, "待评论");
                helper.setGone(R.id.tv_label1, true);
                helper.setGone(R.id.tv_label5, true);
                break;
            case "4":
                helper.setText(R.id.tv_status, "已完成");
                break;
            case "5":
                helper.setText(R.id.tv_status, "售后/退货");
                break;
        }
    }
}
