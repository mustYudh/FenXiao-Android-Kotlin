package com.nhbs.fenxiao.http.api;

import com.nhbs.fenxiao.module.home.bean.AdvertisingInfoBean;
import com.nhbs.fenxiao.module.home.bean.AdvertisingShareBean;
import com.nhbs.fenxiao.module.home.bean.AdvertisingTypeBean;
import com.nhbs.fenxiao.module.home.bean.HomeBannerBean;
import com.nhbs.fenxiao.module.home.bean.HomeFindActivtyListBean;
import com.nhbs.fenxiao.module.home.bean.HomeFindAdvertisingListBean;
import com.nhbs.fenxiao.module.home.bean.HomeHotAdvertiseBean;
import com.nhbs.fenxiao.module.mine.bean.MineAddressBean;
import com.nhbs.fenxiao.module.mine.bean.MineUserInfoBean;
import com.nhbs.fenxiao.module.order.bean.CreateUserOrderBean;
import com.nhbs.fenxiao.module.order.bean.FirstAddressBean;
import com.nhbs.fenxiao.module.order.bean.MineOrderListBean;
import com.nhbs.fenxiao.module.order.bean.PayInfo;
import com.nhbs.fenxiao.module.product.bean.FindMerchandiseListBean;
import com.nhbs.fenxiao.module.product.bean.MerchandiseClassBean;
import com.nhbs.fenxiao.module.product.bean.MerchandiseDetailBean;
import com.nhbs.fenxiao.module.product.bean.ShareMerchandiseBean;
import com.xuexiang.xhttp2.annotation.NetMethod;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author yudneghao
 * @date 2019-07-08
 */
public interface OtherApiServices {
    @NetMethod(Url = "/userAddress/findListByUserId")
    Observable<MineAddressBean> getUserAddress();

    @NetMethod(ParameterNames = {"userName", "mobile", "address", "specificAddress", "type"}, Url = "/userAddress/add")
    Observable<Object> userAddressAdd(String userName, String mobile, String address, String specificAddress, int type);

    @NetMethod(ParameterNames = {"userName", "mobile", "address", "specificAddress", "id", "type"}, Url = "/userAddress/edit")
    Observable<Object> userAddressEdit(String userName, String mobile, String address, String specificAddress, String id, int type);

    @NetMethod(ParameterNames = {"id"}, Url = "/userAddress/del")
    Observable<Object> userAddressDel(String id);

    @NetMethod(Url = "/userAddress/firstAddress")
    Observable<FirstAddressBean> getFirstAddress();

    @NetMethod(ParameterNames = {"context", "mobile", "conUrl", "type"}, Url = "/opinion/add")
    Observable<Object> opinionAdd(String context, String mobile, String conUrl, String type);

    @NetMethod(Url = "/merchandiseClass/list")
    Observable<MerchandiseClassBean> merchandiseClass();

    @NetMethod(ParameterNames = {"classId", "pageNum", "pageSize"}, Url = "/merchandise/findMerchandiseList")
    Observable<FindMerchandiseListBean> findMerchandiseList(String classId, int pageNum, int pageSize);

    @NetMethod(ParameterNames = {"type", "pageNum", "pageSize"}, Url = "/merchandise/findMerchandiseList")
    Observable<FindMerchandiseListBean> homeFindMerchandiseList(String type, int pageNum, int pageSize);

    @NetMethod(Url = "/user/getUserInfo")
    Observable<MineUserInfoBean> userInfo();

    @NetMethod(Url = "/banner/lists")
    Observable<HomeBannerBean> bannerList();

    @NetMethod(ParameterNames = {"province", "city", "district"}, Url = "/advertising/getHotAdvertise")
    Observable<HomeHotAdvertiseBean> getHotAdvertise(String province, String city, String district);

    @NetMethod(ParameterNames = {"typeId", "pageNum", "pageSize"}, Url = "/advertising/findAdvertising")
    Observable<HomeFindAdvertisingListBean> findAdvertisingList(String typeId, int pageNum, int pageSize);

    @NetMethod(Url = "/advertisingType/list")
    Observable<AdvertisingTypeBean> advertisingType();

    @NetMethod(ParameterNames = {"pageNum", "pageSize"}, Url = "/activity/findActivtyPage")
    Observable<HomeFindActivtyListBean> findActivtyList(int pageNum, int pageSize);

    @NetMethod(ParameterNames = {"id"}, Url = "/merchandise/merchandiseDetail")
    Observable<MerchandiseDetailBean> merchandiseDetail(String id);

    @NetMethod(ParameterNames = {"id"}, Url = "/merchandise/agentMerchandise")
    Observable<Object> agentMerchandise(String id);

    @POST("/api/create/userOrder")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Observable<CreateUserOrderBean> createUserOrder(@Body RequestBody requestBody, @Header("token") String token);

    @NetMethod(ParameterNames = {"orderId", "type", "payWay"}, Url = "/user/toPay")
    Observable<PayInfo> userToPay(String orderId, String type, String payWay);

    @NetMethod(ParameterNames = {"pageNum", "pageSize", "type", "status"}, Url = "/order/queryMyOrders")
    Observable<MineOrderListBean> queryMyOrders(String pageNum, String pageSize, String type, String status);

    @NetMethod(ParameterNames = {"id"}, Url = "/advertising/advertiseShare")
    Observable<AdvertisingShareBean> advertiseShare(String id);

    @NetMethod(ParameterNames = {"id"}, Url = "/advertising/getAdvertiseInfo")
    Observable<AdvertisingInfoBean> getAdvertiseInfo(String id);

    @NetMethod(ParameterNames = {"id"}, Url = "/merchandise/shareMerchandise")
    Observable<ShareMerchandiseBean> shareMerchandise(String id);

    @NetMethod(ParameterNames = {"id"}, Url = "/activity/activityShare")
    Observable<ShareMerchandiseBean> activityShare(String id);

}
