package com.nhbs.fenxiao.module.store.bean

import java.io.Serializable

/**
 * @author yudneghao
 * @date 2019-09-16
 */
data class ExpInfoBean(
    var orderInfo: OrderInfo,
    var `data`: List<ExpData>,
    var expSpellName: String,
    var expTextName: String,
    var flag: Boolean,
    var mailNo: String,
    var possibleExpList: List<Any>,
    var ret_code: Int,
    var status: Int,
    var tel: String,
    var update: Long,
    var updateStr: String
) : Serializable

data class ExpData(
    var context: String,
    var time: String
) : Serializable