package com.nhbs.fenxiao.module.order.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.mine.activity.MineAddressListActivity;
import com.nhbs.fenxiao.module.order.activity.presenter.AffirmOrderPresenter;
import com.nhbs.fenxiao.module.order.activity.presenter.AffirmOrderViewer;
import com.nhbs.fenxiao.module.order.bean.CreateOrderParams;
import com.nhbs.fenxiao.module.order.bean.CreateUserOrderBean;
import com.nhbs.fenxiao.module.order.bean.FirstAddressBean;
import com.nhbs.fenxiao.module.product.bean.MerchandiseDetailBean;
import com.yu.common.glide.ImageLoader;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;

/**
 * 确认订单
 */
public class AffirmOrderActivity extends BaseBarActivity implements AffirmOrderViewer {

    @PresenterLifeCycle
    AffirmOrderPresenter mPresenter = new AffirmOrderPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_affirm_order_view);
    }

    private String addressId = "11e6f82d8dd445b2977d5abaa0db4b7a";

    @Override
    protected void loadData() {
        setTitle("确认订单");
        mPresenter.getFirstAddress();
        Bundle bundle = getIntent().getExtras();
        String merchandisedetail = bundle.getString("MERCHANDISEDETAILBEAN");
        String dealway = bundle.getString("DEALWAY");
        String number = bundle.getString("NUMBER");
        String onetag = bundle.getString("ONETAG");
        String twotag = bundle.getString("TWOTAG");

        Gson gson = new Gson();
        MerchandiseDetailBean merchandiseDetailBean = gson.fromJson(merchandisedetail, MerchandiseDetailBean.class);
        ImageView iv_product = bindView(R.id.iv_product);
        if (merchandiseDetailBean.mImgs != null) {
            String[] split = merchandiseDetailBean.mImgs.split(",");
            ImageLoader.getInstance().displayImage(iv_product, split[0], R.drawable.ic_placeholder, R.drawable.ic_placeholder);
        }
        bindText(R.id.tv_product, merchandiseDetailBean.mName);
        TextView tv_specification = bindView(R.id.tv_specification);
        if (!TextUtils.isEmpty(twotag)) {
            tv_specification.setText("分类:" + onetag + ";" + twotag);
        } else {
            tv_specification.setText("分类:" + onetag);
        }


        bindText(R.id.tv_num, number);

        bindView(R.id.tv_commit, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateOrderParams params = new CreateOrderParams();
                params.orderId = merchandiseDetailBean.id;
                params.title = merchandiseDetailBean.mName;
                params.type = "2";
                params.price = merchandiseDetailBean.mPrice + "";
                params.addressId = addressId;
                params.number = number;
                if (onetag != null && !TextUtils.isEmpty(onetag)) {
                    params.color = onetag;
                }
                if (twotag != null && !TextUtils.isEmpty(twotag)) {
                    params.size = twotag;
                }
                params.dealWay = dealway;
                mPresenter.createUserOrder(params);
            }
        });

        bindView(R.id.ll_address, view -> getLaunchHelper().startActivityForResult(MineAddressListActivity.class, 1));

        assert dealway != null;
        switch (dealway) {
            case "1":
                bindText(R.id.tv_dealway, "自提");
                break;
            case "2":
                bindText(R.id.tv_dealway, "送货上门¥" + merchandiseDetailBean.postage);
                break;
            case "3":
                bindText(R.id.tv_dealway, "邮寄¥" + merchandiseDetailBean.postage);
                break;
        }
    }

    @Override
    public void createUserOrderSuccess(CreateUserOrderBean createUserOrderBean) {
        ToastUtils.show("创建订单成功");
    }

    @Override
    public void getFirstAddress(FirstAddressBean firstAddressBean) {
        if (firstAddressBean != null) {
            if (firstAddressBean.id != null) {
                addressId = firstAddressBean.id;
                bindText(R.id.tv_nick_name, firstAddressBean.userName);
                bindText(R.id.tv_mobile, firstAddressBean.mobile);
                bindText(R.id.tv_address, firstAddressBean.address + firstAddressBean.specificAddress);
            } else {

            }
        } else {
            Log.e("地址", "空");
        }
    }
}
