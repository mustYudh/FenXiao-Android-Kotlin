package com.nhbs.fenxiao.module.product.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.nhbs.fenxiao.R;
import com.shehuan.niv.NiceImageView;
import com.yu.common.glide.ImageLoader;
import com.zhouwei.mzbanner.holder.MZViewHolder;

/**
 * Created by mks on 2019/4/1.
 */

public class BannerProductViewHolder implements MZViewHolder<String> {
    private NiceImageView mImageView;

    @Override
    public View createView(Context context) {
        // 返回页面布局
        View view = LayoutInflater.from(context).inflate(R.layout.item_banner, null);
        mImageView = (NiceImageView) view.findViewById(R.id.banner_image);
        mImageView.setCornerRadius(0);
        return view;
    }

    @Override
    public void onBind(Context context, int position, String data) {
        // 数据绑定
        ImageLoader.getInstance().displayImage(mImageView, data, R.drawable.ic_placeholder, R.drawable.ic_placeholder);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
