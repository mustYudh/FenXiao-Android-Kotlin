package com.nhbs.fenxiao.http.api

import com.nhbs.fenxiao.data.UserProfile
import com.nhbs.fenxiao.module.center.bean.GoodsTypeBean
import com.nhbs.fenxiao.module.center.bean.ReleaseActivityResultBean
import com.nhbs.fenxiao.module.center.bean.ReleaseAdesultBean
import com.nhbs.fenxiao.module.home.bean.NimeInfoBean
import com.nhbs.fenxiao.module.login.bean.LoginInfoBean
import com.nhbs.fenxiao.module.order.bean.PayInfo
import com.nhbs.fenxiao.module.store.bean.*
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
      "token") token: String? = UserProfile.getInstance().appToken): Observable<ApiResult<ReleaseAdesultBean>>


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
      "token") token: String? = UserProfile.getInstance().appToken): Observable<ApiResult<ReleaseActivityResultBean>>


  @POST("/api/merchandise/findMerchandiseList")
  @Headers("Content-Type: application/json", "Accept: application/json")
  fun getGoodsList(@Body params: RequestBody, @Header(
          "token") token: String? = UserProfile.getInstance().appToken): Observable<ApiResult<GoodsListBean>>

  @POST("/api/merchandise/queryMyClassGoodsList")
  @Headers("Content-Type: application/json", "Accept: application/json")
  fun getGoodsListNew(@Body params: RequestBody, @Header(
          "token") token: String? = UserProfile.getInstance().appToken): Observable<ApiResult<GoodsListBean>>

  @POST("/api/merchandise/queryMyOtherClassGoodsList")
  @Headers("Content-Type: application/json", "Accept: application/json")
  fun getOtherGoodsList(@Body params: RequestBody, @Header(
          "token") token: String? = UserProfile.getInstance().appToken): Observable<ApiResult<OtherTypeGoodsBean>>

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


  @POST("/api/app/loginout")
  @Headers("Content-Type: application/json", "Accept: application/json")
  fun logout(@Body body: RequestBody, @Header(
      "token") token: String? = UserProfile.getInstance().appToken): Observable<ApiResult<Any>>



  @NetMethod(ParameterNames =["classify"],Url ="/merchandiseClass/merchandiseClass")
  fun merchandiseClass(classify: String): Observable<Any>



  @POST("/api/merchandise/findMyShopMerchandiseList")
  @Headers("Content-Type: application/json", "Accept: application/json")
  fun getMyShopGoodsList(@Body params: RequestBody, @Header(
          "token") token: String? = UserProfile.getInstance().appToken): Observable<ApiResult<GoodsListBean>>


  @NetMethod(Url = "/user/createNeteaseCommunicationUser")
  fun getNimeInfo():Observable<NimeInfoBean>


  @NetMethod(ParameterNames =["id","classify"],Url ="/merchandiseClass/updateClass")
  fun changeTypeName(id: String,classify: String): Observable<Any>


  @NetMethod(ParameterNames =["id"],Url ="/merchandise/deleteClass")
  fun deleteType(id: String): Observable<Any>

  @NetMethod(ParameterNames =["id","classId"],Url ="/merchandise/addClassGoods")
  fun typeAddGoods(id: String,classId:String): Observable<Any>

  @NetMethod(ParameterNames =["id","classId"],Url ="/merchandise/deleteClassGoods")
  fun typeDeleteGoods(id: String,classId:String): Observable<Any>

  @NetMethod(ParameterNames = ["orderId","refuseReason"],Url = "/order/refuseRefund")
  fun refuseRefund(orderId: String,refuseReason: String): Observable<Any>

  @NetMethod(ParameterNames = ["orderId"],Url = "/order/confirmRefund")
  fun confirmRefund(orderId: String): Observable<Any>

  @NetMethod(ParameterNames = ["orderId"],Url = "/query/getRefundInfo")
  fun getRefundInfo(orderId: String): Observable<RefundGoodsInfoBean>


  @NetMethod(Url = "/advertising/createAdvertisingOrder",ParameterNames = ["id","type"])
  fun getAdOrder(id: String,type: Int): Observable<PayInfo>


  @NetMethod(Url = "/activity/createActivityOrder",ParameterNames = ["id","type"])
  fun getActivityOrder(id: String,type: Int): Observable<PayInfo>
}



