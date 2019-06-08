package com.nhbs.fenxiao.module.mine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseFragment;
import com.nhbs.fenxiao.module.mine.activity.MineOrderListActivity;
import com.nhbs.fenxiao.module.mine.activity.MineTeamActivity;
import com.nhbs.fenxiao.module.mine.fragment.presenter.MineFragmentPresenter;
import com.nhbs.fenxiao.module.mine.fragment.presenter.MineFragmentViewer;
import com.nhbs.fenxiao.module.view.MyOneLineView;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.mvp.PresenterLifeCycle;


public class MineFragment extends BaseFragment implements MineFragmentViewer, MyOneLineView.OnRootClickListener, View.OnClickListener {

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
                .initMine(R.drawable.mine_team, "我的团队", true, true).setDividerBottomMargin(48, 31)
                .setOnRootClickListener(this, 1));

        ll_root.addView(new MyOneLineView(getActivity())
                .initMine(R.drawable.mine_address, "我的地址", true, true).setDividerBottomMargin(48, 31)
                .setOnRootClickListener(this, 2));

        ll_root.addView(new MyOneLineView(getActivity())
                .initMine(R.drawable.mine_wechat, "绑定微信", true, true).setDividerBottomHigiht(8).setDividerBottomMargin(0, 15)
                .setOnRootClickListener(this, 3));

        ll_root.addView(new MyOneLineView(getActivity())
                .initMine(R.drawable.mine_problem, "常见问题", true, true).setDividerBottomMargin(48, 31)
                .setOnRootClickListener(this, 4));

        ll_root.addView(new MyOneLineView(getActivity())
                .initMine(R.drawable.mine_opinion, "意见反馈", true, true).setDividerBottomMargin(48, 31)
                .setOnRootClickListener(this, 5));


        ll_root.addView(new MyOneLineView(getActivity())
                .initMine(R.drawable.mine_call, "联系我们", true, false)
                .setOnRootClickListener(this, 6));


        LinearLayout ll_order = bindView(R.id.ll_order);
        ll_order.setOnClickListener(this);

    }


    @Override
    public void onRootClick(View view) {
        switch ((int) view.getTag()) {
            case 1:
                getLaunchHelper().startActivity(MineTeamActivity.class);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_order:
                LauncherHelper.from(getActivity()).startActivity(MineOrderListActivity.class);
                break;
        }
    }
}
