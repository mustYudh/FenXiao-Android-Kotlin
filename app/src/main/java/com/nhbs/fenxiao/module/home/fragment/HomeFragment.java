package com.nhbs.fenxiao.module.home.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.adapter.CommonRvAdapter;
import com.nhbs.fenxiao.base.BaseBarFragment;
import com.nhbs.fenxiao.module.home.StatusBarColorManager;
import com.nhbs.fenxiao.module.home.activity.HomeEventActivity;
import com.nhbs.fenxiao.module.home.activity.HomeProductClassifyActivity;
import com.nhbs.fenxiao.module.home.activity.RewardAdvertisingActivity;
import com.nhbs.fenxiao.module.home.adapter.BannerViewHolder;
import com.nhbs.fenxiao.module.home.bean.HomeBannerBean;
import com.nhbs.fenxiao.module.home.bean.HomeHotAdvertiseBean;
import com.nhbs.fenxiao.module.home.fragment.presenter.HomeFragmentPresenter;
import com.nhbs.fenxiao.module.home.fragment.presenter.HomeFragmentViewer;
import com.nhbs.fenxiao.module.product.bean.FindMerchandiseListBean;
import com.nhbs.fenxiao.module.view.ScreenSpaceItemDecoration;
import com.yu.common.glide.ImageLoader;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.navigation.StatusBarFontColorUtil;
import com.yu.common.ui.CircleImageView;
import com.zhouwei.mzbanner.MZBannerView;

import java.util.List;


public class HomeFragment extends BaseBarFragment implements HomeFragmentViewer, View.OnClickListener {

    @PresenterLifeCycle
    private HomeFragmentPresenter mPresenter = new HomeFragmentPresenter(this);
    private LinearLayout ll_mission_root;
    private RecyclerView rv_home;
    private int pageNum = 1;
    private int pageSize = 10;
    private MZBannerView mBanner;

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
        rv_home = bindView(R.id.rv_home);
        mBanner = bindView(R.id.banner);
        EditText ed_search = bindView(R.id.ed_search);
        ed_search.setInputType(InputType.TYPE_NULL);

        rv_home.addItemDecoration(new ScreenSpaceItemDecoration(getActivity(), 10));
        rv_home.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        ll_product.setOnClickListener(this);
        ll_reward.setOnClickListener(this);
        ll_activity.setOnClickListener(this);
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
        if (findMerchandiseListBean != null && findMerchandiseListBean.rows != null && findMerchandiseListBean.rows.size() != 0) {
            CommonRvAdapter adapter = new CommonRvAdapter(R.layout.item_common_product, findMerchandiseListBean.rows, getActivity());
            rv_home.setAdapter(adapter);
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
                ImageLoader.getInstance().displayImage(iv_icon, homeHotAdvertiseBean.rows.get(i).imgs, R.drawable.ic_placeholder, R.drawable.ic_placeholder);
                tv_title.setText(homeHotAdvertiseBean.rows.get(i).title + "");
                tv_price.setText("¥" + homeHotAdvertiseBean.rows.get(i).pvSpread + "/次");
                tv_share.setText("已分享人数" + homeHotAdvertiseBean.rows.get(i).total);
                ll_mission_root.addView(view);
            }
        }
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
