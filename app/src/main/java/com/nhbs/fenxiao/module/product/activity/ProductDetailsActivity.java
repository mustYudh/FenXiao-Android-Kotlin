package com.nhbs.fenxiao.module.product.activity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.ImageView;

import com.hzrcht.seaofflowers.module.view.APPScrollView;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.product.activity.presenter.ProductDetailsPresenter;
import com.nhbs.fenxiao.module.product.activity.presenter.ProductDetailsViewer;
import com.nhbs.fenxiao.module.product.adapter.BannerProductViewHolder;
import com.nhbs.fenxiao.module.product.bean.MerchandiseDetailBean;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.ui.Res;
import com.yu.common.utils.DensityUtil;
import com.zhouwei.mzbanner.MZBannerView;

import java.util.Arrays;
import java.util.List;


public class ProductDetailsActivity extends BaseBarActivity implements ProductDetailsViewer, APPScrollView.OnScrollListener {

    @PresenterLifeCycle
    ProductDetailsPresenter mPresenter = new ProductDetailsPresenter(this);
    private MZBannerView mBanner;
    private final static int alphaThreshold = DensityUtil.dip2px(200);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_product_details_view);
    }

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.activity_product_details_view_bar;
    }

    @Override
    protected void loadData() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String merchandise_id = bundle.getString("MERCHANDISE_ID");
        mBanner = bindView(R.id.banner);

        bindView(R.id.action_bar_left_actions, view -> finish());

        mPresenter.getMerchandiseDetail(merchandise_id);
    }

    @Override
    public void getMerchandiseDetailSuccess(MerchandiseDetailBean merchandiseDetailBean) {
        if (merchandiseDetailBean != null) {
            if (merchandiseDetailBean.mImgs != null) {
                String[] split = merchandiseDetailBean.mImgs.split(",");
                Log.e("aaaa", split.length + "..." + split[0] + "..." + merchandiseDetailBean.mImgs);
                initBanner(Arrays.asList(split));
            }
            bindText(R.id.tv_title, merchandiseDetailBean.mName + "");
            bindText(R.id.tv_content, merchandiseDetailBean.mContent + "");
            bindText(R.id.tv_commission, "分享赚¥" + merchandiseDetailBean.commission + "");
            bindText(R.id.tv_bug_price, "¥" + merchandiseDetailBean.commission);
            bindText(R.id.tv_share_price, "¥" + merchandiseDetailBean.commission);

        }
    }


    private void initBanner(List<String> xBanner) {
        if (mBanner != null) {
            mBanner.setDuration(500);
            mBanner.setDelayedTime(3000);
            mBanner.setCanLoop(true);
            mBanner.setPages(xBanner, BannerProductViewHolder::new);
            mBanner.start();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mBanner != null) {
            mBanner.start();//开始轮播
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mBanner != null) {
            mBanner.pause();//暂停轮播
        }
    }

    @Override
    public void onDestroy() {
        if (mBanner != null) {
            mBanner.pause();//暂停轮播
        }
        super.onDestroy();
    }

    @Override
    public boolean isImmersionBar() {
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void scrollHeight(int h) {
        float alpha = (h * 1.0f / alphaThreshold);
        if (alpha > 1) {
            alpha = 1;
        }
        setAlpha(alpha, Res.color(R.color.white));
    }

    @Override
    public void actionUp(int h) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setAlpha(float alpha, int color) {
        bindView(R.id.action_bar_center_actions, alpha > 0);
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[0] = hsv[0];
        hsv[1] = hsv[1];
        hsv[2] = hsv[2];
        int darkerColor = Color.HSVToColor((int) (alpha * 255), hsv);
        bindView(R.id.action_bar).setBackgroundColor(darkerColor);
        ImageView imageView = bindView(R.id.ll_add_icon);
        ImageView backIcon = bindView(R.id.back_icon);
        ColorStateList csl =
                getResources().getColorStateList(alpha > 0 ? R.color.black : R.color.white);
        imageView.setImageTintList(csl);
        backIcon.setImageTintList(csl);
        imageView.setAlpha(alpha == 0 ? 1 : alpha);
        backIcon.setAlpha(alpha == 0 ? 1 : alpha);
        bindView(R.id.action_bar_center_actions).setAlpha(alpha);
    }
}
