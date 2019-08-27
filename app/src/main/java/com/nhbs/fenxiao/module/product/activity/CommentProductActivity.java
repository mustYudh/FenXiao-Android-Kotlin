package com.nhbs.fenxiao.module.product.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.order.bean.MineOrderListBean;
import com.nhbs.fenxiao.module.product.activity.presenter.CommentProductPresenter;
import com.nhbs.fenxiao.module.product.activity.presenter.CommentProductViewer;
import com.nhbs.fenxiao.module.product.bean.ProductCommentBean;
import com.yu.common.glide.ImageLoader;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;


public class CommentProductActivity extends BaseBarActivity implements CommentProductViewer {

    @PresenterLifeCycle
    CommentProductPresenter mPresenter = new CommentProductPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_comment_product_view);
    }

    @Override
    protected void loadData() {
        setTitle("发表评论");
        ImageView iv_product = bindView(R.id.iv_product);
        EditText et_comment = bindView(R.id.et_comment);
        Bundle bundle = getIntent().getExtras();
        String order_info = bundle.getString("ORDER_INFO");
        Gson gson = new Gson();
        MineOrderListBean.RowsBean rowsBean = gson.fromJson(order_info, MineOrderListBean.RowsBean.class);
        ImageLoader.getInstance().displayImage(iv_product, rowsBean.goodsImage, R.drawable.ic_placeholder, R.drawable.ic_placeholder);
        bindText(R.id.tv_product, rowsBean.title);
        if (rowsBean.tagTwo != null && !TextUtils.isEmpty(rowsBean.tagTwo)) {
            bindText(R.id.tv_specification, rowsBean.tagOne + ";" + rowsBean.tagTwo);
        } else {
            bindText(R.id.tv_specification, rowsBean.tagOne + "");
        }
        bindView(R.id.tv_commit, view -> {
            if (TextUtils.isEmpty(et_comment.getText().toString().trim())) {
                ToastUtils.show("评价内容不能为空");
                return;
            }
            mPresenter.productComment(rowsBean.id, et_comment.getText().toString().trim());
        });

    }

    @Override
    public void productCommentSuccess(ProductCommentBean productCommentBean) {
        ToastUtils.show("评价成功");
        finish();
    }
}
