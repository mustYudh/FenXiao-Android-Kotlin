package com.nhbs.fenxiao.http.api

import com.nhbs.fenxiao.data.UserProfile
import com.nhbs.fenxiao.module.center.bean.GoodsTypeBean
import com.nhbs.fenxiao.module.login.bean.LoginInfoBean
import com.nhbs.fenxiao.module.store.bean.ActivityListInfo
import com.nhbs.fenxiao.module.store.bean.ExpInfoBean
import com.nhbs.fenxiao.module.store.bean.GoodsListBean
import com.nhbs.fenxiao.module.store.bean.OrderCountBean
import com.nhbs.fenxiao.module.store.bean.OrderManagerInfoBean
import com.nhbs.fenxiao.module.store.bean.ShopInfoBean
import com.nhbs.fenxiao.module.store.bean.TypeCountListBean
import com.nhbs.fenxiao.utils.oss.bean.OssConfig
import com.xuexiang.xhttp2.annotation.NetMethod
import com.xuexiang.xhttp2.model.ApiResult
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Header
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
  fun releaseGoods(@Body params: RequestBody, @Header(
      "token") token: String? = UserProfile.getInstance().appToken): Observable<ApiResult<Any>>


  @POST("/api/advertising/insert")
  @Headers("Content-Type: application/json;charset=UTF-8", "Accept: application/json")
  fun releaseAD(@Body params: RequestBody, @Header(
      "token") token: String? = UserProfile.getInstance().appToken): Observable<ApiResult<Any>>


  @NetMethod(Url = "/merchandiseClass/list")
  fun getGoodsType(): Observable<GoodsTypeBean>


  @NetMethod(Url = "/advertisingType/list")
  fun getAdType(): Observable<GoodsTypeBean>


  @POST("/api/app/winXinlogin")
  fun weChatLogin(@Body body: RequestBody): Observable<ApiResult<LoginInfoBean>>


  @NetMethod(Url = "/userShop/shopDetail")
  fun getShopInfo(): Observable<ShopInfoBean>

  @POST("/api/app/winXinRegister")
  fun weChatRegister(@Body params: RequestBody): Observable<ApiResult<LoginInfoBean>>

  @POST("/api/activity/insert")
  @Headers("Content-Type: application/json", "Accept: application/json")
  fun releaseActivity(@Body params: RequestBody, @Header(
      "token") token: String? = UserProfile.getInstance().appToken): Observable<ApiResult<Any>>


  @POST("/api/merchandise/findMerchandiseList")
  @Headers("Content-Type: application/json", "Accept: application/json")
  fun getGoodsList(@Body params: RequestBody, @Header(
      "token") token: String? = UserProfile.getInstance().appToken): Observable<ApiResult<GoodsListBean>>


  @NetMethod(Url = "/merchandise/pullDown", ParameterNames = ["id"])
  fun pullDownGoods(id: String): Observable<Any>


  @POST("/api/merchandise/update")
  @Headers("Content-Type: application/json", "Accept: application/json")
  fun editGoods(@Body params: RequestBody, @Header(
      "token") token: String? = UserProfile.getInstance().appToken): Observable<ApiResult<Any>>


  @NetMethod(ParameterNames = ["pageNum", "pageSize"], Url = "/activity/MyActivtyPage")
  fun getMyActivityList(pageNum: Int? = 1, pageSize: Int? = 10): Observable<ActivityListInfo>

  @NetMethod(ParameterNames = ["pageNum", "pageSize"], Url = "/merchandise/auditLogList")
  fun getAuditLogList(pageNum: Int? = 1, pageSize: Int? = 10): Observable<ActivityListInfo>


  @POST("/api/create/queryShopKeeperOrders")
  @Headers("Content-Type: application/json", "Accept: application/json")
  fun queryShopKeeperOrders(@Body body: RequestBody, @Header(
      "token") token: String? = UserProfile.getInstance().appToken): Observable<ApiResult<OrderManagerInfoBean>>


  @NetMethod(Url = "/merchandise/countByClass")
  fun getGoodsTypeCount(): Observable<TypeCountListBean>

  @NetMethod(ParameterNames = ["orderId", "expressNumber", "dealWay"],
      Url = "/order/goSendGoods")
  fun goSendGoods(orderId: String, expressNumber: String, dealWay: Int): Observable<Any>

  @NetMethod(ParameterNames = ["orderId","price","postage"],Url = "/order/updateOrderPrice")
  fun updateOrderPrice(orderId: String,price: String,postage: String): Observable<Any>

  @POST("/api/shop/getOrdersCount")
  @Headers("Content-Type: application/json", "Accept: application/json")
  fun getOrdersCount(@Body body: RequestBody, @Header(
      "token") token: String? = UserProfile.getInstance().appToken): Observable<ApiResult<OrderCountBean>>



  @NetMethod(ParameterNames = ["nu"],Url = "/findExp")
  fun findExp(nu: String): Observable<ExpInfoBean>
}
