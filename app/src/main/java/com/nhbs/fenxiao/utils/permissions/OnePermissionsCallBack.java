package com.nhbs.fenxiao.utils.permissions;

/**
 * Created by mks on 2018/06/09.
 * 申请权限回调类
 */

public abstract class OnePermissionsCallBack implements ApplyForPermission.ApplyForOnePermission {
    @Override
    public void onOnePermissionGranted() {
        // 用户已经同意该权限
        permissionGranted();
    }

    @Override
    public void onOnePermissionRejected() {
        // 用户拒绝了该权限，并且选中『不再询问』
        permissionRejected();
    }


    protected abstract void permissionGranted();
    protected abstract void permissionRejected();

}
