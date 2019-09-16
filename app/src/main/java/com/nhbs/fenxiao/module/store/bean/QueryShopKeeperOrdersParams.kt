package com.nhbs.fenxiao.module.store.bean

import java.io.Serializable

/**
 * @author yudneghao
 * @date 2019-09-14
 */
data class QueryShopKeeperOrdersParams(var status: Int = 1,
    var type: Int = 2, var pageNum: Int = 1,
    var pageSize: Int = 10, var searchTtile: String? = "") : Serializable