package com.nhbs.fenxiao.http.interceptor;

import com.nhbs.fenxiao.utils.ActivityManager;
import com.xuexiang.xhttp2.interceptor.BaseExpiredInterceptor;
import com.xuexiang.xhttp2.model.ApiResult;
import com.xuexiang.xhttp2.model.ExpiredInfo;
import com.xuexiang.xhttp2.utils.HttpUtils;
import com.xuexiang.xhttp2.utils.JSONUtils;
import okhttp3.Response;

public class CustomExpiredInterceptor extends BaseExpiredInterceptor {

    private final static int TOKEN_EXCEPTION_CODE = 9994;


    @Override
    protected ExpiredInfo isResponseExpired(Response oldResponse, String bodyString) {
        int code = JSONUtils.getInt(bodyString, ApiResult.CODE, 0);
        ExpiredInfo expiredInfo = new ExpiredInfo(code);
        switch (code) {
            case TOKEN_EXCEPTION_CODE:
                expiredInfo.setExpiredType(9994);
                expiredInfo.setBodyString(bodyString);
                break;
            default:
        }
        return expiredInfo;
    }

    @Override
    protected Response responseExpired(Response oldResponse, Chain chain, ExpiredInfo expiredInfo) {
        Response response = null;
        switch (expiredInfo.getExpiredType()) {
            case TOKEN_EXCEPTION_CODE:
                response = HttpUtils.getErrorResponse(oldResponse, expiredInfo.getCode(), "登录已经失效，请重新登录！");
                ActivityManager.getInstance().reLogin();
                break;
            default:
        }
        return response;
    }
}
