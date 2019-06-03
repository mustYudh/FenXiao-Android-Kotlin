package com.nhbs.fenxiao.http.params;

import com.xuexiang.xhttp2.utils.JSONUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.util.HashMap;
import java.util.Map;

public class PostParams {
    private final static PostParams POST_PARAMS = new PostParams();

    private static Map<String, String> params = new HashMap<>();

    private PostParams() { }

    public static PostParams createParams() {
        params.clear();
        return POST_PARAMS;
    }

    public PostParams put(String key, String value) {
        params.put(key, value);
        return this;
    }

    public RequestBody creatBody() {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSONUtils.parseMapToJson(params));
        return body;
    }

}
