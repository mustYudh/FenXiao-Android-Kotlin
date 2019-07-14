package com.nhbs.fenxiao.http.api;

import com.nhbs.fenxiao.module.mine.bean.MineAddressBean;
import com.xuexiang.xhttp2.annotation.NetMethod;

import io.reactivex.Observable;

/**
 * @author yudneghao
 * @date 2019-07-08
 */
public interface OtherApiServices {
    @NetMethod(Url = "/userAddress/findListByUserId")
    Observable<MineAddressBean> getUserAddress();

    @NetMethod(ParameterNames = {"userName", "mobile", "address", "specificAddress","type"}, Url = "/userAddress/add")
    Observable<Object> userAddressAdd(String userName, String mobile, String address, String specificAddress,int type);

    @NetMethod(ParameterNames = {"userName", "mobile", "address", "specificAddress","id","type"}, Url = "/userAddress/edit")
    Observable<Object> userAddressEdit(String userName, String mobile, String address, String specificAddress,String id,int type);

    @NetMethod(ParameterNames = {"id"}, Url = "/userAddress/del")
    Observable<Object> userAddressDel(String id);

}
