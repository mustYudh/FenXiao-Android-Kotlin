package com.nhbs.fenxiao.module.product.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.adapter.CommonRvAdapter;
import com.nhbs.fenxiao.base.BaseFragment;
import com.nhbs.fenxiao.module.product.bean.FindMerchandiseListBean;
import com.nhbs.fenxiao.module.product.fragment.presenter.ProductClassifyFragmentPresenter;
import com.nhbs.fenxiao.module.product.fragment.presenter.ProductClassifyFragmentViewer;
import com.nhbs.fenxiao.module.view.ScreenSpaceItemDecoration;
import com.yu.common.mvp.PresenterLifeCycle;


public class ProductClassifyFragment extends BaseFragment implements ProductClassifyFragmentViewer {

    @PresenterLifeCycle
    ProductClassifyFragmentPresenter mPresenter = new ProductClassifyFragmentPresenter(this);
    private RecyclerView rv_product;
    private String product_id;
    private int pageNum = 1;
    private int pageSize = 10;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_product_classify_view;
    }

    public static ProductClassifyFragment newInstance(String id) {
        ProductClassifyFragment newFragment = new ProductClassifyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("PRODUCT_ID", id);
        newFragment.setArguments(bundle);
        return newFragment;
    }


    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

        rv_product = bindView(R.id.rv_product);
        rv_product.addItemDecoration(new ScreenSpaceItemDecoration(getActivity(), 10));
        rv_product.setLayoutManager(new GridLayoutManager(getActivity(), 2));

    }

    @Override
    protected void loadData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            product_id = bundle.getString("PRODUCT_ID");
        }


        mPresenter.getMerchandiseClassList(product_id, pageNum, pageSize);

    }

    @Override
    public void getMerchandiseClassListSuccess(FindMerchandiseListBean findMerchandiseListBean) {
        if (findMerchandiseListBean != null && findMerchandiseListBean.rows != null && findMerchandiseListBean.rows.size() != 0) {
            CommonRvAdapter adapter = new CommonRvAdapter(R.layout.item_common_product, findMerchandiseListBean.rows, getActivity());
            rv_product.setAdapter(adapter);

            bindView(R.id.ll_empty, false);
            bindView(R.id.rv_product, true);
        } else {
            //空页面
            bindView(R.id.ll_empty, true);
            bindView(R.id.rv_product, false);
        }
    }
}
