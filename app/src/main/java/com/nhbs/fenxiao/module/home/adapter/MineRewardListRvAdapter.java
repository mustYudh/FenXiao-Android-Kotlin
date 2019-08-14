package com.nhbs.fenxiao.module.home.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.module.home.activity.AdvertisingShareActivity;
import com.nhbs.fenxiao.module.home.bean.HomeFindAdvertisingListBean;
import com.yu.common.glide.ImageLoader;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.ui.CircleImageView;

public class MineRewardListRvAdapter extends BaseQuickAdapter<HomeFindAdvertisingListBean.RowsBean, BaseViewHolder> {
    private Context context;

    public MineRewardListRvAdapter(int layoutResId, Context context) {
        super(layoutResId);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeFindAdvertisingListBean.RowsBean item) {
        CircleImageView iv_icon = helper.getView(R.id.iv_icon);
        ImageLoader.getInstance().displayImage(iv_icon, item.imgs, R.drawable.ic_placeholder, R.drawable.ic_placeholder);

        helper.setText(R.id.tv_title, item.title);
//        helper.setText(R.id.tv_share_num, "已分享人数"+item.);
        helper.setText(R.id.tv_price, "¥" + item.pvSpread + "/次");
        helper.getView(R.id.tv_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("ADVERT_ID", item.id);
                LauncherHelper.from(context).startActivity(AdvertisingShareActivity.class, bundle);
            }
        });
    }
}
