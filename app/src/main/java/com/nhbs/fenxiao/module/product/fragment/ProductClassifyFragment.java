package com.nhbs.fenxiao.module.product.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.adapter.CommonRvAdapter;
import com.nhbs.fenxiao.base.BaseFragment;
import com.nhbs.fenxiao.module.product.fragment.presenter.ProductClassifyFragmentPresenter;
import com.nhbs.fenxiao.module.product.fragment.presenter.ProductClassifyFragmentViewer;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;


public class ProductClassifyFragment extends BaseFragment implements ProductClassifyFragmentViewer {

    @PresenterLifeCycle
    ProductClassifyFragmentPresenter presenter = new ProductClassifyFragmentPresenter(this);
    private RecyclerView rv_product;
    private List<String> list = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_product_classify_view;
    }

    public static ProductClassifyFragment newInstance(int id) {
        ProductClassifyFragment newFragment = new ProductClassifyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("PRODUCT_ID", id);
        newFragment.setArguments(bundle);
        return newFragment;
    }


    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        rv_product = bindView(R.id.rv_product);
        rv_product.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        for (int i = 0; i < 10; i++) {
            list.add("");
        }
    }

    @Override
    protected void loadData() {
        CommonRvAdapter adapter = new CommonRvAdapter(R.layout.item_common_product, list, getActivity());
        rv_product.setAdapter(adapter);
    }
}
