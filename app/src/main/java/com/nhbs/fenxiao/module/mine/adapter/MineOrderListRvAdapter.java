package com.nhbs.fenxiao.module.mine.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.module.mine.bean.MineLocalOrderBean;

import java.util.List;

public class MineOrderListRvAdapter extends BaseMultiItemQuickAdapter<MineLocalOrderBean, BaseViewHolder> {
    private Context context;

    public MineOrderListRvAdapter(List<MineLocalOrderBean> data, Context context) {
        super(data);

        addItemType(0, R.layout.item_order_title);
        addItemType(1, R.layout.item_order_content);
        addItemType(2, R.layout.item_order_bottom);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MineLocalOrderBean item) {
        if (item.itemType == 0) {

        }

        if (item.itemType == 1) {

        }

        if (item.itemType == 2) {

        }
    }
}
