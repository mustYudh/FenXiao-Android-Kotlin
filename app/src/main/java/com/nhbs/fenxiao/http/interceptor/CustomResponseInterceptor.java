package com.nhbs.fenxiao.http.interceptor;

import com.xuexiang.xhttp2.interceptor.BaseResponseInterceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;

/**
 * @author yudneghao
 * @date 2019-04-28
 */
public class CustomResponseInterceptor extends BaseResponseInterceptor {

    @Override
    protected Response onAfterRequest(Response response, Chain chain, String bodyString) {
        ResponseBody responseBody = response.body();
        assert responseBody != null;
        try {
            MediaType mediaType = responseBody.contentType();
            JSONObject jsonObject = new JSONObject(bodyString);
            JSONObject data = jsonObject.optJSONObject("data");
            if (data == null) {
                jsonObject.put("data", new JSONObject());
            }
            return response.newBuilder().body(ResponseBody.create(mediaType, jsonObject.toString())).build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
