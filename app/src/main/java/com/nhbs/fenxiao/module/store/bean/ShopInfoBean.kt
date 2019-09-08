package com.nhbs.fenxiao.module.store.bean

import java.io.Serializable

/**
 * @author yudneghao
 * @date 2019-07-29
 */
data class ShopInfoBean(
    var city: String,
    var describes: String,
    var district: String,
    var nickName: Any,
    var ordersNum: Int,
    var priceNum: Int,
    var province: String,
    var shopName: String,
    var userId: String,
    var headImage: String,
    var usersNum: Int,
    var status: Int
) : Serializable