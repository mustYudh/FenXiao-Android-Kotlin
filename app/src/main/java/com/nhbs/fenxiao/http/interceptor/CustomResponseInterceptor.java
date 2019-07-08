package com.nhbs.fenxiao.http.interceptor;

import com.google.gson.Gson;
import com.xuexiang.xhttp2.interceptor.BaseResponseInterceptor;
import com.xuexiang.xhttp2.model.ApiResult;
import com.xuexiang.xhttp2.utils.JSONUtils;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author yudneghao
 * @date 2019-04-28
 */
public class CustomResponseInterceptor extends BaseResponseInterceptor {

  @Override protected Response onAfterRequest(Response response, Chain chain, String bodyString) {
    ResponseBody responseBody = response.body();
    assert responseBody != null;
    try {
      MediaType mediaType = responseBody.contentType();
      ApiResult result = JSONUtils.parseJsonToBean(bodyString, ApiResult.class);
      Gson gson = new Gson();
      if (result.getData() == null) {
        result.setData(new Object());
      }
      String json = gson.toJson(result);
      return response.newBuilder().body(ResponseBody.create(mediaType, json)).build();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
