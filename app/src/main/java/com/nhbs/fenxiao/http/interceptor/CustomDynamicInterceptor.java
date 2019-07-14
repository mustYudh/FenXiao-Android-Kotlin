package com.nhbs.fenxiao.http.interceptor;

import android.text.TextUtils;
import android.util.Log;
import com.nhbs.fenxiao.data.UserProfile;
import com.nhbs.fenxiao.http.utils.AesEncryptUtils;
import com.xuexiang.xhttp2.interceptor.BaseDynamicInterceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

import java.io.IOException;
import java.util.TreeMap;

public class CustomDynamicInterceptor extends BaseDynamicInterceptor<CustomDynamicInterceptor> {

  @Override
  protected TreeMap<String, Object> updateDynamicParams(TreeMap<String, Object> dynamicMap) {
    return dynamicMap;
  }

  @Override public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();
    //参数就要针对body做操作,我这里针对From表单做操作
    if (request.body() instanceof MultipartBody) {
      return super.intercept(chain);
    } else if (request.body() instanceof RequestBody) {
      RequestBody body = request.body();
      Buffer buffer = new Buffer();
      body.writeTo(buffer);
      String params = buffer.readUtf8();
      Log.e("XHttp===>明文参数:", params);
      try {
        String requestParams =
            "{\"requestData\":" + "\"" + AesEncryptUtils.encrypt(params).trim() + "\"}";
        Log.e("XHttp===>加密参数:", requestParams.trim());
        RequestBody requestBody =
            RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),
                requestParams.trim());
        String token = UserProfile.getInstance().getAppToken();
        if (isAccessToken() && !TextUtils.isEmpty(token)) {
          return chain.proceed(
              request.newBuilder().addHeader("token", token).post(requestBody).build());
        } else {
          return chain.proceed(request.newBuilder().post(requestBody).build());
        }
      } catch (Exception e) {
        return super.intercept(chain);
      }
    } else {
      return super.intercept(chain);
    }
  }
}
