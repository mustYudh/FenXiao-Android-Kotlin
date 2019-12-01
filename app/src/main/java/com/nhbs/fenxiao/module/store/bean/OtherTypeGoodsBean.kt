package com.nhbs.fenxiao.module.store.bean

import java.io.Serializable
import java.math.BigDecimal

/**
 * @author myx
 * @date on 2019-11-16
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email mayuxuan@innobuddy.com
 */
class OtherTypeGoodsBean : Serializable{

    val rows: List<GoodsInfoBean>? = null

    class GoodsInfoBean : Serializable {

        val mPrice: BigDecimal? = null
        val mContent: String? = null
        val userId: String? = null
        val mTitle: String? = null
        val id: String? = null
        val shopId: String? = null
        val mName: String? = null
        val mImgs: String? = null
        var isChecked: Boolean? = false

    }


}