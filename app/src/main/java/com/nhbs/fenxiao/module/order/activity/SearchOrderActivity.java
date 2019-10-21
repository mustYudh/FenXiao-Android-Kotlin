package com.nhbs.fenxiao.module.order.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.adapter.CommonRvAdapter;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.order.activity.presenter.SearchOrderPresenter;
import com.nhbs.fenxiao.module.order.activity.presenter.SearchOrderViewer;
import com.nhbs.fenxiao.module.order.bean.SearchOrderBean;
import com.nhbs.fenxiao.module.view.ClearEditText;
import com.nhbs.fenxiao.module.view.ScreenSpaceItemDecoration;
import com.nhbs.fenxiao.utils.DialogUtils;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;


public class SearchOrderActivity extends BaseBarActivity implements SearchOrderViewer {
    private RecyclerView mSearch;
    private int pageNum = 1;
    private int pageSize = 10;
    private CommonRvAdapter adapter;
    private DialogUtils shareDialog;

    @PresenterLifeCycle
    SearchOrderPresenter mPresenter = new SearchOrderPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_search_order_view);
    }

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.action_bar_search_order_layout;
    }

    @Override
    protected void loadData() {
        ClearEditText ed_search = bindView(R.id.et_search);
        LinearLayout ll_add = bindView(R.id.ll_add);
        ll_add.setOnClickListener(view -> {
            finish();
        });
        mSearch = bindView(R.id.rv_search);
        mSearch.addItemDecoration(new ScreenSpaceItemDecoration(getActivity(), 10));
        mSearch.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new CommonRvAdapter(R.layout.item_common_product, getActivity());
        mSearch.setAdapter(adapter);
        ed_search.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                InputMethodManager inputMgr = (InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMgr.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
                if (TextUtils.isEmpty(ed_search.getText().toString().trim())) {
                    ToastUtils.show("请输入订单信息");
                } else {
                    mPresenter.getSearchOrder("3", pageNum + "", pageSize + "", ed_search.getText().toString().trim());
                }
                return true;
            }
            return false;
        });
    }

    @Override
    public void getSearchOrderSuccess(SearchOrderBean searchOrderBean) {
        if (searchOrderBean != null && searchOrderBean.rows != null && searchOrderBean.rows.size() != 0) {
            bindView(R.id.ll_empty, false);
            bindView(R.id.rv_search, true);
        } else {
            bindView(R.id.ll_empty, true);
            bindView(R.id.rv_search, false);
        }
    }
}
