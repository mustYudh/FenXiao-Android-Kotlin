package com.nhbs.fenxiao.utils.permissions;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import com.nhbs.fenxiao.R;
import com.tbruyelle.rxpermissions2.Permission;

/**
 * Created by mks on 2018/06/09.
 * 权限拒绝后设置页面
 */

public class RequestPermission {
    public static void requestPermissions(final Activity context, final PassPermiOperateListener listener, String... permissions){
        final String brand = Build.BRAND;
        PermissionManager.getInstance(context)
                .checkMorePermission(new MorePermissionsCallBack() {
                    @Override
                    protected void permissionGranted(Permission permission) {
                        listener.doSecureOperate();
                    }

                    @Override
                    protected void permissionShouldShowRequestPermissionRationale(Permission permission) {

                    }

                    @Override
                    protected void permissionRejected(Permission permission) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("在设置-应用-"+context.getString(R.string.app_name)+"-权限中开启相关权限,以正常使用相关功能")
                                .setCancelable(false)
                                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        if(brand.equalsIgnoreCase("xiaomi")){
                                            Intent intent = new Intent();
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                                            intent.setData(uri);
                                            context.startActivity(intent);
                                        }else {
                                            Intent appDetailSettingIntent = AppDetailSettingPage.getAppDetailSettingIntent(context);
                                            context.startActivity(appDetailSettingIntent);
                                        }
                                    }
                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                context.finish();
                            }
                        }).show();
                    }
                },permissions);
    }

    public static void requestPermissions(final Activity context, final PassPermiOperateListener listener, final OnDialogCancleListener dialogCancleListener, String... permissions){
        final String brand = Build.BRAND;
        PermissionManager.getInstance(context)
                .checkMorePermission(new MorePermissionsCallBack() {
                    @Override
                    protected void permissionGranted(Permission permission) {
                        listener.doSecureOperate();
                    }

                    @Override
                    protected void permissionShouldShowRequestPermissionRationale(Permission permission) {

                    }

                    @Override
                    protected void permissionRejected(Permission permission) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("在设置-应用-"+context.getString(R.string.app_name)+"-权限中开启相关权限,以正常使用相关功能")
                                .setCancelable(false)
                                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        if(brand.equalsIgnoreCase("xiaomi")){
                                            Intent intent = new Intent();
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                                            intent.setData(uri);
                                            context.startActivity(intent);
                                        }else {
                                            Intent appDetailSettingIntent = AppDetailSettingPage.getAppDetailSettingIntent(context);
                                            context.startActivity(appDetailSettingIntent);
                                        }
                                    }
                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (dialogCancleListener != null) {
                                    dialogCancleListener.onDialogCancle();
                                }
                            }
                        }).show();
                    }
                },permissions);
    }

    public interface OnDialogCancleListener {
        void onDialogCancle();
    }
}
