package com.nhbs.fenxiao.module.mine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseFragment;
import com.nhbs.fenxiao.module.mine.presenter.MineFragmentPresenter;
import com.nhbs.fenxiao.module.mine.presenter.MineFragmentViewer;
import com.nhbs.fenxiao.module.view.MyOneLineView;
import com.yu.common.mvp.PresenterLifeCycle;


public class MineFragment extends BaseFragment implements MineFragmentViewer, MyOneLineView.OnRootClickListener {

    @PresenterLifeCycle
    MineFragmentPresenter presenter = new MineFragmentPresenter(this);

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_mine_view;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        LinearLayout ll_root = bindView(R.id.ll_root);
        ll_root.addView(new MyOneLineView(getActivity())
                .initMine(R.drawable.mine_team, "我的团队", "", true)
                .setOnRootClickListener(this, 1));

        ll_root.addView(new MyOneLineView(getActivity())
                .initMine(R.drawable.mine_address, "我的地址", "", true)
                .setOnRootClickListener(this, 2));

        ll_root.addView(new MyOneLineView(getActivity())
                .initMine(R.drawable.mine_wechat, "绑定微信", "", true)
                .setOnRootClickListener(this, 3).setDividerBottomHigiht(8));

        ll_root.addView(new MyOneLineView(getActivity())
                .initMine(R.drawable.mine_problem, "常见问题", "", true)
                .setOnRootClickListener(this, 4));

        ll_root.addView(new MyOneLineView(getActivity())
                .initMine(R.drawable.mine_opinion, "意见反馈", "", true)
                .setOnRootClickListener(this, 5));


        ll_root.addView(new MyOneLineView(getActivity())
                .initMine(R.drawable.mine_call, "联系我们", "", false)
                .setOnRootClickListener(this, 6));


    }


    @Override
    public void onRootClick(View view) {

    }
}
