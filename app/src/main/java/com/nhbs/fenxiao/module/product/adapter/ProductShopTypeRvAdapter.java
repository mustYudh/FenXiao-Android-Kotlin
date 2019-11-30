package com.nhbs.fenxiao.module.product.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.module.product.bean.MerchandiseClassBean;

import java.util.List;

public class ProductShopTypeRvAdapter extends BaseQuickAdapter<MerchandiseClassBean.RowsBean, BaseViewHolder> {


    private List<MerchandiseClassBean.RowsBean> list;

    public ProductShopTypeRvAdapter(int layoutResId, @Nullable List<MerchandiseClassBean.RowsBean> list) {
        super(layoutResId, list);
        this.list = list;
        initDate();
    }

    // 初始化isSelected的数据
    public void initDate() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).isIs_select = false;
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, MerchandiseClassBean.RowsBean item) {
        helper.setText(R.id.tv_name, item.classify);
        TextView tv_name = helper.getView(R.id.tv_name);
        int position = helper.getLayoutPosition();
        if (list.get(position).isIs_select) {
            tv_name.setTextColor(Color.parseColor("#FF2751"));
            tv_name.setBackgroundResource(R.drawable.shape_shop_type);
        } else {
            tv_name.setTextColor(Color.parseColor("#A0A9BB"));
            tv_name.setBackgroundResource(R.drawable.shape_shop_type_normal);
        }

        helper.getView(R.id.tv_name).setOnClickListener(view -> {

            initDate();
            if (!list.get(position).isIs_select) {
                list.get(position).isIs_select = true;
            } else {
                list.get(position).isIs_select = false;
            }
            if (onItemOperateListener != null) {
                onItemOperateListener.onItemDetailTypeClick(item.id);
            }
        });
    }

    public interface OnItemOperateListener {

        void onItemDetailTypeClick(String id);
    }

    OnItemOperateListener onItemOperateListener;

    public void setOnItemDetailsDoCilckListener(OnItemOperateListener onItemOperateListener) {
        this.onItemOperateListener = onItemOperateListener;
    }
}
