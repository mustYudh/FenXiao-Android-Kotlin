package com.nhbs.fenxiao.module.mine.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.mine.activity.presenter.MineRedactAddressPresenter;
import com.nhbs.fenxiao.module.mine.activity.presenter.MineRedactAddressViewer;
import com.nhbs.fenxiao.module.mine.bean.JsonBean;
import com.nhbs.fenxiao.module.mine.bean.MineAddressBean;
import com.nhbs.fenxiao.utils.GetJsonDataUtil;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.DelayClickImageView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


public class MineRedactAddressActivity extends BaseBarActivity implements MineRedactAddressViewer {

    @PresenterLifeCycle
    MineRedactAddressPresenter mPresenter = new MineRedactAddressPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_redact_address_view);
    }

    private int type = 1;
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;

    private static boolean isLoaded = false;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    break;
            }
        }
    };

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.activity_mine_redact_address_view_bar;
    }

    @Override
    protected void loadData() {
        initJsonData();
        EditText et_name = bindView(R.id.et_name);
        EditText et_mobile = bindView(R.id.et_mobile);
        EditText et_address = bindView(R.id.et_address);
        TextView tv_city = bindView(R.id.tv_city);
        DelayClickImageView iv_type = bindView(R.id.iv_type);

        String item = getIntent().getStringExtra("item");
        if (item != null && !TextUtils.isEmpty(item)) {
            setTitle("编辑收货地址");
            bindView(R.id.tv_del, true);
            Gson gson = new Gson();
            MineAddressBean.ListBean listBean = gson.fromJson(item, MineAddressBean.ListBean.class);
            et_name.setText(listBean.userName);
            et_name.setSelection(et_name.getText().toString().trim().length());
            et_mobile.setText(listBean.mobile);
            et_mobile.setSelection(et_mobile.getText().toString().trim().length());
            tv_city.setText(listBean.address);
            et_address.setText(listBean.specificAddress);
            et_address.setSelection(et_address.getText().toString().trim().length());
            iv_type.setImageResource(listBean.type == 1 ? R.drawable.ic_guan : R.drawable.ic_kai);
            type = listBean.type;
            bindView(R.id.tv_commit, view -> {
                mPresenter.userAddressEdit(et_name.getText().toString().trim(), et_mobile.getText().toString().trim(), tv_city.getText().toString().trim(), et_address.getText().toString().trim(), listBean.id, type);
            });

            bindView(R.id.tv_del, view -> {
                mPresenter.userAddressDel(listBean.id);
            });
        } else {
            setTitle("新增收货地址");
            bindView(R.id.tv_del, false);
            bindView(R.id.tv_commit, view -> {
                mPresenter.userAddressAdd(et_name.getText().toString().trim(), et_mobile.getText().toString().trim(), tv_city.getText().toString().trim(), et_address.getText().toString().trim(), type);
            });
        }

        bindView(R.id.rl_city, view -> {
            if (isLoaded) {
                showPickerView();
            } else {
                ToastUtils.show("获取城市数据失败");
                initJsonData();
            }
        });

        bindView(R.id.iv_type, view -> {
            iv_type.setImageResource(type == 0 ? R.drawable.ic_guan : R.drawable.ic_kai);
            type = type == 0 ? 1 : 0;
        });
    }

    @Override
    public void userAddressAddSuccess() {
        Intent intent = new Intent();
        setResult(1, intent);
        finish();
    }

    @Override
    public void userAddressEditSuccess() {
        Intent intent = new Intent();
        setResult(1, intent);
        finish();
    }

    @Override
    public void userAddressDelSuccess() {
        Intent intent = new Intent();
        setResult(1, intent);
        finish();
    }

    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";

                String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";

                String tx = opt1tx + opt2tx + opt3tx;
                bindText(R.id.tv_city, tx);
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}
