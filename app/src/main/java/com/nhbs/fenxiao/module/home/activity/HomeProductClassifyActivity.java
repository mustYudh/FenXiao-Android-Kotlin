package com.nhbs.fenxiao.module.home.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.base.BaseFragment;
import com.nhbs.fenxiao.module.home.activity.presenter.HomeProductClassifyPresenter;
import com.nhbs.fenxiao.module.home.activity.presenter.HomeProductClassifyViewer;
import com.nhbs.fenxiao.module.product.adapter.ProductViewPageAdapter;
import com.nhbs.fenxiao.module.product.bean.MerchandiseClassBean;
import com.nhbs.fenxiao.module.product.fragment.ProductClassifyFragment;
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


public class HomeProductClassifyActivity extends BaseBarActivity implements HomeProductClassifyViewer {
    private List<String> mDataList = new ArrayList<>();
    private List<BaseFragment> fragments = new ArrayList<>();
    private ViewPager mViewPager;
    @PresenterLifeCycle
    HomeProductClassifyPresenter mPresenter = new HomeProductClassifyPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_product_classify_view);
    }

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.action_bar_product_paage_fragment_layout;
    }

    @Override
    protected void loadData() {

        mViewPager = bindView(R.id.view_pager);

        EditText et_search = bindView(R.id.et_search);
        et_search.setInputType(InputType.TYPE_NULL);
        bindView(R.id.et_search, view -> getLaunchHelper().startActivity(ProductSearchActivity.class));
        mPresenter.getMerchandiseClass();

        bindView(R.id.action_bar_left_actions, true);
        bindView(R.id.action_bar_left_actions, view -> {
            finish();
        });

    }

    /**
     * 初始化tablayout
     */
    private void initMagicIndicator() {
        ProductViewPageAdapter adapter = new ProductViewPageAdapter(getSupportFragmentManager(), mDataList, fragments);
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

    @Override
    public void getMerchandiseClassSuccess(MerchandiseClassBean merchandiseClassBean) {
        if (merchandiseClassBean != null && merchandiseClassBean.rows != null && merchandiseClassBean.rows.size() != 0) {
            for (int i = 0; i < merchandiseClassBean.rows.size(); i++) {
                mDataList.add(merchandiseClassBean.rows.get(i).classify);
                fragments.add(ProductClassifyFragment.newInstance(merchandiseClassBean.rows.get(i).id));
            }

            initMagicIndicator();
        }
    }
}
