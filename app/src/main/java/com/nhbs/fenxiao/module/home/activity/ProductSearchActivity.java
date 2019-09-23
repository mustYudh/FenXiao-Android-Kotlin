package com.nhbs.fenxiao.module.home.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.adapter.CommonRvAdapter;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.home.activity.presenter.ProductSearchPresenter;
import com.nhbs.fenxiao.module.home.activity.presenter.ProductSearchViewer;
import com.nhbs.fenxiao.module.product.bean.FindMerchandiseListBean;
import com.nhbs.fenxiao.module.product.bean.ShareMerchandiseBean;
import com.nhbs.fenxiao.module.view.ClearEditText;
import com.nhbs.fenxiao.module.view.ScreenSpaceItemDecoration;
import com.nhbs.fenxiao.utils.DialogUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.share.ShareHelp;
import com.yu.share.SharesBean;
import com.yu.share.callback.ShareCallback;


public class ProductSearchActivity extends BaseBarActivity implements ProductSearchViewer {

    @PresenterLifeCycle
    ProductSearchPresenter mPresenter = new ProductSearchPresenter(this);
    private RecyclerView mSearch;
    private int pageNum = 1;
    private int pageSize = 10;
    private CommonRvAdapter adapter;
    private DialogUtils shareDialog;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_product_search_view);
    }

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.action_bar_product_search_layout;
    }

    @Override
    protected void loadData() {
        ClearEditText ed_search = bindView(R.id.et_search);
        LinearLayout ll_add = bindView(R.id.ll_add);
        ll_add.setOnClickListener(view -> {
            finish();
        });
        mSearch = bindView(R.id.rv_search);
        mSearch.addItemDecoration(new ScreenSpaceItemDecoration(getActivity(), 10));
        mSearch.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new CommonRvAdapter(R.layout.item_common_product, getActivity());
        mSearch.setAdapter(adapter);
        ed_search.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                InputMethodManager inputMgr = (InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMgr.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
                if (TextUtils.isEmpty(ed_search.getText().toString().trim())) {
                    ToastUtils.show("请输入商品");
                } else {
                    mPresenter.searchMerchandiseList(ed_search.getText().toString().trim(), pageNum, pageSize);
                }
                return true;
            }
            return false;
        });
    }

    @Override
    public void searchMerchandiseListSuccess(FindMerchandiseListBean findMerchandiseListBean) {
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
        ToastUtils.show("代理成功");
        item.isAgent = "1";
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
