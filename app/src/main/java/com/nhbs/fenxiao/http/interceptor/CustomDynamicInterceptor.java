package com.nhbs.fenxiao.http.interceptor;

import com.xuexiang.xhttp2.interceptor.BaseDynamicInterceptor;
import okhttp3.Response;

import java.io.IOException;
import java.util.TreeMap;

public class CustomDynamicInterceptor extends BaseDynamicInterceptor<CustomDynamicInterceptor> {

    @Override
    protected TreeMap<String, Object> updateDynamicParams(TreeMap<String, Object> dynamicMap) {
        return dynamicMap;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return super.intercept(chain);
    }
}
