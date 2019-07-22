package com.nhbs.fenxiao.http.api

import com.nhbs.fenxiao.module.center.bean.GoodsTypeBean
import com.nhbs.fenxiao.module.login.bean.LoginInfoBean
import com.nhbs.fenxiao.utils.oss.bean.OssConfig
import com.xuexiang.xhttp2.annotation.NetMethod
import com.xuexiang.xhttp2.model.ApiResult
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * @author yudenghao
 * @date 2019-07-08
 */
interface AppApiServices {

  @get:NetMethod(Url = "/upload/param")
  val ossConfig: Observable<OssConfig>

  /**
   * 获取验证码
   *
   * @param params
   * @return
   */
  @NetMethod(ParameterNames = ["mobile"], Url = "/sms/send")
  fun sendVerCode(params: String): Observable<Any>

  @NetMethod(ParameterNames = ["mobile", "code", "invitePeopleCode"], Url = "/app/login")
  fun login(mobile: String, code: String, invitePeopleCode: String): Observable<LoginInfoBean>


  @POST("/api/merchandise/insert")
  @Headers("Content-Type: application/json", "Accept: application/json")
  fun releaseGoods(@Body params: RequestBody): Observable<ApiResult<Any>>


  @NetMethod(Url = "/merchandiseClass/list")
  fun getGoodsType(): Observable<GoodsTypeBean>


  @NetMethod(Url = "/advertisingType/list")
  fun getAdType(): Observable<GoodsTypeBean>


  @NetMethod(Url = "/app/winXinlogin")
  fun wechatLogin(openId: String, nickName: String, headImg: String): Observable<LoginInfoBean>
}
