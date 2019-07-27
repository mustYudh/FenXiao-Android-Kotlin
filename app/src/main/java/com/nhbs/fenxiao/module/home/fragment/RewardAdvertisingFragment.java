package com.nhbs.fenxiao.module.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseFragment;
import com.nhbs.fenxiao.module.home.adapter.MineRewardListRvAdapter;
import com.nhbs.fenxiao.module.home.bean.HomeFindAdvertisingListBean;
import com.nhbs.fenxiao.module.home.fragment.presenter.RewardAdvertisingFragmentPresenter;
import com.nhbs.fenxiao.module.home.fragment.presenter.RewardAdvertisingFragmentViewer;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;


public class RewardAdvertisingFragment extends BaseFragment implements RewardAdvertisingFragmentViewer {
    private List<String> list = new ArrayList<>();
    @PresenterLifeCycle
    RewardAdvertisingFragmentPresenter mPresenter = new RewardAdvertisingFragmentPresenter(this);
    private int reward_type;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_reward_advertising_fragment_view;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    public static RewardAdvertisingFragment newInstance(String reward_type) {
        RewardAdvertisingFragment newFragment = new RewardAdvertisingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("REWARD_TYPE", reward_type);
        newFragment.setArguments(bundle);
        return newFragment;
    }


    @Override
    protected void loadData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            reward_type = bundle.getInt("REWARD_TYPE");
        }

        RecyclerView rv_reward = bindView(R.id.rv_reward);
        rv_reward.setLayoutManager(new LinearLayoutManager(getActivity()));

        for (int i = 0; i < reward_type; i++) {
            list.add("");
        }
        Log.d("fragment","走了吗");
        MineRewardListRvAdapter adapter = new MineRewardListRvAdapter(R.layout.item_reward_advertising, list, getActivity());
        rv_reward.setAdapter(adapter);

    }

    @Override
    public void getFindAdvertisingListSuccess(HomeFindAdvertisingListBean homeFindAdvertisingListBean) {

    }
}
