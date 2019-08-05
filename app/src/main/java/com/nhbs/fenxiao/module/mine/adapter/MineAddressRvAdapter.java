package com.nhbs.fenxiao.module.mine.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.module.mine.bean.MineAddressBean;

import java.util.List;

public class MineAddressRvAdapter extends BaseQuickAdapter<MineAddressBean.ListBean, BaseViewHolder> {
    private Context context;

    public MineAddressRvAdapter(int layoutResId, @Nullable List<MineAddressBean.ListBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MineAddressBean.ListBean item) {
        helper.setText(R.id.tv_content, item.userName + " " + item.address);
        helper.setText(R.id.tv_address, item.specificAddress);
        helper.addOnClickListener(R.id.tv_redact);
        helper.addOnClickListener(R.id.ll_root);
    }
}
