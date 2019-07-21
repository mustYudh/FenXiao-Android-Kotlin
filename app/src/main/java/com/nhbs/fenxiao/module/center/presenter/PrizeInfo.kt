package com.nhbs.fenxiao.module.center.presenter

import com.nhbs.fenxiao.utils.checkTextEmpty
import com.nhbs.fenxiao.utils.showToast

/**
 * @author yudneghao
 * @date 2019-07-21
 */
class PrizeInfo {
  var prizeRes = ""
  var prizeName = ""
  var prizeCount = ""

  fun checkEmpty(): Boolean {
    if (prizeRes.checkTextEmpty()) {
      showToast("请选择一张奖品的照片")
      return false
    }
    if (prizeName.checkTextEmpty()) {
      showToast("奖品名称输入不能为空")
      return false
    }
    if (prizeCount.checkTextEmpty()) {
      showToast("奖品数量输入不能为空")
      return false
    }
    return true
  }
}