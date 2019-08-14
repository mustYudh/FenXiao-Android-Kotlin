package com.nhbs.fenxiao.module.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.widget.ImageView;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.home.activity.presenter.AdvertisingSharePresenter;
import com.nhbs.fenxiao.module.home.activity.presenter.AdvertisingShareViewer;
import com.nhbs.fenxiao.module.home.bean.AdvertisingInfoBean;
import com.nhbs.fenxiao.module.home.bean.AdvertisingShareBean;
import com.nhbs.fenxiao.utils.DateUtil;
import com.nhbs.fenxiao.utils.DialogUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yu.common.glide.ImageLoader;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.CircleImageView;
import com.yu.share.ShareHelp;
import com.yu.share.SharesBean;
import com.yu.share.callback.ShareCallback;


public class AdvertisingShareActivity extends BaseBarActivity implements AdvertisingShareViewer {

    @PresenterLifeCycle
    AdvertisingSharePresenter mPresenter = new AdvertisingSharePresenter(this);
    private ImageView iv_img;
    private CircleImageView iv_shop;
    private DialogUtils shareDialog;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_advertising_share_view);
    }

    @Override
    protected void loadData() {
        setTitle("广告分享");
        Bundle bundle = getIntent().getExtras();
        String advert_id = bundle.getString("ADVERT_ID");
        iv_img = bindView(R.id.iv_img);
        iv_shop = bindView(R.id.iv_shop);
        mPresenter.getAdvertiseInfo(advert_id);
    }


    @Override
    public void getAdvertiseInfoSuccess(AdvertisingInfoBean advertisingInfoBean) {
        if (advertisingInfoBean != null) {
            bindText(R.id.tv_shop_name, advertisingInfoBean.shopName + "");
            bindText(R.id.tv_title, advertisingInfoBean.title + "");
            bindText(R.id.tv_content, advertisingInfoBean.content + "");
            bindText(R.id.tv_commission, "分享任务赏金" + advertisingInfoBean.pvSpread + "元/次" + "");
            bindText(R.id.tv_gross, "总金额：¥" + advertisingInfoBean.grossSpread);
            bindText(R.id.tv_time, DateUtil.formatDateAndTime(advertisingInfoBean.createTime));
            ImageLoader.getInstance().displayImage(iv_img, advertisingInfoBean.imgs, R.drawable.ic_placeholder, R.drawable.ic_placeholder);
            ImageLoader.getInstance().displayImage(iv_shop, advertisingInfoBean.shopImage, R.drawable.ic_placeholder, R.drawable.ic_placeholder);
            bindView(R.id.tv_share, view -> mPresenter.advertiseShare(advertisingInfoBean.id));
        }
    }

    @Override
    public void advertiseShareSuccess(AdvertisingShareBean advertisingShareBean) {
        if (advertisingShareBean != null) {
            showShareDialog(advertisingShareBean);
        } else {
            ToastUtils.show("分享数据出问题了~");
        }
    }

    private void showShareDialog(AdvertisingShareBean advertisingShareBean) {
        SharesBean sharesBean = new SharesBean();
        sharesBean.content = advertisingShareBean.content;
        sharesBean.iconUrl = advertisingShareBean.imgs;
        sharesBean.targetUrl = advertisingShareBean.shareUrl;
        sharesBean.title = advertisingShareBean.title;
        ShareHelp shareHelp = new ShareHelp(getActivity());

        shareDialog = new DialogUtils.Builder(getActivity()).view(R.layout.dialog_share)
                .gravity(Gravity.BOTTOM)
                .cancelTouchout(true)
                .style(R.style.Dialog)
                .addViewOnclick(R.id.ll_save, view -> {
                    if (shareDialog.isShowing()) {
                        shareDialog.dismiss();
                    }
                })
                .addViewOnclick(R.id.ll_link, view -> {
                    if (shareDialog.isShowing()) {
                        shareDialog.dismiss();
                    }
                })
                .addViewOnclick(R.id.ll_friend, view -> {
                    if (shareDialog.isShowing()) {
                        shareDialog.dismiss();
                    }
                    sharesBean.type = SHARE_MEDIA.WEIXIN_CIRCLE;
                    shareHelp.share(sharesBean);
                })
                .addViewOnclick(R.id.ll_wx, view -> {
                    if (shareDialog.isShowing()) {
                        shareDialog.dismiss();
                    }
                    sharesBean.type = SHARE_MEDIA.WEIXIN;
                    shareHelp.share(sharesBean);
                })
                .addViewOnclick(R.id.ll_weibo, view -> {
                    if (shareDialog.isShowing()) {
                        shareDialog.dismiss();
                    }
                    sharesBean.type = SHARE_MEDIA.SINA;
                    shareHelp.share(sharesBean);
                })
                .addViewOnclick(R.id.ll_qq, view -> {
                    if (shareDialog.isShowing()) {
                        shareDialog.dismiss();
                    }
                    sharesBean.type = SHARE_MEDIA.QQ;
                    shareHelp.share(sharesBean);
                })
                .build();
        shareDialog.show();

        shareHelp.callback(new ShareCallback() {
            @Override
            public void onShareStart(SHARE_MEDIA shareMedia) {

            }

            @Override
            public void onShareSuccess(SHARE_MEDIA media) {

            }

            @Override
            public void onShareFailed(SHARE_MEDIA media, Throwable throwable) {

            }

            @Override
            public void onShareCancel(SHARE_MEDIA shareMedia) {

            }
        });
    }
}
