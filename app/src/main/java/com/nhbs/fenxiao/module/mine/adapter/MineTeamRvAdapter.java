package com.nhbs.fenxiao.module.mine.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.module.mine.bean.MineGroupBean;
import com.yu.common.glide.ImageLoader;
import com.yu.common.ui.CircleImageView;

public class MineTeamRvAdapter extends BaseQuickAdapter<MineGroupBean.UserGroupTosBean, BaseViewHolder> {
    private Context context;

    public MineTeamRvAdapter(int layoutResId, Context context) {
        super(layoutResId);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MineGroupBean.UserGroupTosBean item) {
        CircleImageView iv_headimg = helper.getView(R.id.iv_headimg);
        ImageLoader.getInstance().displayImage(iv_headimg, item.photo);
        helper.setText(R.id.tv_name, item.userName);
    }
}
