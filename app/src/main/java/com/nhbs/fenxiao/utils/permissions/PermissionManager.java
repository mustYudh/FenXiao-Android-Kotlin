package com.nhbs.fenxiao.utils.permissions;

import android.app.Activity;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;


/**
 * Created by zb on 2017/10/13.
 * 权限管理
 * 使用内部类来维护单例的实例，当PermissionManager被加载时，其内部类并不会被初始化，故可以确保当
 * PermissionManager类被载入JVM时，不会初始化单例类。只有 getInstance() 方法调用时，才会初始化INSTANCE。
 * 同时，由于实例的建立是时在类加载时完成，故天生对多线程友好，getInstance() 方法也无需使用同步关键字。
 */

public class PermissionManager {

    //private volatile  static PermissionManager INSTANCE;
    private static RxPermissions rxPermissions;
    private static class PermissionManagerHolder {
        private static final PermissionManager INSTANCE = new PermissionManager();
    }

    private PermissionManager() {}

    public static PermissionManager getInstance(Activity activity) {
        if (activity != null) {
            rxPermissions = new RxPermissions(activity);
        }
        return PermissionManagerHolder.INSTANCE;
    }

//    public void checkOnePermission(final OnePermissionsCallBack callBack, String... permissions) {
//        rxPermissions.request(permissions)
//                .subscribe(new Consumer<Boolean>() {
//                    @Override
//                    public void accept(Boolean aBoolean) throws Exception {
//                        if (aBoolean) {
//                            callBack.onOnePermissionGranted();
//                        }else {
//                            callBack.onOnePermissionRejected();
//                        }
//                    }
//                });
//    }

    public void checkMorePermission(final MorePermissionsCallBack callBack,String... permissions) {
        rxPermissions.requestEach(permissions)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            callBack.onPermissionGranted(permission);
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            callBack.onPermissionShouldShowRequestPermissionRationale(permission);
                        } else {
                            callBack.onPermissionRejected(permission);
                        }
                    }
                });
    }
}
