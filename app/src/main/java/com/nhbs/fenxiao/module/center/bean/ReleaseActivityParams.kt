package com.nhbs.fenxiao.module.center.bean

import com.nhbs.fenxiao.utils.checkTextEmpty
import com.nhbs.fenxiao.utils.showToast
import java.io.Serializable

/**
 * @author yudneghao
 * @date 2019-08-04
 */
class ReleaseActivityParams : Serializable {
  var type = "1"
  var aName: String? = null
  var content: String? = null
  var drawTime: String? = null
  var firstPrizeImgs: String? = null
  var firstPrizeName: String? = null
  var firstPrizeNum: Int? = null
  var accessitImgs: String? = null
  var accessitName: String? = null
  var accessitNum: Int? = null
  var thirdPrizeImgs: String? = null
  var thirdPrizeName: String? = null
  var thirdPrizeNum: Int? = null
  var isGeneralize: Int? = 0
  var grossSpread: String? = null
  var pvPrice: String? = null
  var province: String? = null
  var city: String? = null
  var district: String? = null


  fun checkParams(): Boolean {
    if (aName.checkTextEmpty()) {
      showToast("活动标题不能为空")
      return false
    }
    if (content.checkTextEmpty()) {
      showToast("活动内容不能为空")
      return false
    }
    if (drawTime.checkTextEmpty()) {
      showToast("请选择开奖时间")
      return false
    }
    if (province.checkTextEmpty() && city.checkTextEmpty() && district.checkTextEmpty()) {
      showToast("请选择推广区域")
      return false
    }
    return true
  }
}