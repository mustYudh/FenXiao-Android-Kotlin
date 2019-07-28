package com.nhbs.fenxiao.module.home.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.module.home.bean.HomeFindActivtyListBean;
import com.nhbs.fenxiao.utils.DateUtil;
import com.shehuan.niv.NiceImageView;
import com.yu.common.glide.ImageLoader;
import com.yu.common.ui.CircleImageView;

public class MineHomeEventRvAdapter extends BaseQuickAdapter<HomeFindActivtyListBean.RowsBean, BaseViewHolder> {
    public MineHomeEventRvAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeFindActivtyListBean.RowsBean item) {
        CircleImageView iv_icon = helper.getView(R.id.iv_icon);
        NiceImageView iv_first = helper.getView(R.id.iv_first);
        NiceImageView iv_second = helper.getView(R.id.iv_second);
        NiceImageView iv_third = helper.getView(R.id.iv_third);
        if (item.firstPrizeImgs != null && !TextUtils.isEmpty(item.firstPrizeImgs)) {
            ImageLoader.getInstance().displayImage(iv_first, item.firstPrizeImgs.split(",")[0], R.drawable.ic_placeholder, R.drawable.ic_placeholder);
        }
        if (item.accessitImgs != null && !TextUtils.isEmpty(item.accessitImgs)) {
            ImageLoader.getInstance().displayImage(iv_second, item.accessitImgs.split(",")[0], R.drawable.ic_placeholder, R.drawable.ic_placeholder);
        }
        if (item.thirdPrizeImgs != null && !TextUtils.isEmpty(item.thirdPrizeImgs)) {
            ImageLoader.getInstance().displayImage(iv_third, item.thirdPrizeImgs.split(",")[0], R.drawable.ic_placeholder, R.drawable.ic_placeholder);
        }
        helper.setText(R.id.tv_first, item.firstPrizeName);
        helper.setText(R.id.tv_second, item.accessitName);
        helper.setText(R.id.tv_third, item.thirdPrizeName);
        helper.setText(R.id.tv_content, item.content);
        helper.setText(R.id.tv_title, item.aName);
        helper.setText(R.id.tv_time, DateUtil.formatDateAndTime(item.drawTime) + "自动开奖");

        ImageLoader.getInstance().displayImage(iv_icon, item.headerImage, R.drawable.ic_placeholder, R.drawable.ic_placeholder);
    }
}
