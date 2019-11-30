package com.nhbs.fenxiao.module.home.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.adapter.CommonRvAdapter;
import com.nhbs.fenxiao.base.BaseBarFragment;
import com.nhbs.fenxiao.module.home.StatusBarColorManager;
import com.nhbs.fenxiao.module.home.activity.AdvertisingShareActivity;
import com.nhbs.fenxiao.module.home.activity.HomeEventActivity;
import com.nhbs.fenxiao.module.home.activity.HomeProductClassifyActivity;
import com.nhbs.fenxiao.module.home.activity.ProductSearchActivity;
import com.nhbs.fenxiao.module.home.activity.RewardAdvertisingActivity;
import com.nhbs.fenxiao.module.home.adapter.BannerViewHolder;
import com.nhbs.fenxiao.module.home.bean.HomeBannerBean;
import com.nhbs.fenxiao.module.home.bean.HomeHotAdvertiseBean;
import com.nhbs.fenxiao.module.home.fragment.presenter.HomeFragmentPresenter;
import com.nhbs.fenxiao.module.home.fragment.presenter.HomeFragmentViewer;
import com.nhbs.fenxiao.module.product.bean.FindMerchandiseListBean;
import com.nhbs.fenxiao.module.product.bean.ShareMerchandiseBean;
import com.nhbs.fenxiao.module.view.ScreenSpaceItemDecoration;
import com.nhbs.fenxiao.utils.DialogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yu.common.glide.ImageLoader;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.navigation.StatusBarFontColorUtil;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.CircleImageView;
import com.yu.share.ShareHelp;
import com.yu.share.SharesBean;
import com.yu.share.callback.ShareCallback;
import com.zhouwei.mzbanner.MZBannerView;

import java.util.List;


public class HomeFragment extends BaseBarFragment implements HomeFragmentViewer, View.OnClickListener {

    @PresenterLifeCycle
    private HomeFragmentPresenter mPresenter = new HomeFragmentPresenter(this);
    private LinearLayout ll_mission_root;
    private RecyclerView rv_home;
    private int pageNum = 1;
    private int pageSize = 1000;
    private MZBannerView mBanner;
    private CommonRvAdapter adapter;
    private DialogUtils shareDialog;
    private SmartRefreshLayout refreshLayout;

    @Override
    public boolean isImmersionBar() {
        return true;
    }


    @Override
    protected int getActionBarLayoutId() {
        return R.layout.action_bar_home_paage_fragment_layout;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home_view;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        ll_mission_root = bindView(R.id.ll_mission_root);
        LinearLayout ll_product = bindView(R.id.ll_product);
        LinearLayout ll_reward = bindView(R.id.ll_reward);
        LinearLayout ll_activity = bindView(R.id.ll_activity);
        refreshLayout = bindView(R.id.refresh);
        rv_home = bindView(R.id.rv_home);
        mBanner = bindView(R.id.banner);
        EditText ed_search = bindView(R.id.ed_search);
        ed_search.setInputType(InputType.TYPE_NULL);
        ed_search.setOnClickListener(view -> getLaunchHelper().startActivity(ProductSearchActivity.class));
        rv_home.addItemDecoration(new ScreenSpaceItemDecoration(getActivity(), 10));
        rv_home.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new CommonRvAdapter(R.layout.item_common_product, getActivity());
        rv_home.setAdapter(adapter);
        ll_product.setOnClickListener(this);
        ll_reward.setOnClickListener(this);
        ll_activity.setOnClickListener(this);
        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()).setSpinnerStyle(SpinnerStyle.Translate));
        refreshLayout.setEnableOverScrollBounce(false);
        refreshLayout.setEnableAutoLoadMore(true);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(refreshLayout12 -> {
            pageNum = 1;
            mPresenter.getMerchandiseClassList("1", pageNum, pageSize);
        });
    }

    @Override
    protected void loadData() {
        mPresenter.getMerchandiseClassList("1", pageNum, pageSize);
        mPresenter.getBannerList();
        mPresenter.getHotAdvertiseList("全部", "全部", "全部");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_product:
                getLaunchHelper().startActivity(HomeProductClassifyActivity.class);
                break;
            case R.id.ll_reward:
                getLaunchHelper().startActivity(RewardAdvertisingActivity.class);
                break;
            case R.id.ll_activity:
                getLaunchHelper().startActivity(HomeEventActivity.class);
                break;
        }
    }

    @Override
    protected void onPageInTop() {
        super.onPageInTop();
        StatusBarColorManager.INSTANCE.setDark(false);
        StatusBarFontColorUtil.statusBarDarkMode(getActivity());
    }

    @Override
    public void getMerchandiseClassListSuccess(FindMerchandiseListBean findMerchandiseListBean) {
        if (refreshLayout != null) {
            refreshLayout.finishRefresh();
        }
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
        }
    }

    @Override
    public void getBannerListSuccess(HomeBannerBean homeBannerBean) {
        if (homeBannerBean != null && homeBannerBean.rows != null && homeBannerBean.rows.size() != 0) {
            initBanner(homeBannerBean.rows);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getHotAdvertiseListSuccess(HomeHotAdvertiseBean homeHotAdvertiseBean) {
        if (homeHotAdvertiseBean != null && homeHotAdvertiseBean.rows != null && homeHotAdvertiseBean.rows.size() != 0) {
            ll_mission_root.removeAllViews();
            for (int i = 0; i < homeHotAdvertiseBean.rows.size(); i++) {
                View view = View.inflate(getActivity(), R.layout.item_home_mission_product, null);
                TextView tv_title = view.findViewById(R.id.tv_title);
                TextView tv_price = view.findViewById(R.id.tv_price);
                TextView tv_share = view.findViewById(R.id.tv_share);
                CircleImageView iv_icon = view.findViewById(R.id.iv_icon);
                LinearLayout ll_root = view.findViewById(R.id.ll_root);
                ImageLoader.getInstance().displayImage(iv_icon, homeHotAdvertiseBean.rows.get(i).imgs, R.drawable.ic_placeholder, R.drawable.ic_placeholder);
                tv_title.setText(homeHotAdvertiseBean.rows.get(i).title + "");
                tv_price.setText("¥" + homeHotAdvertiseBean.rows.get(i).pvSpread + "/次");
                tv_share.setText("已分享人数" + homeHotAdvertiseBean.rows.get(i).total);
                int finalI = i;
                ll_root.setOnClickListener(view1 -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("ADVERT_ID", homeHotAdvertiseBean.rows.get(finalI).id);
                    getLaunchHelper().startActivity(AdvertisingShareActivity.class, bundle);
                });
                ll_mission_root.addView(view);
            }
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

    @Override
    public void getMerchandiseClassListFail() {
        if (refreshLayout != null) {
            refreshLayout.finishRefresh();
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

    /**
     * banner 初始化
     *
     * @param xbanner
     */
    private void initBanner(List<HomeBannerBean.RowsBean> xbanner) {
        if (mBanner != null) {
            mBanner.setDuration(500);
            mBanner.setDelayedTime(3000);
            mBanner.setCanLoop(true);
            mBanner.setPages(xbanner, BannerViewHolder::new);
            mBanner.start();
        }
    }
}
