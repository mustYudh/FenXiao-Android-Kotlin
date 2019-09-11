package com.nhbs.fenxiao.module.mine.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.base.BaseFragment;
import com.nhbs.fenxiao.module.home.adapter.HomePageAdapter;
import com.nhbs.fenxiao.module.mine.activity.presenter.MineTeamActivityPresenter;
import com.nhbs.fenxiao.module.mine.activity.presenter.MineTeamActivityViewer;
import com.nhbs.fenxiao.module.mine.fragment.MineTeamFragment;
import com.nhbs.fenxiao.module.mine.fragment.TeamGeneralizeFragment;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.utils.DensityUtils;

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


public class MineTeamActivity extends BaseBarActivity implements MineTeamActivityViewer {

    @PresenterLifeCycle
    MineTeamActivityPresenter mPresenter = new MineTeamActivityPresenter(this);
    private List<String> mDataList = new ArrayList<>();
    private List<BaseFragment> fragments = new ArrayList<>();
    private ViewPager mViewPager;
    public TextView tv_team_num;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_team_view);
    }

    @Override
    protected void loadData() {
        setTitle("我的团队");
        mViewPager = bindView(R.id.view_pager);
        tv_team_num = bindView(R.id.tv_team_num);
        mDataList.add("我的团队");
        mDataList.add("团队推广");

        fragments.add(new MineTeamFragment());
        fragments.add(new TeamGeneralizeFragment());
        initMagicIndicator();
    }




    /**
     * 初始化tablayout
     */
    private void initMagicIndicator() {
        HomePageAdapter adapter = new HomePageAdapter(getSupportFragmentManager(), mDataList, fragments);
        mViewPager.setOffscreenPageLimit(fragments.size());
        mViewPager.setAdapter(adapter);
        MagicIndicator magicIndicator = bindView(R.id.magic_indicator);
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdjustMode(false);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
                commonPagerTitleView.setContentView(R.layout.item_top_tab_recharge);
                // 初始化
                final TextView titleText = commonPagerTitleView.findViewById(R.id.tv_title);
                titleText.setText(mDataList.get(index));

                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                        titleText.setTextSize(16);
                        titleText.setTextColor(Color.parseColor("#FF2B49"));
                        titleText.setTypeface(Typeface.DEFAULT_BOLD);
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        titleText.setTextSize(16);
                        titleText.setTextColor(Color.parseColor("#ADB6C4"));
                        titleText.setTypeface(Typeface.DEFAULT);
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
                    }
                });
                commonPagerTitleView.setOnClickListener(view -> mViewPager.setCurrentItem(index));
                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                linePagerIndicator.setColors(Color.parseColor("#FF2B49"));
                linePagerIndicator.setLineWidth(DensityUtils.dp2px(getActivity(), 20));
                linePagerIndicator.setLineHeight(DensityUtils.dp2px(getActivity(), 3));
                return linePagerIndicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }
}
