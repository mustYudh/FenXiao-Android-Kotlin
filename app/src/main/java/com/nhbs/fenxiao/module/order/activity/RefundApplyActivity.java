package com.nhbs.fenxiao.module.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseActivity;
import com.nhbs.fenxiao.module.order.activity.presenter.RefundApplyPresenter;
import com.nhbs.fenxiao.module.order.activity.presenter.RefundApplyViewer;
import com.nhbs.fenxiao.module.order.adapter.AddEvidenceAdapter;
import com.nhbs.fenxiao.module.order.adapter.RefundTypePopListAdapter;
import com.nhbs.fenxiao.module.order.bean.RefundTypeBean;
import com.nhbs.fenxiao.module.order.dialog.ChooseRefundTypeDialog;
import com.nhbs.fenxiao.module.view.RecycleItemSpace;
import com.nhbs.fenxiao.utils.DensityUtils;
import com.yu.common.glide.ImageLoader;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author myx
 * @date on 2019-12-01
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email mayuxuan@innobuddy.com
 */
public class RefundApplyActivity extends BaseActivity implements RefundApplyViewer {

    @PresenterLifeCycle
    RefundApplyPresenter mPresenter = new RefundApplyPresenter(this);
    private String id;
    private String imgUrl;
    private String name;
    private String type;
    private String price;
    private String expressPrice;
    private ImageView img_ico;
    private EditText edit_number;
    private EditText edit_explain;
    private int isGoods = -1;
    private int goodsStatus = -1;
    private String reason = "";
    private String imageUrls = "";
    private List<RefundTypeBean> listStatus = new ArrayList<>();
    private List<RefundTypeBean> listReason = new ArrayList<>();
    private ChooseRefundTypeDialog dialog_status;
    private ChooseRefundTypeDialog dialog_reason;
    private RecyclerView recycle_img;
    private AddEvidenceAdapter mAdapter = new AddEvidenceAdapter();
    private List<String> listImgs = new ArrayList<>();


    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_refund);
    }

    @Override
    protected void loadData() {
        setTitle("退款申请");
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("ORDER_ID");//商品订单id
        imgUrl = bundle.getString("IMG_URL");//商品图片地址
        name = bundle.getString("NAME");//商品名称
        type = bundle.getString("TYPE");//分类名称
        price = bundle.getString("PRICE");//总价(含邮费)
        expressPrice = bundle.getString("EXPRESS_PRICE");//邮费
        initListStatus();
        initListReason();
        img_ico = bindView(R.id.img_ico);
        ImageLoader.getInstance().displayImage(img_ico, imgUrl);
        edit_number = bindView(R.id.edit_number);
        edit_number.setHint("请修改，最多¥" + price + "，含发货邮费" + expressPrice);
        edit_explain = bindView(R.id.edit_explain);
        recycle_img = bindView(R.id.recycle_img);

        recycle_img.addItemDecoration(new RecycleItemSpace(8,0));
        GridLayoutManager manager = new GridLayoutManager(RefundApplyActivity.this, 4);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle_img.setLayoutManager(manager);
        recycle_img.setAdapter(mAdapter);
        mAdapter.addData("");
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.delete_photo) {
                    mAdapter.remove(position);
                }else if (view.getId() == R.id.add_photo) {
                    if (position == adapter.getItemCount() - 1 && TextUtils.isEmpty(adapter.getData().get(position).toString()) && adapter.getItemCount() < 3) {
                        int max = 3 - adapter.getItemCount();
                        PictureSelector.create(RefundApplyActivity.this)
                                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                                .maxSelectNum(max)// 最大图片选择数量 int.imageSpanCount(4)// 每行显示个数 int
                                .selectionMode( max > 0 ? PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE.previewImage(true)// 是否可预览图片 true or false
                                .previewVideo(true)// 是否可预览视频 true or false
                                .enablePreviewAudio(true) // 是否可播放音频 true or false
                                .isCamera(true)// 是否显示拍照按钮 true or false
                                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                                .isZoomAnim(false)// 图片列表点击 缩放效果 默认true
                                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                                .enableCrop(false)// 是否裁剪 true or false
                                .compress(true)// 是否压缩 true or false
                                .isGif(false)// 是否显示gif图片 true or false
                                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                                .minimumCompressSize(100)// 小于100kb的图片不压缩
                                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                                .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
                                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
                                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                    }

                }
            }
        });

        bindText(R.id.text_name, name);
        bindText(R.id.text_price, "¥" + price);
        bindText(R.id.text_goods_type, "分类: " + type);
        bindView(R.id.text_no_need, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bindSelector(R.id.text_no_need, true);
                bindSelector(R.id.text_need, false);
                isGoods = 0;
            }
        });
        bindView(R.id.text_need, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bindSelector(R.id.text_no_need, false);
                bindSelector(R.id.text_need, true);
                isGoods = 1;
            }
        });
        bindView(R.id.text_confirm, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isGoods == -1) {
                    ToastUtils.show("请选择服务类型");
                }else if (goodsStatus == -1) {
                    ToastUtils.show("请选择货物状态");
                }else if (TextUtils.isEmpty(edit_number.getText().toString())) {
                    ToastUtils.show("请填写金额");
                }else if (Double.parseDouble(edit_number.getText().toString()) > Double.parseDouble(price)) {
                    ToastUtils.show("填写金额超出订单金额");
                }else {

                    List<String> listData = mAdapter.getData();
                    imageUrls = "";
                    if (listData.size() > 0) {
                        for (String s : listData) {
                            imageUrls += "," + s;
                        }
                        imageUrls = imageUrls.substring(1);
                    }

                    mPresenter.applyRefund(id, edit_explain.getText().toString(),reason,imageUrls,isGoods,goodsStatus);
                }
            }
        });
        bindView(R.id.ll_status, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog_status == null) {
                    dialog_status = new ChooseRefundTypeDialog(RefundApplyActivity.this, R.style.dialog);
                    dialog_status.setCancelable(false);
                    TextView text_cancel = dialog_status.findViewById(R.id.text_cancel);
                    RecyclerView recycle = dialog_status.findViewById(R.id.recycle);
                    recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
                    RefundTypePopListAdapter adapter = new RefundTypePopListAdapter(R.layout.item_choose_refund_type, listStatus);
                    recycle.setAdapter(adapter);
                    adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            if (listStatus.get(position).isChecked()) {
                                listStatus.get(position).setChecked(false);
                                goodsStatus = -1;
                            }else {
                                listStatus.get(position).setChecked(true);
                                if (listStatus.get(position).getName().equals("未收到货")) {
                                    goodsStatus = 0;
                                }else {
                                    goodsStatus = 1;
                                }
                            }
                            adapter.notifyDataSetChanged();
                            dialog_status.dismiss();
                        }
                    });
                    text_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog_status.dismiss();
                        }
                    });
                    dialog_status.show();
                }else {
                    dialog_status.show();
                }
            }
        });
        bindView(R.id.ll_reason, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog_reason == null) {
                    dialog_reason = new ChooseRefundTypeDialog(RefundApplyActivity.this, R.style.dialog);
                    dialog_reason.setCancelable(false);
                    TextView text_title = dialog_reason.findViewById(R.id.text_title);
                    text_title.setText("退款原因");
                    TextView text_cancel = dialog_reason.findViewById(R.id.text_cancel);
                    RecyclerView recycle = dialog_reason.findViewById(R.id.recycle);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) recycle.getLayoutParams();
                    params.height = DensityUtils.dp2px(getActivity(), 300);
                    recycle.setLayoutParams(params);
                    recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
                    RefundTypePopListAdapter adapter = new RefundTypePopListAdapter(R.layout.item_choose_refund_type, listReason);
                    recycle.setAdapter(adapter);
                    adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            if (listReason.get(position).isChecked()) {
                                listReason.get(position).setChecked(false);
                                reason = "";
                            }else {
                                listReason.get(position).setChecked(true);
                                reason = listReason.get(position).getName();
                            }
                            adapter.notifyDataSetChanged();
                            dialog_reason.dismiss();
                        }
                    });
                    text_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog_reason.dismiss();
                        }
                    });
                    dialog_reason.show();
                }else {
                    dialog_reason.show();
                }
            }
        });
    }

    private void initListStatus() {
        List<String> list = new ArrayList<>();
        list.add("未收到货");
        list.add("已收到货");
        for (String s: list) {
            RefundTypeBean bean = new RefundTypeBean();
            bean.setName(s);
            listStatus.add(bean);
        }
    }

    private void initListReason() {
        List<String> list = new ArrayList<>();
        list.add("尺寸拍错/不喜欢/效果不好");
        list.add("商品成分描述不符");
        list.add("质量问题");
        list.add("材质不符");
        list.add("尺寸问题");
        list.add("生产日期/保质期不符");
        list.add("颜色/款式/型号描述不符");
        list.add("配件破损");
        list.add("卖家发错货");
        list.add("假冒品牌");
        list.add("主商品破损");
        list.add("其他");
        for (String s: list) {
            RefundTypeBean bean = new RefundTypeBean();
            bean.setName(s);
            listReason.add(bean);
        }
    }

    @Override
    public void applySuc() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList != null && selectList.size() > 0) {
                    List<String> list = new ArrayList<>();
                    for (LocalMedia media : selectList) {
                        list.add(media.getCompressPath());
                    }
                    mAdapter.setNewData(list);
                }
            }
        }
    }
}
