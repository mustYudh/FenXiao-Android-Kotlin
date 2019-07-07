package com.nhbs.fenxiao;

import com.nhbs.fenxiao.http.interceptor.CustomDynamicInterceptor;
import com.nhbs.fenxiao.http.interceptor.CustomLoggingInterceptor;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.XHttpSDK;
import com.xuexiang.xhttp2.model.HttpHeaders;
import com.yu.common.CommonInit;
import com.yu.common.base.BaseApp;
import com.yu.share.ShareAuthSDK;

/**
 * @author yudneghao
 * @date 2019-06-03
 */
public class APP extends BaseApp {
    public static final int NET_TYPE = BuildConfig.API_MODE;
    public static final boolean DEBUG = APP.NET_TYPE == 0;
    private static APP instance;
    @Override
    public void onCreate() {
        APP.instance = this;
        super.onCreate();
        CommonInit.init(this);
        ShareAuthSDK.init(this, DEBUG);
        initHttp();
    }

    private void initHttp() {
        XHttpSDK.init(this);
        if (DEBUG) {
            XHttpSDK.debug();
            XHttpSDK.debug(new CustomLoggingInterceptor());
        }
        XHttpSDK.setBaseUrl(getBaseUrl());
        XHttpSDK.setSubUrl(getSubUrl());
        XHttpSDK.addInterceptor(new CustomDynamicInterceptor());
//        XHttpSDK.addInterceptor(new CustomExpiredInterceptor());
        XHttp.getInstance().setTimeout(60000);
        XHttp.getInstance().setRetryCount(3);
        XHttp.getInstance().addCommonHeaders(getHttpHeaders());
    }


    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        return headers;
    }

    private String getBaseUrl() {
        if (APP.NET_TYPE == 1) {
            return "http://139.180.218.55:8070";
        } else if (APP.NET_TYPE == 2) {
            return "http://139.180.218.55:8070";
        } else {
            return "http://139.180.218.55:8070";
        }
    }

    public String getSubUrl() {
        return "";
    }

    public synchronized static APP getInstance() {
        return instance;
    }
}
