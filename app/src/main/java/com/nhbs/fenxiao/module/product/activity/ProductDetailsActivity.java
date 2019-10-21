package com.nhbs.fenxiao.module.product.activity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;
import com.hzrcht.seaofflowers.module.view.APPScrollView;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.order.activity.AffirmOrderActivity;
import com.nhbs.fenxiao.module.product.activity.presenter.ProductDetailsPresenter;
import com.nhbs.fenxiao.module.product.activity.presenter.ProductDetailsViewer;
import com.nhbs.fenxiao.module.product.adapter.BannerProductViewHolder;
import com.nhbs.fenxiao.module.product.bean.CommentListBean;
import com.nhbs.fenxiao.module.product.bean.FindMyShopMerchandiseListBean;
import com.nhbs.fenxiao.module.product.bean.MerchandiseDetailBean;
import com.nhbs.fenxiao.module.product.bean.ShareMerchandiseBean;
import com.nhbs.fenxiao.module.product.bean.SpecificationBean;
import com.nhbs.fenxiao.utils.DialogUtils;
import com.shehuan.niv.NiceImageView;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yu.common.glide.ImageLoader;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.CircleImageView;
import com.yu.common.ui.DelayClickTextView;
import com.yu.common.ui.Res;
import com.yu.common.utils.DensityUtil;
import com.yu.share.ShareHelp;
import com.yu.share.SharesBean;
import com.yu.share.callback.ShareCallback;
import com.zhouwei.mzbanner.MZBannerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressLint("SetTextI18n")
public class ProductDetailsActivity extends BaseBarActivity implements ProductDetailsViewer, APPScrollView.OnScrollListener {

    @PresenterLifeCycle
    ProductDetailsPresenter mPresenter = new ProductDetailsPresenter(this);
    private MZBannerView mBanner;
    private final static int alphaThreshold = DensityUtil.dip2px(200);
    private String merchandise_id;
    private DialogUtils specificaDialog;
    private List<SpecificationBean> oneList = new ArrayList();
    private List<SpecificationBean> twoList = new ArrayList();
    private List<DelayClickTextView> tvList = new ArrayList();
    private int dealWay = -1;
    private int number = 1;
    private String oneTag = "";
    private String twoTag = "";
    private CircleImageView iv_shop;
    private ImageView iv_like;
    private DialogUtils shareDialog;
    private LinearLayout ll_product;

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
        merchandise_id = bundle.getString("MERCHANDISE_ID");
        mBanner = bindView(R.id.banner);
        ll_product = bindView(R.id.ll_product);
        iv_shop = bindView(R.id.iv_shop);
        iv_like = bindView(R.id.iv_like);
        bindView(R.id.action_bar_left_actions, view -> finish());

