package com.nhbs.fenxiao.module.center.bean

import java.io.Serializable

/**
 * @author yudneghao
 * @date 2019-12-01
 */
data class ReleaseAdesultBean(
    var auditReason: Any,
    var city: String,
    var content: String,
    var createTime: Long,
    var district: String,
    var endTime: Long,
    var grossSpread: String,
    var id: String,
    var imgs: String,
    var number: Any,
    var phoneNumber: String,
    var province: String,
    var pvSpread: Any,
    var shareType: Any,
    var shopId: Any,
    var startTime: Long,
    var status: Int,
    var title: String,
    var town: Any,
    var typeId: String,
    var userId: String
): Serializable