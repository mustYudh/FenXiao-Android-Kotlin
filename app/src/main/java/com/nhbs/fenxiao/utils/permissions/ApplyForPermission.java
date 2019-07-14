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

public interface ApplyForPermission {
    interface ApplyForOnePermission {

        void onOnePermissionGranted();

        void onOnePermissionRejected();
    }


    interface ApplyForMorePermission {
        void onPermissionGranted(Permission permission);

        void onPermissionShouldShowRequestPermissionRationale(Permission permission);

        void onPermissionRejected(Permission permission);
    }
}
