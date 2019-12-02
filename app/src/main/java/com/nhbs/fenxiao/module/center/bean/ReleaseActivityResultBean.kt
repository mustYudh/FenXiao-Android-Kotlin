package com.nhbs.fenxiao.module.center.bean

import java.io.Serializable

/**
 * @author yudneghao
 * @date 2019-12-02
 */
data class ReleaseActivityResultBean(
    var aName: String,
    var accessitImgs: String,
    var accessitName: String,
    var accessitNum: Int,
    var auditReason: Any,
    var city: String,
    var content: String,
    var createTime: Long,
    var district: String,
    var drawTime: Long,
    var firstPrizeImgs: String,
    var firstPrizeName: String,
    var firstPrizeNum: Int,
    var grossSpread: Double,
    var id: String,
    var isGeneralize: Int,
    var lotteryWinner: Any,
    var province: String,
    var pvPrice: Double,
    var shareType: Any,
    var shopId: String,
    var shopName: Any,
    var status: Int,
    var thirdPrizeImgs: String,
    var thirdPrizeName: String,
    var thirdPrizeNum: Int,
    var town: Any,
    var userId: String
):Serializable