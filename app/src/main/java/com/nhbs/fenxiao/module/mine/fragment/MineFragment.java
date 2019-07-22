package com.nhbs.fenxiao.module.mine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.denghao.control.view.utils.UpdataCurrentFragment;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarFragment;
import com.nhbs.fenxiao.module.home.StatusBarColorManager;
import com.nhbs.fenxiao.module.mine.activity.MineAddressListActivity;
import com.nhbs.fenxiao.module.mine.activity.MineGeneralizeActivity;
import com.nhbs.fenxiao.module.mine.activity.MineIncomeActivity;
import com.nhbs.fenxiao.module.mine.activity.MineOpinionActivity;
import com.nhbs.fenxiao.module.mine.activity.MineOrderListActivity;
import com.nhbs.fenxiao.module.mine.activity.MineTeamActivity;
import com.nhbs.fenxiao.module.mine.activity.MineWithdrawActivity;
import com.nhbs.fenxiao.module.mine.bean.MineUserInfoBean;
import com.nhbs.fenxiao.module.mine.fragment.presenter.MineFragmentPresenter;
import com.nhbs.fenxiao.module.mine.fragment.presenter.MineFragmentViewer;
import com.nhbs.fenxiao.module.view.MyOneLineView;
import com.yu.common.glide.ImageLoader;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.navigation.StatusBarFontColorUtil;
import com.yu.common.ui.CircleImageView;
import com.yu.common.ui.DelayClickTextView;

public class MineFragment extends BaseBarFragment
        implements MineFragmentViewer, MyOneLineView.OnRootClickListener, View.OnClickListener,
    UpdataCurrentFragment {

    @PresenterLifeCycle
    private MineFragmentPresenter mPresenter = new MineFragmentPresenter(this);
    private CircleImageView mHeadimg;

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.action_bar_page_fragment_mine_layout;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_mine_view;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        mHeadimg = bindView(R.id.iv_headimg);
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
        LinearLayout ll_generalize = bindView(R.id.ll_generalize);
        LinearLayout ll_income = bindView(R.id.ll_income);
        DelayClickTextView tv_withdraw = bindView(R.id.tv_withdraw);
        ll_order.setOnClickListener(this);
        ll_generalize.setOnClickListener(this);
        ll_income.setOnClickListener(this);
        tv_withdraw.setOnClickListener(this);

        mPresenter.getUserInfo();

    }


    @Override
    public void onRootClick(View view) {
        switch ((int) view.getTag()) {
            case 1:
                //团队
                getLaunchHelper().startActivity(MineTeamActivity.class);
                break;
            case 2:
                //地址
                getLaunchHelper().startActivity(MineAddressListActivity.class);
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                //意见反馈
                getLaunchHelper().startActivity(MineOpinionActivity.class);
                break;
            case 6:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_order:
                //订单
                LauncherHelper.from(getActivity()).startActivity(MineOrderListActivity.class);
                break;
            case R.id.ll_generalize:
                //推广记录
                LauncherHelper.from(getActivity()).startActivity(MineGeneralizeActivity.class);
                break;
            case R.id.ll_income:
                //收入报表
                LauncherHelper.from(getActivity()).startActivity(MineIncomeActivity.class);
                break;
            case R.id.tv_withdraw:
                //提现
                LauncherHelper.from(getActivity()).startActivity(MineWithdrawActivity.class);
                break;
        }
    }


    @Override
    protected void onPageInTop() {
        super.onPageInTop();
        StatusBarColorManager.INSTANCE.setDark(true);
        StatusBarFontColorUtil.StatusBarLightMode(getActivity());
    }

    @Override
    public void getUserInfoSuccess(MineUserInfoBean mineUserInfoBean) {
        if (mineUserInfoBean != null) {
            bindText(R.id.tv_name, mineUserInfoBean.nickName);
            bindText(R.id.tv_balance, mineUserInfoBean.balance + "");
            bindText(R.id.tv_focusnum, "关注：" + mineUserInfoBean.focusNum + "件宝贝");
            if (mineUserInfoBean.headImage != null && !TextUtils.isEmpty(mineUserInfoBean.headImage)) {
                ImageLoader.getInstance().displayImage(mHeadimg, mineUserInfoBean.headImage + "");
            } else {
                mHeadimg.setImageResource(R.drawable.ic_launcher_background);
            }
        }
    }

    @Override public void update(Bundle bundle) {
        loadData();
    }
}
