package com.nhbs.fenxiao.module.center.bean

import com.nhbs.fenxiao.utils.checkTextEmpty
import com.nhbs.fenxiao.utils.showToast
import java.io.Serializable

/**
 * @author yudneghao
 * @date 2019-08-04
 */
class ReleaseAdParams : Serializable {
  var type: String = "1"
  var title: String? = null
  var content: String? = null
  var imgs: String? = null
  var grossSpread: String? = null
  var pvSpread: String? = null
  var endTime: String? = null
  var typeId: String? = null
  var province: String? = null
  var city: String? = null
  var district: String? = null

  fun checkParams(): Boolean {
    if (title.checkTextEmpty()) {
      showToast("请输入广告标题")
      return false
    }
    if (content.checkTextEmpty()) {
      showToast("请输入广告内容")
      return false
    }
    if (endTime.checkTextEmpty()) {
      showToast("请选择广告截止时间")
      return false
    }
    if (typeId.checkTextEmpty()) {
      showToast("请选择广告类型")
      return false
    }
    if (province.checkTextEmpty() && city.checkTextEmpty() && district.checkTextEmpty()) {
      showToast("请选择推广区域")
      return false
    }
    return true
  }
}