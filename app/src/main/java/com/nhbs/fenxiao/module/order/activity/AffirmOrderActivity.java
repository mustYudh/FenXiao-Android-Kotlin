package com.nhbs.fenxiao.module.order.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.mine.activity.MineAddressListActivity;
import com.nhbs.fenxiao.module.mine.bean.MineAddressBean;
import com.nhbs.fenxiao.module.order.activity.presenter.AffirmOrderPresenter;
import com.nhbs.fenxiao.module.order.activity.presenter.AffirmOrderViewer;
import com.nhbs.fenxiao.module.order.bean.CreateOrderParams;
import com.nhbs.fenxiao.module.order.bean.CreateUserOrderBean;
import com.nhbs.fenxiao.module.order.bean.FirstAddressBean;
import com.nhbs.fenxiao.module.order.bean.PayInfo;
import com.nhbs.fenxiao.module.product.bean.MerchandiseDetailBean;
import com.nhbs.fenxiao.utils.DialogUtils;
import com.nhbs.fenxiao.utils.PayUtils;
import com.yu.common.glide.ImageLoader;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.CircleImageView;
import com.yu.common.ui.DelayClickTextView;

import java.math.BigDecimal;

/**
 * 确认订单
 */
@SuppressLint("SetTextI18n")
public class AffirmOrderActivity extends BaseBarActivity implements AffirmOrderViewer {

