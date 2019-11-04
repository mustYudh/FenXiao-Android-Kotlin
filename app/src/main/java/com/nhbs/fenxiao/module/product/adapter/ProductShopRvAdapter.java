package com.nhbs.fenxiao.module.product.adapter;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.module.product.activity.ProductDetailsActivity;
import com.nhbs.fenxiao.module.product.bean.FindMyShopMerchandiseListBean;
import com.yu.common.glide.ImageLoader;
import com.yu.common.launche.LauncherHelper;

public class ProductShopRvAdapter extends BaseQuickAdapter<FindMyShopMerchandiseListBean.ListBean, BaseViewHolder> {
    private Context context;

    public ProductShopRvAdapter(int layoutResId, Context context) {
        super(layoutResId);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, FindMyShopMerchandiseListBean.ListBean item) {
        ImageView iv_product = helper.getView(R.id.iv_product);
        if (item.mImgs != null) {
            String[] split = item.mImgs.split(",");
            ImageLoader.getInstance().displayImage(iv_product, split[0], R.drawable.ic_placeholder, R.drawable.ic_placeholder);
        }

        helper.setText(R.id.tv_title, item.mName);
        helper.setText(R.id.tv_price, "¥" + item.mPrice);
        helper.setText(R.id.tv_share, "分享赚¥" + item.commission);
        helper.getView(R.id.ll_root).setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("MERCHANDISE_ID", item.id);
            LauncherHelper.from(context).startActivity(ProductDetailsActivity.class, bundle);
        });
        helper.getView(R.id.tv_agency).setOnClickListener(view -> {
            if (onItemOperateListener != null){
                onItemOperateListener.onItemDetailsAgencyClick(item.id);
            }
        });

        helper.getView(R.id.tv_share).setOnClickListener(view -> {
            if (onItemOperateListener != null){
                onItemOperateListener.onItemDetailsShareClick(item.id);
            }
        });
    }

    public interface OnItemOperateListener {
        void onItemDetailsAgencyClick(String id);

        void onItemDetailsShareClick(String id);
    }

    OnItemOperateListener onItemOperateListener;

    public void setOnItemDetailsDoCilckListener(OnItemOperateListener onItemOperateListener) {
        this.onItemOperateListener = onItemOperateListener;
    }
}
