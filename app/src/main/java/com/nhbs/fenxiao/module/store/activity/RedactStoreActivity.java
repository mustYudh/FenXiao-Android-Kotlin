package com.nhbs.fenxiao.module.store.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.widget.EditText;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.mine.bean.UploadImgBean;
import com.nhbs.fenxiao.module.store.activity.presenter.RedactStorePresenter;
import com.nhbs.fenxiao.module.store.activity.presenter.RedactStoreViewer;
import com.nhbs.fenxiao.utils.PhotoUtils;
import com.yu.common.glide.ImageLoader;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.CircleImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class RedactStoreActivity extends BaseBarActivity implements RedactStoreViewer {

    @PresenterLifeCycle
    RedactStorePresenter mPresenter = new RedactStorePresenter(this);
    private List<LocalMedia> allLocationSelectedPicture = new ArrayList<>();
    private CircleImageView iv_img;
    private ArrayList<String> imageFiles = new ArrayList<>();
    private String store_id;
    private EditText et_describes;
    private EditText et_name;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressLint("CheckResult")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //得到这个消息标记开始处理消息
            if (msg.what == 1001) {
                //处理消息
                final StringBuilder urlNo = new StringBuilder();
                if (imageFiles.size() != 0) {
                    for (int i = 0; i < imageFiles.size(); i++) {
                        urlNo.append(imageFiles.get(i));
                        if (i < imageFiles.size() - 1) {
                            urlNo.append(";");
                        }
                    }
                }

                mPresenter.userShopUpdate(store_id, urlNo.toString().trim(), et_name.getText().toString().trim(), et_describes.getText().toString().trim());

            } else if (msg.what == 1002) {

                ToastUtils.show("图片压缩失败,请重试");
            } else if (msg.what == 1003) {

                imageFiles.clear();
                ToastUtils.show("图片上传失败,请重试");
            }
        }
    };


    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_redact_store_view);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void loadData() {
        setTitle("小店资料");
        store_id = getIntent().getStringExtra("STORE_ID");
        iv_img = bindView(R.id.iv_img);
        et_describes = bindView(R.id.et_describes);
        et_name = bindView(R.id.et_name);

        bindView(R.id.rl_img, view -> PhotoUtils.changeAvatar(RedactStoreActivity.this, allLocationSelectedPicture, 1, "选择图片"));

        bindView(R.id.tv_commit, view -> {
            if (allLocationSelectedPicture.size() == 0) {
                ToastUtils.show("请选择小店图标");
                return;
            }
            if (TextUtils.isEmpty(et_name.getText().toString().trim())) {
                ToastUtils.show("请填写小店名称");
                return;
            }
            if (TextUtils.isEmpty(et_describes.getText().toString().trim())) {
                ToastUtils.show("请填写小店描述");
                return;
            }

            for (int i = 0; i < allLocationSelectedPicture.size(); i++) {
                File imageFileCrmera = new File(allLocationSelectedPicture.get(i).getCompressPath());
                /** 上传图片*/
                new Compressor(getActivity())
                        .compressToFileAsFlowable(imageFileCrmera)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(file -> mPresenter.uploadImg(file), throwable -> {
                            throwable.printStackTrace();
//                                        showError(throwable.getMessage());
                            new Thread(() -> {
                                handler.sendEmptyMessage(1002);//向消息队列发送一个标记
                            }).start();
                        });
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
//                    allLocationSelectedPicture.put(count, compressPath);
                    for (int i = 0; i < selectList.size(); i++) {
                        allLocationSelectedPicture.add(selectList.get(i));
                    }
                    ImageLoader.getInstance().displayImage(iv_img, allLocationSelectedPicture.get(0).getCompressPath());
                    break;
                default:
            }
        }
    }

    @Override
    public void uploadImgSuccess(UploadImgBean uploadImgBean) {
        if (uploadImgBean != null) {
            imageFiles.add(uploadImgBean.url + "");
            if (imageFiles.size() == allLocationSelectedPicture.size()) {
                new Thread(() -> {
                    handler.sendEmptyMessage(1001);//向消息队列发送一个标记
                }).start();
            }
        } else {
            new Thread(() -> {
                handler.sendEmptyMessage(1003);//向消息队列发送一个标记
            }).start();
        }
    }

    @Override
    public void uploadImgFail() {
        imageFiles.clear();
        ToastUtils.show("图片上传失败,请重试");
    }

    @Override
    public void userShopUpdateSuccess() {
        imageFiles.clear();
        ToastUtils.show("店铺信息修改成功");
        setResult(1);
        finish();
    }

    @Override
    public void userShopUpdateFail() {
        imageFiles.clear();
        ToastUtils.show("店铺信息修改失败");
    }
}
