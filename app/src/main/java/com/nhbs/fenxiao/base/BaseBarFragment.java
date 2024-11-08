package com.nhbs.fenxiao.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.nhbs.fenxiao.R;
import com.yu.common.utils.BarUtils;

public abstract class BaseBarFragment extends BaseFragment {

    protected @LayoutRes
    int getActionBarLayoutId() {
        return R.layout.action_bar_white_web_view;
    }


    @Override public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                                       Bundle savedInstanceState) {
        View rootView;
        if ((rootView = getView()) == null) {
            rootView = onReplaceRootView(inflater, viewGroup);
        }
        mContentView = View.inflate(getActivity(), getContentViewId(),
                (ViewGroup) rootView.findViewById(R.id.content_container));
        return rootView;
    }


    @Override protected View onReplaceRootView(LayoutInflater inflater, ViewGroup viewGroup) {
        View rootView = super.onReplaceRootView(inflater, viewGroup);
        onReplaceActionBar(inflater, rootView);
        return rootView;
    }

    protected void onReplaceActionBar(LayoutInflater inflater, View rootView) {
        FrameLayout container = (FrameLayout) rootView.findViewById(R.id.action_bar_container);
        container.setVisibility(View.VISIBLE);
        View actionBar = inflater.inflate(getActionBarLayoutId(), container, false);
        container.addView(actionBar);
        BarUtils.setActionBarLayout(actionBar);
    }


    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        barActionInit();
    }

    protected void barActionInit() {
//        bindView(R.id.back, new View.OnClickListener() {
//            @Override public void onClick(View v) {
//                finish();
//            }
//        });
    }


    public void setTitle(CharSequence charSequence) {
        bindText(R.id.title, charSequence);
    }

    public void showBack(boolean back) {
//        bindView(R.id.back,back);
    }


    public void setRightMenu(CharSequence text, View.OnClickListener onClickListener) {
//        if (!TextUtils.isEmpty(text)) {
//            TextView right = bindView(R.id.right_menu, onClickListener);
//            right.setText(text);
//            right.setVisibility(View.VISIBLE);
//        }
    }
}
