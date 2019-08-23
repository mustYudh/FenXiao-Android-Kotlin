package com.nhbs.fenxiao.module.store.bean

import java.io.Serializable

/**
 * @author yudenghao
 * @date 2019-08-19
 */
class GetGoodsParams : Serializable {
  var pageNum: Int? = 1
  var pageSize: Int? = 10
  var type: String? = "0"
  var classId: String? = ""
  var orderType: Int? = 1
}