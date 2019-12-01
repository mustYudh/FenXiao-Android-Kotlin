package com.nhbs.fenxiao.module.order.adapter;


import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.module.order.bean.RefundTypeBean;

import java.util.List;

/**
 * @author myx
 * @date on 2019-12-01
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email mayuxuan@innobuddy.com
 */
public class RefundTypePopListAdapter extends BaseQuickAdapter<RefundTypeBean, BaseViewHolder> {

    private int size = 0;

    public RefundTypePopListAdapter(int layoutResId, List<RefundTypeBean> data) {
        super(layoutResId, data);
        size = data.size();
    }

    @Override
    protected void convert(BaseViewHolder helper, RefundTypeBean item) {
        helper.setText(R.id.text_name, item.getName());
        ImageView img_choose = helper.getView(R.id.img_choose);
        if (item.isChecked()) {
            img_choose.setSelected(true);
        }else {
            img_choose.setSelected(false);
        }
        if (helper.getAdapterPosition() == size - 1) {
            helper.setGone(R.id.view_line, false);
        }else {
            helper.setGone(R.id.view_line, true);
        }
    }
}
