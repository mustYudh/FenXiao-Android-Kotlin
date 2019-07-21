package com.nhbs.fenxiao.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.module.product.bean.FindMerchandiseListBean;
import com.yu.common.glide.ImageLoader;

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
        ImageLoader.getInstance().displayImage(iv_product, item.mImgs);
        helper.setText(R.id.tv_title, item.mName);
        helper.setText(R.id.tv_price, "¥" + item.mPrice);
        helper.setText(R.id.tv_shape, "分享赚¥" + item.commission);
    }
}
