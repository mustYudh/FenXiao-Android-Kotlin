package com.nhbs.fenxiao.module.store.bean

import java.io.Serializable
import java.math.BigDecimal

/**
 * @author yudenghao
 * @date 2019-08-26
 */
class MiniStoreActivityBean : Serializable {

  /**
   * firstPrizeImgs : http://nhbs.oss-cn-beijing.aliyuncs.com/03.jpg
   * firstPrizeNum : 10
   * city : 杭州市
   * drawTime : 1576108800000
   * thirdPrizeImgs : http://nhbs.oss-cn-beijing.aliyuncs.com/21.jpg
   * content : 这是一条测试活动
   * thirdPrizeName : 小黄鸭
   * pvPrice : 100
   * accessitImgs : http://nhbs.oss-cn-beijing.aliyuncs.com/05.jpg
   * province : 浙江省
   * accessitName : 棒棒糖
   * headerImage :
   * isGeneralize : 1
   * lotteryWinner :
   * firstPrizeName : 娃哈哈
   * id : 281667796843106304
   * town :
   * thirdPrizeNum : 50
   * grossSpread : 12
   * userId : 600e3c4a480340cbafb4fe6cef315c46
   * accessitNum : 100
   * createTime : 1566198435902
   * aName : 测试活动
   * district : 上城区
   * status : 3
   */

  var firstPrizeImgs: String? = null
  var firstPrizeNum: Int = 0
  var type: Int = 0
  var city: String? = null
  var drawTime: Long = 0
  var thirdPrizeImgs: String? = null
  var content: String? = null
  var thirdPrizeName: String? = null
  var pvPrice: BigDecimal? = null
  var accessitImgs: String? = null
  var province: String? = null
  var accessitName: String? = null
  var headerImage: String? = null
  var shopImage: String? = null
  var isGeneralize: Int = 0
  var lotteryWinner: String? = null
  var firstPrizeName: String? = null
  var id: String? = null
  var town: String? = null
  var thirdPrizeNum: BigDecimal? = null
  var grossSpread: BigDecimal? = null
  var userId: String? = null
  var accessitNum: Int = 0
  var createTime: Long = 0
  var aName: String? = null
  var district: String? = null
  var status: Int = 0
  var name: String = ""
  var images: String = ""
  val mPrice: String? = null

}
