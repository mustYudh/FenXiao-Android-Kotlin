package com.nhbs.fenxiao.module.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.adapter.CommonRvAdapter;
import com.nhbs.fenxiao.base.BaseBarFragment;
import com.nhbs.fenxiao.module.home.StatusBarColorManager;
import com.nhbs.fenxiao.module.home.activity.HomeProductClassifyActivity;
import com.nhbs.fenxiao.module.home.activity.RewardAdvertisingActivity;
import com.nhbs.fenxiao.module.home.fragment.presenter.HomeFragmentPresenter;
import com.nhbs.fenxiao.module.home.fragment.presenter.HomeFragmentViewer;
import com.nhbs.fenxiao.module.view.ScreenSpaceItemDecoration;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.navigation.StatusBarFontColorUtil;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends BaseBarFragment implements HomeFragmentViewer, View.OnClickListener {

    @PresenterLifeCycle
    private HomeFragmentPresenter presenter = new HomeFragmentPresenter(this);
    private LinearLayout ll_mission_root;
    private RecyclerView rv_home;
    private List<String> list = new ArrayList<>();

    @Override public boolean isImmersionBar() {
        return true;
    }


    @Override protected int getActionBarLayoutId() {
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
        rv_home = bindView(R.id.rv_home);
        EditText ed_search = bindView(R.id.ed_search);
        ed_search.setInputType(InputType.TYPE_NULL);

        rv_home.addItemDecoration(new ScreenSpaceItemDecoration(getActivity(), 10));
        rv_home.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        ll_product.setOnClickListener(this);
        ll_reward.setOnClickListener(this);
    }

    @Override
    protected void loadData() {
        ll_mission_root.removeAllViews();
        for (int i = 0; i < 5; i++) {
            View view = View.inflate(getActivity(), R.layout.item_home_mission_product, null);
            ll_mission_root.addView(view);
        }


        for (int i = 0; i < 10; i++) {
            list.add("");
        }

        CommonRvAdapter adapter = new CommonRvAdapter(R.layout.item_common_product, list, getActivity());
        rv_home.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_product:
                LauncherHelper.from(getActivity()).startActivity(HomeProductClassifyActivity.class);
                break;
            case R.id.ll_reward:
                LauncherHelper.from(getActivity()).startActivity(RewardAdvertisingActivity.class);
                break;
        }
    }

    @Override protected void onPageInTop() {
        super.onPageInTop();
        StatusBarColorManager.INSTANCE.setDark(false);
        StatusBarFontColorUtil.statusBarDarkMode(getActivity());
    }

}
