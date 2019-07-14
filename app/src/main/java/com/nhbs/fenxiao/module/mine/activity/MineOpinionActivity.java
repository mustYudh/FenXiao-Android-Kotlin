package com.nhbs.fenxiao.module.mine.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import com.yu.common.ui.NoSlidingGridView;

import java.util.ArrayList;
import java.util.List;


public class MineOpinionActivity extends BaseBarActivity implements MineOpinionViewer {
    private List<LocalMedia> allLocationSelectedPicture = new ArrayList<>();
    @PresenterLifeCycle
    MineOpinionPresenter mPresenter = new MineOpinionPresenter(this);
    private GridAdapter adapter;
    private NoSlidingGridView gvPhoto;
    private String type = "";
    private DialogUtils typeDialog;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_opinion_view);
    }

    @Override
    protected void loadData() {
        setTitle("意见反馈");
        gvPhoto = bindView(R.id.gv_photo);
        adapter = new GridAdapter(getActivity(), allLocationSelectedPicture);
        gvPhoto.setAdapter(adapter);
        ClearEditText mContent = bindView(R.id.et_content);
        ClearEditText mMobile = bindView(R.id.et_mobile);

        bindView(R.id.tv_commit, view -> {
            mPresenter.uploadImg();
//            mPresenter.opinionAdd();
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
                    type = "购物相关";
                })
                .addViewOnclick(R.id.tv2, view -> {
                    type = "提现问题";
                })
                .addViewOnclick(R.id.tv3, view -> {
                    type = "信息错误";
                })
                .addViewOnclick(R.id.tv4, view -> {
                    type = "友好意见";
                })
                .addViewOnclick(R.id.tv5, view -> {
                    type = "其他";
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

    }

    @Override
    public void uploadImgSuccess(UploadImgBean uploadImgBean) {

    }

    @Override
    public void uploadImgFail() {

    }
}
