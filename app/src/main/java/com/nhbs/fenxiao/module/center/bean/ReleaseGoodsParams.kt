package com.nhbs.fenxiao.module.center.bean

import com.nhbs.fenxiao.utils.checkTextEmpty
import com.nhbs.fenxiao.utils.showToast
import java.io.Serializable

/**
 * @author yudneghao
 * @date 2019-07-20
 */
class ReleaseGoodsParams : Serializable {
  var mImgs: String? = null
  var id: String? = ""
  var mContent: String? = null
  var mPrice: String? = null
  var commission: String? = null
  var postage: String? = null
  var classId: String? = null
  var dealWay: String? = "-1"
  var tagOne: String? = null
  var tagTwo: String? = null
  var mName: String? = null
  var tagOneName: String? = null
  var tagTwoName: String? = null

  fun checkEmpty(): Boolean {
    if (mImgs.checkTextEmpty()) {
      showToast("至少选择一张商品图片")
      return false
    }
    if (mName.checkTextEmpty()) {
      showToast("商品名称输入不能为空")
      return false
    }
    if (mContent.checkTextEmpty()) {
      showToast("商品描述输入不能为空")
      return false
    }
    if (mPrice.checkTextEmpty()) {
      showToast("商品价格输入不能为空")
      return false
    }
    if (commission.checkTextEmpty()) {
      showToast("商品佣金输入不能为空")
      return false
    }
    if (classId.checkTextEmpty() || classId.equals("-1")) {
      showToast("请选择商品类别")
      return false
    }
    if (dealWay.checkTextEmpty() || dealWay.equals("-1")) {
      showToast("请选择交易方式")
      return false
    }
    if (tagOne.checkTextEmpty()) {
      showToast("标签填写不能为空")
      return false
    }
    if (tagTwo.checkTextEmpty()) {
      showToast("标签填写不能为空")
      return false
    }
    return true
  }
}