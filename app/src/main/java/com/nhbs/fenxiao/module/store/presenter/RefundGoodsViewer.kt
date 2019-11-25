package com.nhbs.fenxiao.module.store.presenter

import com.nhbs.fenxiao.module.store.bean.ExpInfoBean
import com.nhbs.fenxiao.module.store.bean.RefundGoodsInfoBean
import com.yu.common.mvp.Viewer

/**
 * @author myx
 * @date on 2019-11-24
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email mayuxuan@innobuddy.com
 */
interface RefundGoodsViewer : Viewer {
    fun getInfoSuc(data: RefundGoodsInfoBean)

    fun findExpSuccess(data: ExpInfoBean?)

    fun confirmRefundSuc()

    fun refuseRefundSuc()
}