package com.nhbs.fenxiao.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.module.product.activity.ProductDetailsActivity;
import com.nhbs.fenxiao.module.product.bean.FindMerchandiseListBean;
import com.yu.common.glide.ImageLoader;
import com.yu.common.launche.LauncherHelper;

import java.util.List;

public class CommonRvAdapter extends BaseQuickAdapter<FindMerchandiseListBean.RowsBean, BaseViewHolder> {
    private Context context;

    public CommonRvAdapter(int layoutResId, @Nullable List<FindMerchandiseListBean.RowsBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, FindMerchandiseListBean.RowsBean item) {
        ImageView iv_product = helper.getView(R.id.iv_product);
        ImageLoader.getInstance().displayImage(iv_product, item.mImgs,R.drawable.ic_placeholder,R.drawable.ic_placeholder);
        helper.setText(R.id.tv_title, item.mName);
        helper.setText(R.id.tv_price, "¥" + item.mPrice);
        helper.setText(R.id.tv_shape, "分享赚¥" + item.commission);
        helper.getView(R.id.ll_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("MERCHANDISE_ID", item.id);
                LauncherHelper.from(context).startActivity(ProductDetailsActivity.class, bundle);
            }
        });

    }
}
