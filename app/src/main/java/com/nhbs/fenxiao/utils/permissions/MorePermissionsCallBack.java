package com.nhbs.fenxiao.utils.permissions;

import com.tbruyelle.rxpermissions2.Permission;

/**
 * <pre>
 *     author : mks
 *     e-mail : xxx@xx
 *     time   : 2018/06/23
 *     desc   :
 * </pre>
 */

public abstract class MorePermissionsCallBack implements ApplyForPermission.ApplyForMorePermission {
    @Override
    public void onPermissionGranted(Permission permission) {
        // 用户已经同意该权限
        permissionGranted(permission);
    }

    @Override
    public void onPermissionShouldShowRequestPermissionRationale(Permission permission) {
        // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
        permissionShouldShowRequestPermissionRationale(permission);
    }

    @Override
    public void onPermissionRejected(Permission permission) {
        // 用户拒绝了该权限，并且选中『不再询问』
        permissionRejected(permission);
    }

    protected abstract void permissionGranted(Permission permission);

    protected abstract void permissionShouldShowRequestPermissionRationale(Permission permission);

    protected abstract void permissionRejected(Permission permission);
}
