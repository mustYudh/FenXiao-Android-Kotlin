package com.nhbs.fenxiao.module.product.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.product.activity.presenter.ProductShopDetailsPresenter;
import com.nhbs.fenxiao.module.product.activity.presenter.ProductShopDetailsViewer;
import com.nhbs.fenxiao.module.product.adapter.ProductShopRvAdapter;
import com.nhbs.fenxiao.module.product.adapter.ProductShopTypeRvAdapter;
import com.nhbs.fenxiao.module.product.bean.FindMyShopMerchandiseListBean;
import com.nhbs.fenxiao.module.product.bean.MerchandiseClassBean;
import com.nhbs.fenxiao.module.product.bean.ShareMerchandiseBean;
import com.nhbs.fenxiao.module.product.bean.ShopOtherUserDetailBean;
import com.nhbs.fenxiao.module.store.bean.UserShopShareBean;
import com.nhbs.fenxiao.module.view.ScreenSpaceItemDecoration;
import com.nhbs.fenxiao.utils.DialogUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yu.common.glide.ImageLoader;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.CircleImageView;
import com.yu.share.ShareHelp;
import com.yu.share.SharesBean;
import com.yu.share.callback.ShareCallback;

import java.util.List;


public class ProductShopDetailsActivity extends BaseBarActivity implements ProductShopDetailsViewer {

