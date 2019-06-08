package com.nhbs.fenxiao.module.home.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.base.BaseFragment;
import com.nhbs.fenxiao.module.home.activity.presenter.RewardAdvertisingPresenter;
import com.nhbs.fenxiao.module.home.activity.presenter.RewardAdvertisingViewer;
import com.nhbs.fenxiao.module.home.adapter.MineRewardListViewPageAdapter;
import com.nhbs.fenxiao.module.home.fragment.RewardAdvertisingFragment;
import com.nhbs.fenxiao.utils.DensityUtils;
import com.yu.common.mvp.PresenterLifeCycle;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;
import java.util.List;


public class RewardAdvertisingActivity extends BaseBarActivity implements RewardAdvertisingViewer {
    private List<String> mDataList = new ArrayList<>();
    private List<BaseFragment> fragments = new ArrayList<>();
    private ViewPager mViewPager;
    @PresenterLifeCycle
    RewardAdvertisingPresenter presenter = new RewardAdvertisingPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_reward_advertising_view);
    }

    @Override
    protected void loadData() {
        mDataList.add("热门");
        mDataList.add("招聘");
        mDataList.add("房产");
        mDataList.add("招商");
        mDataList.add("金融");
        mDataList.add("居家");
        mDataList.add("鞋品");
        mDataList.add("电器");
        mDataList.add("水汽");
        mDataList.add("火器");
        mDataList.add("其他");
        mViewPager = bindView(R.id.view_pager);

        for (int i = 0; i < mDataList.size(); i++) {
            fragments.add(RewardAdvertisingFragment.newInstance(i + 1));
        }
        initMagicIndicator();
    }

    /**
     * 初始化tablayout
     */
    private void initMagicIndicator() {
        MineRewardListViewPageAdapter adapter = new MineRewardListViewPageAdapter(getSupportFragmentManager(), mDataList, fragments);
        mViewPager.setAdapter(adapter);
        MagicIndicator magicIndicator = bindView(R.id.magic_indicator);
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
                commonPagerTitleView.setContentView(R.layout.item_top_tab_list);
                // 初始化
                final TextView titleText = commonPagerTitleView.findViewById(R.id.tv_title);
                titleText.setText(mDataList.get(index));

                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                        titleText.setTextSize(16);
                        titleText.setTextColor(Color.parseColor("#000000"));
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        titleText.setTextSize(14);
                        titleText.setTextColor(Color.parseColor("#A0A9BB"));
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
                    }
                });
                commonPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                linePagerIndicator.setColors(Color.parseColor("#FF3E2B"));
                linePagerIndicator.setLineWidth(DensityUtils.dp2px(getActivity(), 24));
                linePagerIndicator.setLineHeight(DensityUtils.dp2px(getActivity(), 2));
                return linePagerIndicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);

    }
}
