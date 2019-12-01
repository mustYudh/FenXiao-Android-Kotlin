package com.nhbs.fenxiao.module.mine.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.module.mine.bean.MineSpreadLogsListBean;
import com.yu.common.glide.ImageLoader;
import com.yu.common.ui.CircleImageView;

public class MineTeamGeneralizeRvAdapter extends BaseQuickAdapter<MineSpreadLogsListBean.RowsBean, BaseViewHolder> {
    private Context context;

    public MineTeamGeneralizeRvAdapter(int layoutResId, Context context) {
        super(layoutResId);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MineSpreadLogsListBean.RowsBean item) {
        CircleImageView iv_icon = helper.getView(R.id.iv_icon);
        ImageLoader.getInstance().displayImage(iv_icon, item.imgs, R.drawable.ic_placeholder, R.drawable.ic_placeholder);
        helper.setText(R.id.tv_share, item.shareCount + "次");
        helper.setText(R.id.tv_share_success, item.shareCount + "次");
        helper.setText(R.id.tv_title, item.title + "");
        helper.setText(R.id.tv_price, "+" + item.pvSpread + "¥");
        CircleImageView iv_headimg = helper.getView(R.id.iv_headimg);
        ImageLoader.getInstance().displayImage(iv_headimg, item.imgs);
    }
}