    @PresenterLifeCycle
    ProductShopDetailsPresenter mPresenter = new ProductShopDetailsPresenter(this);
    private int pageNum = 1;
    private int pageSize = 10;
    private CircleImageView iv_shop;
    private DialogUtils shareDialog;
    private RecyclerView rv_product;
    private ProductShopRvAdapter adapter;
    private DialogUtils shopDialog;
    private String shop_id;
    private String classId = "";

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_product_shop_details_view);
    }

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.activity_product_shop_details_view_bar;
    }

    @Override
    public boolean isImmersionBar() {
        return true;
    }

    @Override
    protected void loadData() {
        Bundle bundle = getIntent().getExtras();
        shop_id = bundle.getString("SHOP_ID");
        iv_shop = bindView(R.id.iv_shop);
        rv_product = bindView(R.id.rv_product);
        rv_product.addItemDecoration(new ScreenSpaceItemDecoration(getActivity(), 10));
        rv_product.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new ProductShopRvAdapter(R.layout.item_common_product, getActivity());
        rv_product.setAdapter(adapter);
        mPresenter.getShopOtherUserDetail(shop_id);
        if (!TextUtils.isEmpty(classId)) {
            mPresenter.findMyShopMerchandiseList(classId, pageNum + "", pageSize + "", shop_id);
        } else {
            mPresenter.findMyShopMerchandiseListAll(pageNum + "", pageSize + "", shop_id);
        }
        bindView(R.id.ll_share, view -> mPresenter.userShareShop(shop_id));
        bindView(R.id.ll_type, view -> mPresenter.getMerchandiseClass());
    }

    @Override
    public void findMyShopMerchandiseListSuccess(FindMyShopMerchandiseListBean findMyShopMerchandiseListBean) {
        if (findMyShopMerchandiseListBean != null && findMyShopMerchandiseListBean.rows != null && findMyShopMerchandiseListBean.rows.size() != 0) {
            adapter.setNewData(findMyShopMerchandiseListBean.rows);
            adapter.setOnItemDetailsDoCilckListener(new ProductShopRvAdapter.OnItemOperateListener() {
                @Override
                public void onItemDetailsAgencyClick(FindMyShopMerchandiseListBean.ListBean item) {
                    if ("0".equals(item.isAgent)) {
                        mPresenter.agentMerchandise(item);
                    } else {
                        ToastUtils.show("您已经代理过了");
                    }
                }

                @Override
                public void onItemDetailsShareClick(String id) {
                    mPresenter.advertiseShare(id);
                }
            });
            bindView(R.id.ll_empty, false);
            bindView(R.id.rv_product, true);
        } else {
            //空页面
            bindView(R.id.ll_empty, true);
            bindView(R.id.rv_product, false);
        }
    }

    @Override
    public void getShopOtherUserDetailSuccess(ShopOtherUserDetailBean shopOtherUserDetailBean) {
        if (shopOtherUserDetailBean != null) {
            bindText(R.id.tv_shopName, shopOtherUserDetailBean.shopName + "");
            bindText(R.id.tv_describes, (shopOtherUserDetailBean.describes != null && !TextUtils.isEmpty(shopOtherUserDetailBean.describes)) ? shopOtherUserDetailBean.describes : "暂无活动介绍哦~");
            bindText(R.id.tv_shop_address, shopOtherUserDetailBean.province + shopOtherUserDetailBean.city + shopOtherUserDetailBean.district + "");

            ImageLoader.getInstance().displayImage(iv_shop, shopOtherUserDetailBean.shopImage, R.drawable.ic_placeholder, R.drawable.ic_placeholder);

            if ("1".equals(shopOtherUserDetailBean.status)) {
                //关注
                bindText(R.id.tv_status, "已关注");
                bindView(R.id.ll_status, view -> mPresenter.likeProductCancle(shop_id, "1"));
            } else {
                bindText(R.id.tv_status, "+ 关注");
                bindView(R.id.ll_status, view -> mPresenter.likeProduct(shop_id, "1"));
            }
        }
    }

    @Override
    public void userShareShopSuccess(UserShopShareBean userShopShareBean) {
        if (userShopShareBean != null) {
            showShareDialog(userShopShareBean);
        }
    }

    @Override
    public void advertiseShareSuccess(ShareMerchandiseBean shareMerchandiseBean) {
        if (shareMerchandiseBean != null) {
            showShareGoodsDialog(shareMerchandiseBean);
        } else {
            ToastUtils.show("分享数据出问题了~");
        }
    }

    @Override
    public void agentMerchandiseSuccess(FindMyShopMerchandiseListBean.ListBean item) {
        ToastUtils.show("代理成功");
        item.isAgent = "1";
    }

    @Override
    public void getMerchandiseClassSuccess(MerchandiseClassBean merchandiseClassBean) {
        if (merchandiseClassBean != null && merchandiseClassBean.rows != null && merchandiseClassBean.rows.size() != 0) {
            showTypeDialog(merchandiseClassBean.rows);
        }
    }

    @Override
    public void likeProductSuccess() {
        ToastUtils.show("关注成功");
        mPresenter.getShopOtherUserDetail(shop_id);
    }

    @Override
    public void likeProductCancleSuccess() {
        ToastUtils.show("取消关注成功");
        mPresenter.getShopOtherUserDetail(shop_id);
    }


    private void showShareGoodsDialog(ShareMerchandiseBean shareMerchandiseBean) {
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


    private void showShareDialog(UserShopShareBean userShopShareBean) {
        SharesBean sharesBean = new SharesBean();
        sharesBean.content = userShopShareBean.describes;
        sharesBean.iconUrl = userShopShareBean.shopImage;
        sharesBean.targetUrl = userShopShareBean.shareUrl;
        sharesBean.title = userShopShareBean.shopName;
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


    @SuppressLint("SetTextI18n")
    private void showTypeDialog(List<MerchandiseClassBean.RowsBean> rows) {
        shopDialog = new DialogUtils.Builder(getActivity()).view(R.layout.dialog_shop)
                .gravity(Gravity.BOTTOM)
                .cancelTouchout(true)
                .style(R.style.Dialog)
                .build();
        shopDialog.show();

        RecyclerView rv_shop = shopDialog.findViewById(R.id.rv_shop);
        rv_shop.setLayoutManager(new GridLayoutManager(ProductShopDetailsActivity.this, 3));

        ProductShopTypeRvAdapter shopTypeRvAdapter = new ProductShopTypeRvAdapter(R.layout.item_shop_type, rows);
        rv_shop.setAdapter(shopTypeRvAdapter);
        shopTypeRvAdapter.setOnItemDetailsDoCilckListener(id -> {
            if (shopDialog.isShowing()) {
                shopDialog.dismiss();
            }
            shopTypeRvAdapter.notifyDataSetChanged();
            classId = id;
            mPresenter.findMyShopMerchandiseList(id, pageNum + "", pageSize + "", shop_id);
        });
    }
}
