package com.nhbs.fenxiao.module.product.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarFragment;
import com.nhbs.fenxiao.base.BaseFragment;
import com.nhbs.fenxiao.module.home.StatusBarColorManager;
import com.nhbs.fenxiao.module.product.adapter.ProductViewPageAdapter;
import com.nhbs.fenxiao.module.product.fragment.presenter.ProductFragmentPresenter;
import com.nhbs.fenxiao.module.product.fragment.presenter.ProductFragmentViewer;
import com.nhbs.fenxiao.utils.DensityUtils;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.navigation.StatusBarFontColorUtil;
import java.util.ArrayList;
import java.util.List;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;


public class ProductFragment extends BaseBarFragment implements ProductFragmentViewer {
    private List<String> mDataList = new ArrayList<>();
    private List<BaseFragment> fragments = new ArrayList<>();
    private ViewPager mViewPager;
    @PresenterLifeCycle
    private ProductFragmentPresenter presenter = new ProductFragmentPresenter(this);

    @Override protected int getActionBarLayoutId() {
        return R.layout.action_bar_product_paage_fragment_layout;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_product_view;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        mDataList.add("精选");
        mDataList.add("女装");
        mDataList.add("男装");
        mDataList.add("美妆");
        mDataList.add("食品");
        mDataList.add("居家");
        mDataList.add("鞋品");
        mDataList.add("电器");
        mDataList.add("水汽");
        mDataList.add("火器");
        mDataList.add("其他");
        mViewPager = bindView(R.id.view_pager);



        for (int i = 0; i < mDataList.size(); i++) {
            fragments.add(ProductClassifyFragment.newInstance(i));
        }
        initMagicIndicator();

    }


    /**
     * 初始化tablayout
     */
    private void initMagicIndicator() {
        ProductViewPageAdapter adapter = new ProductViewPageAdapter(getChildFragmentManager(), mDataList, fragments);
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


    @Override protected void onPageInTop() {
        super.onPageInTop();
        StatusBarColorManager.INSTANCE.setDark(true);
        StatusBarFontColorUtil.StatusBarLightMode(getActivity());
    }


}