        mPresenter.getMerchandiseDetail(merchandise_id);
        mPresenter.commentList(merchandise_id, "1", "1000");

    }

    @Override
    public void getMerchandiseDetailSuccess(MerchandiseDetailBean merchandiseDetailBean) {
        if (merchandiseDetailBean != null) {
            if (merchandiseDetailBean.mImgs != null) {
                String[] split = merchandiseDetailBean.mImgs.split(",");
                initBanner(Arrays.asList(split));
            }
            mPresenter.findMyShopMerchandiseList("1", "1000", merchandiseDetailBean.shopId);

            bindText(R.id.tv_title, merchandiseDetailBean.mName + "");
            bindText(R.id.tv_price, "¥" + merchandiseDetailBean.mPrice);
            bindText(R.id.tv_content, merchandiseDetailBean.mContent + "");
            bindText(R.id.tv_share, "分享了" + merchandiseDetailBean.shareNum + "次");
            bindText(R.id.tv_shop_name, merchandiseDetailBean.shopName + "");
            bindText(R.id.tv_shop_address, merchandiseDetailBean.province + merchandiseDetailBean.city + "");
            bindText(R.id.tv_bug_price, "自买省¥" + merchandiseDetailBean.commission);
            bindText(R.id.tv_share_price, "分享赚¥" + merchandiseDetailBean.commission);
//            bindText(R.id.tv_song_huo, "送货上门:¥" + merchandiseDetailBean.delivery);
//            bindText(R.id.tv_you_fei, "快递: ¥" + merchandiseDetailBean.postage);
            iv_like.setImageResource(("1".equals(merchandiseDetailBean.followStatus)) ? R.drawable.ic_shou_cang_other : R.drawable.ic_shou_cang);
            bindView(R.id.tv_apply, view -> {
                if (merchandiseDetailBean.isAgent != null && "0".equals(merchandiseDetailBean.isAgent)) {
                    mPresenter.agentMerchandise(merchandise_id, merchandiseDetailBean);
                } else {
                    ToastUtils.show("您已经代理过了");
                }
            });


            bindView(R.id.tv_bug_price, view -> showTypeDialog(merchandiseDetailBean, merchandiseDetailBean.tagOne, merchandiseDetailBean.tagTwo));

            ImageLoader.getInstance().displayImage(iv_shop, merchandiseDetailBean.shopImage, R.drawable.ic_placeholder, R.drawable.ic_placeholder);

            bindView(R.id.tv_share_price, view -> mPresenter.advertiseShare(merchandise_id));

            bindView(R.id.rl_shop, view -> {
                if (merchandiseDetailBean.shopId != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("SHOP_ID", merchandiseDetailBean.shopId);
                    getLaunchHelper().startActivity(ProductShopDetailsActivity.class, bundle);
                }
            });

            bindView(R.id.ll_add, view -> mPresenter.likeProduct(merchandiseDetailBean, "0"));

            switch (merchandiseDetailBean.dealWay) {
                case "1":
                    bindText(R.id.tv_type, "自提");
                    break;
                case "2":
                    bindText(R.id.tv_type, "送货上门");
                    break;
                case "3":
                    bindText(R.id.tv_type, "邮寄");
                    break;
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void showTypeDialog(MerchandiseDetailBean merchandiseDetailBean, String tabOne, String tabTwo) {
        specificaDialog = new DialogUtils.Builder(getActivity()).view(R.layout.dialog_specifica)
                .gravity(Gravity.BOTTOM)
                .cancelTouchout(true)
                .style(R.style.Dialog)
                .build();
        specificaDialog.show();
        TextView tv_specification = specificaDialog.findViewById(R.id.tv_specification);
        TextView tv_price = specificaDialog.findViewById(R.id.tv_price);
        NiceImageView iv_product = specificaDialog.findViewById(R.id.iv_product);
        if (merchandiseDetailBean.mImgs != null) {
            String[] split = merchandiseDetailBean.mImgs.split(",");
            ImageLoader.getInstance().displayImage(iv_product, split[0], R.drawable.ic_placeholder, R.drawable.ic_placeholder);
        }
        tv_price.setText("¥" + merchandiseDetailBean.mPrice);

        TextView tv_num = specificaDialog.findViewById(R.id.tv_num);
        DelayClickTextView tv_commit = specificaDialog.findViewById(R.id.tv_commit);
        DelayClickTextView tv_jian = specificaDialog.findViewById(R.id.tv_jian);
        DelayClickTextView tv_add = specificaDialog.findViewById(R.id.tv_add);
        tv_add.setOnClickListener(view -> {
            number++;
            tv_num.setText(number + "");
            if (twoTag != null && !TextUtils.isEmpty(twoTag)) {
                tv_specification.setText(oneTag + "," + twoTag + "  x" + number);
            } else {
                tv_specification.setText(oneTag + "  x" + number);
            }
        });

        tv_jian.setOnClickListener(view -> {
            if (number == 1) {
                ToastUtils.show("数量不能少于1");
                return;
            }
            number--;
            tv_num.setText(number + "");
            if (twoTag != null && !TextUtils.isEmpty(twoTag)) {
                tv_specification.setText(oneTag + "," + twoTag + "  x" + number);
            } else {
                tv_specification.setText(oneTag + "  x" + number);
            }
        });


        DelayClickTextView tv_label1 = specificaDialog.findViewById(R.id.tv_label1);
        DelayClickTextView tv_label2 = specificaDialog.findViewById(R.id.tv_label2);
        DelayClickTextView tv_label3 = specificaDialog.findViewById(R.id.tv_label3);
        tvList.add(tv_label1);
        tvList.add(tv_label2);
        tvList.add(tv_label3);
        tv_label1.setOnClickListener(view -> {
            dealWay = 1;
            setTypeCheck(tv_label1);
        });
        tv_label2.setOnClickListener(view -> {
            dealWay = 2;
            setTypeCheck(tv_label2);
        });
        tv_label3.setOnClickListener(view -> {
            dealWay = 3;
            setTypeCheck(tv_label3);
        });

        FlexboxLayout flexboxLayout = specificaDialog.findViewById(R.id.flexboxLayout);
        FlexboxLayout flexboxLayout2 = specificaDialog.findViewById(R.id.flexboxLayout2);
        flexboxLayout.removeAllViews();
        flexboxLayout2.removeAllViews();
        if (tabOne != null && !TextUtils.isEmpty(tabOne)) {
            String[] split = tabOne.split("\\/");
            oneList.clear();
            for (int i = 0; i < split.length; i++) {
                SpecificationBean specificationBean = new SpecificationBean();
                specificationBean.tab = split[i];
                oneList.add(specificationBean);
            }

            for (int i = 0; i < oneList.size(); i++) {
                addChildToFlexboxLayout(flexboxLayout, oneList.get(i), oneList, 1, tv_specification);
            }
        }

        if (tabTwo != null && !TextUtils.isEmpty(tabTwo)) {
            String[] split = tabTwo.split("\\/");
            twoList.clear();
            for (int i = 0; i < split.length; i++) {
                SpecificationBean specificationBean = new SpecificationBean();
                specificationBean.tab = split[i];
                twoList.add(specificationBean);
            }

            for (int i = 0; i < twoList.size(); i++) {
                addChildToFlexboxLayout(flexboxLayout2, twoList.get(i), twoList, 2, tv_specification);
            }
        }

        tv_commit.setOnClickListener(view -> {
            if (dealWay == -1) {
                ToastUtils.show("请选择交易方式");
                return;
            }
            if (tabOne != null) {
                if (TextUtils.isEmpty(oneTag)) {
                    ToastUtils.show("请选择规格");
                    return;
                }
            }
            if (tabTwo != null) {
                if (TextUtils.isEmpty(twoTag)) {
                    ToastUtils.show("请选择规格");
                    return;
                }
            }

            if (specificaDialog != null && specificaDialog.isShowing()) {
                specificaDialog.dismiss();
            }
            Gson gson = new Gson();
            Bundle bundle = new Bundle();
            bundle.putString("MERCHANDISEDETAILBEAN", gson.toJson(merchandiseDetailBean));
            bundle.putString("DEALWAY", dealWay + "");
            bundle.putInt("NUMBER", number);
            bundle.putString("ONETAG", oneTag);
            bundle.putString("TWOTAG", twoTag);
            getLaunchHelper().startActivity(AffirmOrderActivity.class, bundle);
        });
    }

    private void addChildToFlexboxLayout(FlexboxLayout flexboxLayout, SpecificationBean bean, List<SpecificationBean> list, int type, TextView tv_specification) {
        View view = View.inflate(getActivity(), R.layout.item_specification, null);
        TextView tvLabel = view.findViewById(R.id.tv_label);
        tvLabel.setText(bean.tab);
        view.setTag(bean);
        if (bean.is_select) {
            tvLabel.setTextColor(Color.parseColor("#FF3539"));
            tvLabel.setBackgroundResource(R.drawable.shape_specification);
        } else {
            tvLabel.setTextColor(Color.parseColor("#A0A9BB"));
            tvLabel.setBackgroundResource(R.drawable.shape_specification_normal);
        }
        tvLabel.setOnClickListener(view1 -> {
            bean.is_select = true;
            for (SpecificationBean tagListBean : list) {
                if (!tagListBean.equals(bean)) {
                    tagListBean.is_select = false;
                }
            }
            checkLabeel(flexboxLayout, list, type, tv_specification);
            if (type == 1) {
                oneTag = bean.tab;
            } else {
                twoTag = bean.tab;
            }
            if (twoTag != null && !TextUtils.isEmpty(twoTag)) {
                tv_specification.setText(oneTag + "," + twoTag + "  x" + number);
            } else {
                tv_specification.setText(oneTag + "  x" + number);
            }
        });
        flexboxLayout.addView(view);
    }

    private void checkLabeel(FlexboxLayout flexboxLayout, List<SpecificationBean> list, int type, TextView tv_specification) {
        flexboxLayout.removeAllViews();
        for (SpecificationBean tagListBean : list) {
            addChildToFlexboxLayout(flexboxLayout, tagListBean, list, type, tv_specification);
        }
    }

    //点击不同对象不同的风格
    private void setTypeCheck(DelayClickTextView tvType) {
        for (int i = 0; i < tvList.size(); i++) {
            DelayClickTextView delayClickTextView = tvList.get(i);
            if (delayClickTextView.equals(tvType)) {
                tvList.get(i).setTextColor(Color.parseColor("#FF3539"));
                tvList.get(i).setBackgroundResource(R.drawable.shape_specification);
            } else {
                tvList.get(i).setTextColor(Color.parseColor("#A0A9BB"));
                tvList.get(i).setBackgroundResource(R.drawable.shape_specification_normal);
            }
        }
    }

    @Override
    public void agentMerchandiseSuccess(MerchandiseDetailBean merchandiseDetailBean) {
        ToastUtils.show("代理成功");
        merchandiseDetailBean.isAgent = "1";
    }

    @Override
    public void advertiseShareSuccess(ShareMerchandiseBean shareMerchandiseBean) {
        if (shareMerchandiseBean != null) {
            showShareDialog(shareMerchandiseBean);
        } else {
            ToastUtils.show("分享数据出问题了~");
        }
    }

    @Override
    public void likeProductSuccess(MerchandiseDetailBean merchandiseDetailBean) {
        if ("1".equals(merchandiseDetailBean.followStatus)) {
            ToastUtils.show("取消收藏成功");
            merchandiseDetailBean.followStatus = "0";
        } else {
            ToastUtils.show("收藏成功");
            merchandiseDetailBean.followStatus = "1";
        }
        iv_like.setImageResource(("1".equals(merchandiseDetailBean.followStatus)) ? R.drawable.ic_shou_cang_other : R.drawable.ic_shou_cang);
    }

    @Override
    public void commentListSuccess(CommentListBean commentListBean) {
        if (commentListBean != null) {
            bindView(R.id.ll_comment, true);
        } else {
            bindView(R.id.ll_comment, false);
        }
    }

    @Override
    public void findMyShopMerchandiseListSuccess(FindMyShopMerchandiseListBean findMyShopMerchandiseListBean) {
        if (findMyShopMerchandiseListBean != null && findMyShopMerchandiseListBean.rows != null && findMyShopMerchandiseListBean.rows.size() != 0) {
            ll_product.setVisibility(View.VISIBLE);
            ll_product.removeAllViews();
            for (int i = 0; i < findMyShopMerchandiseListBean.rows.size(); i++) {
                View view = View.inflate(getActivity(), R.layout.item_bottom_product, null);
                NiceImageView iv_icon = view.findViewById(R.id.iv_icon);
                TextView tv_name = view.findViewById(R.id.tv_name);
                TextView tv_price = view.findViewById(R.id.tv_price);
                tv_name.setText(findMyShopMerchandiseListBean.rows.get(i).mName);
                tv_price.setText("¥" + findMyShopMerchandiseListBean.rows.get(i).mPrice);
                if (findMyShopMerchandiseListBean.rows.get(i).mImgs != null) {
                    String[] split = findMyShopMerchandiseListBean.rows.get(i).mImgs.split(",");
                    ImageLoader.getInstance().displayImage(iv_icon, split[0], R.drawable.ic_placeholder, R.drawable.ic_placeholder);
                }
                ll_product.addView(view);
            }
        } else {
            ll_product.setVisibility(View.GONE);
        }
    }

    private void showShareDialog(ShareMerchandiseBean shareMerchandiseBean) {
        SharesBean sharesBean = new SharesBean();
        sharesBean.content = shareMerchandiseBean.mContent;
        sharesBean.iconUrl = shareMerchandiseBean.mImgs;
        sharesBean.targetUrl = shareMerchandiseBean.shareUrl;
        sharesBean.title = shareMerchandiseBean.mTitle;
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
