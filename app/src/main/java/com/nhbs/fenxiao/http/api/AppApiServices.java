package com.nhbs.fenxiao.http.api;

import com.xuexiang.xhttp2.annotation.NetMethod;
import io.reactivex.Observable;

/**
 * @author yudenghao
 * @date 2019-07-08
 */
public interface AppApiServices {
  @NetMethod(ParameterNames = {"mobile"},Url = "/sms/send")
  Observable<Object> sendSems(String params);
}
