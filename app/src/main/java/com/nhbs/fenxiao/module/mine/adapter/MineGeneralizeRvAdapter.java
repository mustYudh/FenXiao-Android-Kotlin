package com.nhbs.fenxiao.module.mine.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.module.mine.bean.MineSpreadLogsListBean;
import com.nhbs.fenxiao.utils.DateUtil;
import com.yu.common.glide.ImageLoader;
import com.yu.common.ui.CircleImageView;

public class MineGeneralizeRvAdapter extends BaseQuickAdapter<MineSpreadLogsListBean.RowsBean, BaseViewHolder> {
    private Context context;

    public MineGeneralizeRvAdapter(int layoutResId, Context context) {
        super(layoutResId);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MineSpreadLogsListBean.RowsBean item) {
        CircleImageView iv_icon = helper.getView(R.id.iv_icon);
        ImageLoader.getInstance().displayImage(iv_icon, item.imgs, R.drawable.ic_placeholder, R.drawable.ic_placeholder);
        helper.setText(R.id.tv_share, "已分享人数" + item.shareCount);
        helper.setText(R.id.tv_num, "推广剩余费用：¥" + item.grossSpread);
        helper.setText(R.id.tv_price, "¥" + item.pvSpread + "/次");
        helper.setText(R.id.tv_time, "截止时间："+DateUtil.formatDate(item.endTime));
    }
}
