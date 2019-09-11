package com.nhbs.fenxiao.module.mine.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class MineGeneralizeRvAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;

    public MineGeneralizeRvAdapter(int layoutResId, Context context) {
        super(layoutResId);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
