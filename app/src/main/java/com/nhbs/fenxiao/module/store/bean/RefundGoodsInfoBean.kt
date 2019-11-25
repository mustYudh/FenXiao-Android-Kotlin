package com.nhbs.fenxiao.module.store.bean

import java.io.Serializable

/**
 * @author yudneghao
 * @date 2019-09-16
 */
data class RefundGoodsInfoBean(
    var id: String,
    var orderId: String,
    var reason: String,
    var createTime: String,
    var remark: String
) : Serializable
