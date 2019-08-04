package com.nhbs.fenxiao.module.center.bean

import java.io.Serializable

/**
 * @author yudenghao
 * @date 2019-07-14
 */
data class GoodsTypeBean(
    var rows: List<Row>
): Serializable

data class Row(
    var name: String,
    var classify: String,
    var id: String,
    var selected: Boolean
) : Serializable