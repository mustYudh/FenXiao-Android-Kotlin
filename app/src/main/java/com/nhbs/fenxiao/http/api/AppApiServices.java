package com.nhbs.fenxiao.http.api;

import com.nhbs.fenxiao.module.login.bean.LoginInfoBean;
import com.nhbs.fenxiao.utils.oss.bean.OssConfig;
import com.xuexiang.xhttp2.annotation.NetMethod;
import io.reactivex.Observable;

/**
 * @author yudenghao
 * @date 2019-07-08
 */
public interface AppApiServices {
    /**
     * 获取验证码
     *
     * @param params
     * @return
     */
    @NetMethod(ParameterNames = {"mobile"}, Url = "/sms/send")
    Observable<Object> sendVerCode(String params);

    @NetMethod(ParameterNames = {"mobile", "code", "invitePeopleCode"}, Url = "/app/login")
    Observable<LoginInfoBean> login(String mobile, String code, String invitePeopleCode);

    @NetMethod(Url = "/upload/param")
    Observable<OssConfig> getOssConfig();
}
