package com.nhbs.fenxiao.module.store.bean

import java.io.Serializable
import java.math.BigDecimal

/**
 * @author yudenghao
 * @date 2019-08-19
 */
class GoodsListBean : Serializable {

  /**
   * rows : [{"mPrice":100,"affiliationShopId":"","mContent":"进口服饰","typeName":null,"shopName":null,"updateTime":1561613662356,"tagTwo":"气质","userId":"","postage":10,"classId":"1","dealWay":1,"tagOne":"进口","createTime":1561613662356,"mTitle":"进口服饰","commission":2,"id":"03a98c3fce1e440d9adf3ca10d67cfc8","shopId":"1","mName":"进口服饰","affiliationShopName":null,"mImgs":"https://imgsa.baidu.com/news/q%3D100/sign=4bf9145b4910b912b9c1f2fef3fffcb5/f636afc379310a5535f18d14b94543a98326107c.jpg","status":1}]
   * isFirstPage : true
   * pageNum : 1
   */

  val isFirstPage: Boolean = false
  val pageNum: Int = 0
  val rows: List<GoodsInfoBean>? = null


  class GoodsInfoBean : Serializable {
    /**
     * mPrice : 100
     * affiliationShopId :
     * mContent : 进口服饰
     * typeName : null
     * shopName : null
     * updateTime : 1561613662356
     * tagTwo : 气质
     * userId :
     * postage : 10
     * classId : 1
     * dealWay : 1
     * tagOne : 进口
     * createTime : 1561613662356
     * mTitle : 进口服饰
     * commission : 2
     * id : 03a98c3fce1e440d9adf3ca10d67cfc8
     * shopId : 1
     * mName : 进口服饰
     * affiliationShopName : null
     * mImgs : https://imgsa.baidu.com/news/q%3D100/sign=4bf9145b4910b912b9c1f2fef3fffcb5/f636afc379310a5535f18d14b94543a98326107c.jpg
     * status : 1
     */

    val mPrice: BigDecimal? = null
    val affiliationShopId: String? = null
    val mContent: String? = null
    val typeName: String? = null
    val shopName: String? = null
    val updateTime: Long = 0
    val tagTwo: String? = null
    val userId: String? = null
    val postage: BigDecimal? = null
    val classId: String? = null
    val dealWay: Int = 0
    val tagOne: String? = null
    val createTime: Long = 0
    val mTitle: String? = null
    val commission: BigDecimal? = null
    val id: String? = null
    val shopId: String? = null
    val shareType: String? = null
    val mName: String? = null
    val affiliationShopName: Any? = null
    val mImgs: String? = null
    val status: Int = 0
    var number: Int = 0
    var isChecked: Boolean = false
    var isShowCheck: Boolean = false

  }
}
