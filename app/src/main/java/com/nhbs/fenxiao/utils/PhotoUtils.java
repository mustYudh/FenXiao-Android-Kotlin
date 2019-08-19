package com.nhbs.fenxiao.utils;

import android.Manifest;
import android.app.Activity;
import android.view.Gravity;
import android.view.View;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.utils.permissions.MorePermissionsCallBack;
import com.nhbs.fenxiao.utils.permissions.PermissionManager;
import com.tbruyelle.rxpermissions2.Permission;

import java.util.List;

public class PhotoUtils {
    public static DialogUtils mDialog;

    /**
     * 更换图片
     */
    public static void changeAvatar(Activity activity, List<LocalMedia> list, int num, String title) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_bottom:
                        if (mDialog.isShowing()) {
                            mDialog.dismiss();
                        }

                        //检测权限
                        PermissionManager.getInstance(activity).checkMorePermission(new MorePermissionsCallBack() {
                                                                                        @Override
                                                                                        protected void permissionGranted(Permission permission) {
                                                                                            // 用户已经同意该权限
                                                                                            if ("android.permission.WRITE_EXTERNAL_STORAGE".equals(permission.name)) {
                                                                                                //拍照获取照片
                                                                                                photoSelector(activity);
                                                                                            }
                                                                                        }

                                                                                        @Override
                                                                                        protected void permissionShouldShowRequestPermissionRationale(Permission permission) {
                                                                                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
//                                                                                                 ToastUtil.showCommonToast("请先开启权限");
                                                                                        }

                                                                                        @Override
                                                                                        protected void permissionRejected(Permission permission) {
//                                                                                                 ToastUtil.showCommonToast("您已拒绝手机存储权限,请先开启权限");
                                                                                        }
                                                                                    },
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA
                        );
                        break;
                    case R.id.tv_top:
                        if (mDialog.isShowing()) {
                            mDialog.dismiss();
                        }
                        //检测权限
                        PermissionManager.getInstance(activity).checkMorePermission(new MorePermissionsCallBack() {
                                                                                        @Override
                                                                                        protected void permissionGranted(Permission permission) {
                                                                                            // 用户已经同意该权限
                                                                                            if ("android.permission.WRITE_EXTERNAL_STORAGE".equals(permission.name)) {
                                                                                                //从相册获取照片
                                                                                                pictureSelector(activity, num - list.size());
                                                                                            }
                                                                                        }

                                                                                        @Override
                                                                                        protected void permissionShouldShowRequestPermissionRationale(Permission permission) {
                                                                                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
//                                                                                                 ToastUtil.showCommonToast("请先开启权限");
                                                                                        }

                                                                                        @Override
                                                                                        protected void permissionRejected(Permission permission) {
//                                                                                                 ToastUtil.showCommonToast("您已拒绝手机存储权限,请先开启权限");
                                                                                        }
                                                                                    },
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        );


                        break;
                    case R.id.tv_cancle:
                        if (mDialog.isShowing()) {
                            mDialog.dismiss();
                        }
                        break;
                }
            }
        };
        DialogUtils.Builder builder = new DialogUtils.Builder(activity);
        mDialog = builder.view(R.layout.dialog_normal)
                .gravity(Gravity.BOTTOM)
                .cancelTouchout(true)
                .style(R.style.Dialog)
                .settext(title, R.id.tv_title)
                .addViewOnclick(R.id.tv_top, listener)
                .addViewOnclick(R.id.tv_bottom, listener)
                .addViewOnclick(R.id.tv_cancle, listener)
                .build();
        mDialog.show();
    }

    //选择图片
    public static void pictureSelector(Activity context, int num) {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(context)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(num)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(false)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.JPEG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//                .setOutputCameraPath("/CarPooling/Image")// 自定义拍照保存路径,可不填
                .enableCrop(false)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(true)// 是否显示gif图片 true or false
//                .compressSavePath("/CarPooling/CompressImage")//压缩图片保存地址
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
//                .selectionMedia()// 是否传入已选图片 List<LocalMedia> rows
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    //选择图片
    public static void pictureSelector(Activity context) {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(context)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(false)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.JPEG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//                .setOutputCameraPath("/CarPooling/Image")// 自定义拍照保存路径,可不填
                .enableCrop(false)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(true)// 是否显示gif图片 true or false
//                .compressSavePath("/CarPooling/CompressImage")//压缩图片保存地址
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
//                .selectionMedia()// 是否传入已选图片 List<LocalMedia> rows
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    //拍照
    public static void photoSelector(Activity context) {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(context)
                .openCamera(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.JPEG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//                .setOutputCameraPath("/CarPooling/Image")// 自定义拍照保存路径,可不填
                .enableCrop(false)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(true)// 是否显示gif图片 true or false
//                .compressSavePath("/CarPooling/CompressImage")//压缩图片保存地址
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
//                .selectionMedia()// 是否传入已选图片 List<LocalMedia> rows
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }
}
