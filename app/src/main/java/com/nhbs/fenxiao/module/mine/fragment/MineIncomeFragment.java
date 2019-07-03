package com.nhbs.fenxiao.module.mine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseFragment;
import com.nhbs.fenxiao.module.mine.fragment.presenter.MineIncomePresenter;
import com.nhbs.fenxiao.module.mine.fragment.presenter.MineIncomeViewer;
import com.yu.common.mvp.PresenterLifeCycle;


public class MineIncomeFragment extends BaseFragment implements MineIncomeViewer {

    @PresenterLifeCycle
    MineIncomePresenter presenter = new MineIncomePresenter(this);


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_mine_income_view;
    }

    public static MineIncomeFragment newInstance(int income_type) {
        MineIncomeFragment newFragment = new MineIncomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("INCOME_TYPE", income_type);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {

    }
}
