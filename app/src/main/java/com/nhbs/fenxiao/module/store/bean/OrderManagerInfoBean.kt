package com.nhbs.fenxiao.module.store.bean

import java.io.Serializable
import java.math.BigDecimal

/**
 * @author yudneghao
 * @date 2019-09-15
 */
data class OrderManagerInfoBean(
    var rows: List<OrderInfo>
) : Serializable

data class OrderInfo(
    var addressId: String,
    var createTime: Long,
    var dealWay: Int,
    var expressNumber: String,
    var goodsImage: String,
    var id: String,
    var mobile: String,
    var number: Int,
    var orderId: String,
    var price: BigDecimal,
    var receivingTime: Int,
    var shopId: String,
    var shopImage: String,
    var status: Int,
    var tagOne: String,
    var tagTwo: String,
    var title: String,
    var totalPrice: String,
    var postage: String,
    var type: Int,
    var updateTime: Long,
    var userId: String,
    var userName: String
) : Serializable