    @PresenterLifeCycle
    AffirmOrderPresenter mPresenter = new AffirmOrderPresenter(this);
    private int number;
    private DialogUtils payDialog;
    private CircleImageView iv_shop;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_affirm_order_view);
    }

    private String addressId = "";

    @Override
    protected void loadData() {
        setTitle("确认订单");
        Bundle bundle = getIntent().getExtras();
        String merchandisedetail = bundle.getString("MERCHANDISEDETAILBEAN");
        String dealway = bundle.getString("DEALWAY");
        number = bundle.getInt("NUMBER");
        String onetag = bundle.getString("ONETAG");
        String twotag = bundle.getString("TWOTAG");
        iv_shop = bindView(R.id.iv_shop);
        if (dealway != null && "1".equals(dealway)) {
            //自提
            bindView(R.id.ll_address, false);
            bindView(R.id.ll_address_empty, false);
            bindView(R.id.ll_address_ziti, true);
        } else {
            mPresenter.getFirstAddress();
        }
        Gson gson = new Gson();
        MerchandiseDetailBean merchandiseDetailBean = gson.fromJson(merchandisedetail, MerchandiseDetailBean.class);
        ImageView iv_product = bindView(R.id.iv_product);
        if (merchandiseDetailBean.mImgs != null) {
            String[] split = merchandiseDetailBean.mImgs.split(",");
            ImageLoader.getInstance().displayImage(iv_product, split[0], R.drawable.ic_placeholder, R.drawable.ic_placeholder);
        }
        bindText(R.id.tv_product, merchandiseDetailBean.mName);
        TextView tv_specification = bindView(R.id.tv_specification);
        if (twotag != null && !TextUtils.isEmpty(twotag)) {
            tv_specification.setText("分类:" + onetag + ";" + twotag);
        } else {
            tv_specification.setText("分类:" + onetag);
        }


        bindText(R.id.tv_num, number + "");
        bindText(R.id.tv_shop_name, merchandiseDetailBean.shopName + "");
        bindText(R.id.tv_shop_address, merchandiseDetailBean.province + merchandiseDetailBean.city + "");
        ImageLoader.getInstance().displayImage(iv_shop, merchandiseDetailBean.shopImage, R.drawable.ic_placeholder, R.drawable.ic_placeholder);
        bindView(R.id.tv_commit, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateOrderParams params = new CreateOrderParams();
                params.orderId = merchandiseDetailBean.id;
                params.title = merchandiseDetailBean.mName;
                params.type = "2";
                params.price = merchandiseDetailBean.mPrice + "";
                params.number = number;
                if (onetag != null && !TextUtils.isEmpty(onetag)) {
                    params.color = onetag;
                }
                if (twotag != null && !TextUtils.isEmpty(twotag)) {
                    params.size = twotag;
                }
                params.dealWay = dealway;
                switch (dealway) {
                    case "1":
                        break;
                    case "2":
                        params.addressId = addressId;
                        params.delivery = (merchandiseDetailBean.delivery != null ? String.valueOf(merchandiseDetailBean.delivery) : "0");
                        break;
                    case "3":
                        params.addressId = addressId;
                        params.postage = (merchandiseDetailBean.postage != null ? String.valueOf(merchandiseDetailBean.postage) : "0");
                        break;
                }
                mPresenter.createUserOrder(params);
            }
        });

        bindView(R.id.ll_address, view -> getLaunchHelper().startActivityForResult(MineAddressListActivity.class, 1));
        bindView(R.id.ll_address_empty, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLaunchHelper().startActivityForResult(MineAddressListActivity.class, 1);
            }
        });

        BigDecimal num = new BigDecimal(number);
        bindText(R.id.tv_num_product, "共" + number + "件");
        bindText(R.id.tv_num_bottom, "共" + number + "件");

        bindText(R.id.tv_num, number + "");
        bindView(R.id.tv_add, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number++;
                BigDecimal num = new BigDecimal(number);
                bindText(R.id.tv_num_product, "共" + number + "件");
                bindText(R.id.tv_num_bottom, "共" + number + "件");
                switch (dealway) {
                    case "1":
                        bindText(R.id.tv_price, "¥" + (merchandiseDetailBean.mPrice.multiply(num)));
                        bindText(R.id.tv_price_bottom, "¥" + (merchandiseDetailBean.mPrice.multiply(num)));
                        break;
                    case "2":
                        bindText(R.id.tv_price, "¥" + ((merchandiseDetailBean.mPrice.multiply(num)).add(merchandiseDetailBean.delivery)));
                        bindText(R.id.tv_price_bottom, "¥" + ((merchandiseDetailBean.mPrice.multiply(num)).add(merchandiseDetailBean.delivery)));
                        break;
                    case "3":
                        bindText(R.id.tv_price, "¥" + ((merchandiseDetailBean.mPrice.multiply(num)).add(merchandiseDetailBean.postage)));
                        bindText(R.id.tv_price_bottom, "¥" + ((merchandiseDetailBean.mPrice.multiply(num)).add(merchandiseDetailBean.postage)));
                        break;
                }

                bindText(R.id.tv_num, number + "");
            }
        });

        bindView(R.id.tv_jian, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (number == 1) {
                    ToastUtils.show("数量不能少于1");
                    return;
                }
                number--;
                BigDecimal num = new BigDecimal(number);
                bindText(R.id.tv_num_product, "共" + number + "件");
                bindText(R.id.tv_num_bottom, "共" + number + "件");
                switch (dealway) {
                    case "1":
                        bindText(R.id.tv_price, "¥" + (merchandiseDetailBean.mPrice.multiply(num)));
                        bindText(R.id.tv_price_bottom, "¥" + (merchandiseDetailBean.mPrice.multiply(num)));
                        break;
                    case "2":
                        bindText(R.id.tv_price, "¥" + ((merchandiseDetailBean.mPrice.multiply(num)).add(merchandiseDetailBean.delivery)));
                        bindText(R.id.tv_price_bottom, "¥" + ((merchandiseDetailBean.mPrice.multiply(num)).add(merchandiseDetailBean.delivery)));
                        break;
                    case "3":
                        bindText(R.id.tv_price, "¥" + ((merchandiseDetailBean.mPrice.multiply(num)).add(merchandiseDetailBean.postage)));
                        bindText(R.id.tv_price_bottom, "¥" + ((merchandiseDetailBean.mPrice.multiply(num)).add(merchandiseDetailBean.postage)));
                        break;
                }
                bindText(R.id.tv_num, number + "");
            }
        });
        assert dealway != null;
        switch (dealway) {
            case "1":
                if (merchandiseDetailBean.mPrice != null) {
                    bindText(R.id.tv_price, "¥" + (merchandiseDetailBean.mPrice.multiply(num)));
                    bindText(R.id.tv_price_bottom, "¥" + (merchandiseDetailBean.mPrice.multiply(num)));
                }
                break;
            case "2":
                if (merchandiseDetailBean.mPrice != null) {
                    bindText(R.id.tv_price, "¥" + ((merchandiseDetailBean.mPrice.multiply(num)).add((merchandiseDetailBean.delivery != null ? merchandiseDetailBean.delivery : BigDecimal.valueOf(0)))));
                    bindText(R.id.tv_price_bottom, "¥" + ((merchandiseDetailBean.mPrice.multiply(num)).add(merchandiseDetailBean.delivery != null ? merchandiseDetailBean.delivery : BigDecimal.valueOf(0))));
                }
                break;
            case "3":
                if (merchandiseDetailBean.mPrice != null) {
                    bindText(R.id.tv_price, "¥" + ((merchandiseDetailBean.mPrice.multiply(num)).add(merchandiseDetailBean.postage != null ? merchandiseDetailBean.postage : BigDecimal.valueOf(0))));
                    bindText(R.id.tv_price_bottom, "¥" + ((merchandiseDetailBean.mPrice.multiply(num)).add(merchandiseDetailBean.postage != null ? merchandiseDetailBean.postage : BigDecimal.valueOf(0))));
                }
                break;
        }
        switch (dealway) {
            case "1":
                bindText(R.id.tv_dealway, "自提");
                break;
            case "2":
                bindText(R.id.tv_dealway, "送货上门¥" + (merchandiseDetailBean.delivery != null ? merchandiseDetailBean.delivery : "0"));
                break;
            case "3":
                bindText(R.id.tv_dealway, "邮寄¥" + (merchandiseDetailBean.postage != null ? merchandiseDetailBean.postage : "0"));
                break;
        }
    }

    @Override
    public void createUserOrderSuccess(CreateUserOrderBean createUserOrderBean) {
        if (createUserOrderBean != null) {
            showTypeDialog(createUserOrderBean);
        }
    }

    @Override
    public void getFirstAddress(FirstAddressBean firstAddressBean) {
        if (firstAddressBean != null) {
            if (firstAddressBean.id != null) {
                addressId = firstAddressBean.id;
                bindText(R.id.tv_nick_name, firstAddressBean.userName);
                bindText(R.id.tv_mobile, firstAddressBean.mobile);
                bindText(R.id.tv_address, firstAddressBean.address + firstAddressBean.specificAddress);
                bindView(R.id.ll_address, true);
                bindView(R.id.ll_address_empty, false);
                bindView(R.id.ll_address_ziti, false);
            } else {
                bindView(R.id.ll_address, false);
                bindView(R.id.ll_address_empty, true);
                bindView(R.id.ll_address_ziti, false);
            }
        } else {
            bindView(R.id.ll_address, false);
            bindView(R.id.ll_address_empty, true);
            bindView(R.id.ll_address_ziti, false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1:
                String item = data.getStringExtra("item");
                if (item != null && !TextUtils.isEmpty(item)) {
                    Gson gson = new Gson();
                    MineAddressBean.ListBean listBean = gson.fromJson(item, MineAddressBean.ListBean.class);
                    bindText(R.id.tv_nick_name, listBean.userName);
                    bindText(R.id.tv_mobile, listBean.mobile);
                    bindText(R.id.tv_address, listBean.address + listBean.specificAddress);

                    bindView(R.id.ll_address, true);
                    bindView(R.id.ll_address_empty, false);
                    bindView(R.id.ll_address_ziti, false);
                }
                break;

        }
    }

    private int type = 1;

    @SuppressLint("SetTextI18n")
    private void showTypeDialog(CreateUserOrderBean createUserOrderBean) {
        payDialog = new DialogUtils.Builder(getActivity()).view(R.layout.dialog_pay)
                .gravity(Gravity.BOTTOM)
                .cancelTouchout(true)
                .addViewOnclick(R.id.iv_close, view -> {
                    if (payDialog.isShowing()) {
                        payDialog.dismiss();
                    }
                })
                .style(R.style.Dialog)
                .build();
        payDialog.show();

        RelativeLayout rl_ali = payDialog.findViewById(R.id.rl_ali);
        RelativeLayout rl_wx = payDialog.findViewById(R.id.rl_wx);
        ImageView iv_ali = payDialog.findViewById(R.id.iv_ali);
        ImageView iv_wx = payDialog.findViewById(R.id.iv_wx);
        TextView tv_price = payDialog.findViewById(R.id.tv_price);
        tv_price.setText("¥" + createUserOrderBean.data.price);
        rl_ali.setOnClickListener(view -> {
            iv_ali.setImageResource(R.drawable.ic_circle_select);
            iv_wx.setImageResource(R.drawable.ic_circle_normal);
            type = 1;

        });

        rl_wx.setOnClickListener(view -> {
            iv_wx.setImageResource(R.drawable.ic_circle_select);
            iv_ali.setImageResource(R.drawable.ic_circle_normal);
            type = 2;
        });

        DelayClickTextView tv_commit = payDialog.findViewById(R.id.tv_commit);
        tv_commit.setOnClickListener(view -> mPresenter.userToPay(createUserOrderBean.data.id, "2", type + "", createUserOrderBean));
    }

    @Override
    public void userToPaySuccess(PayInfo payInfo, CreateUserOrderBean createUserOrderBean) {
        if (payDialog.isShowing()) {
            payDialog.dismiss();
        }
        PayUtils.getInstance().pay(getActivity(), type, payInfo)
                .getPayResult(new PayUtils.PayCallBack() {
                    @Override
                    public void onPaySuccess(int type) {
                        Bundle bundle = new Bundle();
                        bundle.putString("PRICE", createUserOrderBean.data.price + "");
                        getLaunchHelper().startActivity(PaySuccessActivity.class, bundle);
                        finish();
                    }

                    @Override
                    public void onFailed(int type) {
                        ToastUtils.show("支付失败，请重试");
                    }
                });
    }
}
