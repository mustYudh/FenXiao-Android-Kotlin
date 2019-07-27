package com.nhbs.fenxiao.module.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseFragment;
import com.nhbs.fenxiao.module.home.adapter.MineRewardListRvAdapter;
import com.nhbs.fenxiao.module.home.bean.HomeFindAdvertisingListBean;
import com.nhbs.fenxiao.module.home.fragment.presenter.RewardAdvertisingFragmentPresenter;
import com.nhbs.fenxiao.module.home.fragment.presenter.RewardAdvertisingFragmentViewer;
import com.yu.common.mvp.PresenterLifeCycle;


public class RewardAdvertisingFragment extends BaseFragment implements RewardAdvertisingFragmentViewer {
    @PresenterLifeCycle
    RewardAdvertisingFragmentPresenter mPresenter = new RewardAdvertisingFragmentPresenter(this);
    private String reward_type;
    private int pageNum = 1;
    private int pageSize = 10;
    private MineRewardListRvAdapter adapter;
    private RecyclerView rv_reward;

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
            reward_type = bundle.getString("REWARD_TYPE");
        }

        rv_reward = bindView(R.id.rv_reward);
        rv_reward.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MineRewardListRvAdapter(R.layout.item_reward_advertising, getActivity());
        rv_reward.setAdapter(adapter);

        mPresenter.getFindAdvertisingList(reward_type, pageNum, pageSize);
    }

    @Override
    public void getFindAdvertisingListSuccess(HomeFindAdvertisingListBean homeFindAdvertisingListBean) {
        if (homeFindAdvertisingListBean != null && homeFindAdvertisingListBean.rows != null && homeFindAdvertisingListBean.rows.size() != 0) {

            adapter.setNewData(homeFindAdvertisingListBean.rows);
            bindView(R.id.ll_empty, false);
            bindView(R.id.rv_reward, true);
        } else {
            //空页面
            bindView(R.id.ll_empty, true);
            bindView(R.id.rv_reward, false);
        }
    }
}
