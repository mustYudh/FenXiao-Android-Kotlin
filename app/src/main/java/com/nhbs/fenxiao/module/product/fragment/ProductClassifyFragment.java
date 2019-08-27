package com.nhbs.fenxiao.module.product.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.adapter.CommonRvAdapter;
import com.nhbs.fenxiao.base.BaseFragment;
import com.nhbs.fenxiao.module.product.bean.FindMerchandiseListBean;
import com.nhbs.fenxiao.module.product.bean.ShareMerchandiseBean;
import com.nhbs.fenxiao.module.product.fragment.presenter.ProductClassifyFragmentPresenter;
import com.nhbs.fenxiao.module.product.fragment.presenter.ProductClassifyFragmentViewer;
import com.nhbs.fenxiao.module.view.ScreenSpaceItemDecoration;
import com.nhbs.fenxiao.utils.DialogUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.share.ShareHelp;
import com.yu.share.SharesBean;
import com.yu.share.callback.ShareCallback;


public class ProductClassifyFragment extends BaseFragment implements ProductClassifyFragmentViewer {

    @PresenterLifeCycle
    ProductClassifyFragmentPresenter mPresenter = new ProductClassifyFragmentPresenter(this);
    private RecyclerView rv_product;
    private String product_id;
    private int pageNum = 1;
    private int pageSize = 10;
    private CommonRvAdapter adapter;
    private DialogUtils shareDialog;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_product_classify_view;
    }

    public static ProductClassifyFragment newInstance(String id) {
        ProductClassifyFragment newFragment = new ProductClassifyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("PRODUCT_ID", id);
        newFragment.setArguments(bundle);
        return newFragment;
    }


    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

        rv_product = bindView(R.id.rv_product);
        rv_product.addItemDecoration(new ScreenSpaceItemDecoration(getActivity(), 10));
        rv_product.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new CommonRvAdapter(R.layout.item_common_product, getActivity());
        rv_product.setAdapter(adapter);

    }

    @Override
    protected void loadData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            product_id = bundle.getString("PRODUCT_ID");
        }


        mPresenter.getMerchandiseClassList(product_id, pageNum, pageSize);

    }

    @Override
    public void getMerchandiseClassListSuccess(FindMerchandiseListBean findMerchandiseListBean) {
        if (findMerchandiseListBean != null && findMerchandiseListBean.rows != null && findMerchandiseListBean.rows.size() != 0) {
            adapter.setNewData(findMerchandiseListBean.rows);
            adapter.setOnItemDetailsDoCilckListener(new CommonRvAdapter.OnItemOperateListener() {
                @Override
                public void onItemDetailsAgencyClick(FindMerchandiseListBean.RowsBean item) {
                    if (item.isAgent != null && "0".equals(item.isAgent)) {
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
    public void advertiseShareSuccess(ShareMerchandiseBean shareMerchandiseBean) {
        if (shareMerchandiseBean != null) {
            showShareDialog(shareMerchandiseBean);
        } else {
            ToastUtils.show("分享数据出问题了~");
        }
    }

    @Override
    public void agentMerchandiseSuccess(FindMerchandiseListBean.RowsBean item) {

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
}
