package com.nhbs.fenxiao.module.mine.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.Gravity;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.mine.activity.presenter.MineOpinionPresenter;
import com.nhbs.fenxiao.module.mine.activity.presenter.MineOpinionViewer;
import com.nhbs.fenxiao.module.mine.adapter.GridAdapter;
import com.nhbs.fenxiao.module.mine.bean.UploadImgBean;
import com.nhbs.fenxiao.module.view.ClearEditText;
import com.nhbs.fenxiao.utils.DialogUtils;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.NoSlidingGridView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MineOpinionActivity extends BaseBarActivity implements MineOpinionViewer {
    private List<LocalMedia> allLocationSelectedPicture = new ArrayList<>();
    @PresenterLifeCycle
    MineOpinionPresenter mPresenter = new MineOpinionPresenter(this);
    private GridAdapter adapter;
    private NoSlidingGridView gvPhoto;
    private String type = "";
    private DialogUtils typeDialog;
    private ArrayList<String> imageFiles = new ArrayList<>();
    private ClearEditText mContent;
    private ClearEditText mMobile;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_opinion_view);
    }

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

                mPresenter.opinionAdd(mContent.getText().toString().trim(), mMobile.getText().toString().trim(), urlNo.toString().trim(), type);

            } else if (msg.what == 1002) {

                ToastUtils.show("图片压缩失败,请重试");
            } else if (msg.what == 1003) {

                imageFiles.clear();
                ToastUtils.show("图片上传失败,请重试");
            }
        }
    };

    @SuppressLint("CheckResult")
    @Override
    protected void loadData() {
        setTitle("意见反馈");
        gvPhoto = bindView(R.id.gv_photo);
        adapter = new GridAdapter(getActivity(), allLocationSelectedPicture);
        gvPhoto.setAdapter(adapter);
        mContent = bindView(R.id.et_content);
        mMobile = bindView(R.id.et_mobile);

        bindView(R.id.tv_commit, view -> {
            for (int i = 0; i < allLocationSelectedPicture.size(); i++) {
                File imageFileCrmera = new File(allLocationSelectedPicture.get(i).getCompressPath());
                /** 上传图片*/
                new Compressor(getActivity())
                        .compressToFileAsFlowable(imageFileCrmera)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<File>() {
                            @Override
                            public void accept(File file) {
                                mPresenter.uploadImg(file);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                throwable.printStackTrace();
//                                        showError(throwable.getMessage());
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        handler.sendEmptyMessage(1002);//向消息队列发送一个标记
                                    }
                                }).start();
                            }
                        });
            }
        });

        bindView(R.id.rl_type, view -> {
            showTypeDialog();
        });
    }

    /**
     * 清空
     */
    private void showTypeDialog() {
        typeDialog = new DialogUtils.Builder(getActivity()).view(R.layout.dialog_type)
                .gravity(Gravity.BOTTOM)
                .addViewOnclick(R.id.tv1, view -> {
                    if (typeDialog.isShowing()) {
                        typeDialog.dismiss();
                    }
                    type = "购物相关";
                    bindText(R.id.tv_type, "购物相关");
                })
                .addViewOnclick(R.id.tv2, view -> {
                    if (typeDialog.isShowing()) {
                        typeDialog.dismiss();
                    }
                    type = "提现问题";
                    bindText(R.id.tv_type, "提现问题");
                })
                .addViewOnclick(R.id.tv3, view -> {
                    if (typeDialog.isShowing()) {
                        typeDialog.dismiss();
                    }
                    type = "信息错误";
                    bindText(R.id.tv_type, "信息错误");
                })
                .addViewOnclick(R.id.tv4, view -> {
                    if (typeDialog.isShowing()) {
                        typeDialog.dismiss();
                    }
                    type = "友好意见";
                    bindText(R.id.tv_type, "友好意见");
                })
                .addViewOnclick(R.id.tv5, view -> {
                    if (typeDialog.isShowing()) {
                        typeDialog.dismiss();
                    }
                    type = "其他";
                    bindText(R.id.tv_type, "其他");
                })
                .cancelTouchout(true)
                .style(R.style.Dialog)
                .build();
        typeDialog.show();
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
                    if (adapter != null) {
                        gvPhoto.setAdapter(adapter);
                    }
                    break;
                default:
            }
        }
    }

    @Override
    public void opinionAddSuccess() {
        imageFiles.clear();
        ToastUtils.show("意见反馈成功");
        finish();
    }

    @Override
    public void uploadImgSuccess(UploadImgBean uploadImgBean) {

    }

    @Override
    public void uploadImgFail() {
        imageFiles.clear();
        ToastUtils.show("图片上传失败,请重试");
    }

    @Override
    public void opinionAddFail() {
        imageFiles.clear();
        ToastUtils.show("意见反馈失败");
    }
}
