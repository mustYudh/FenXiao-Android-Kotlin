package com.nhbs.fenxiao.module.store.bean

import java.io.Serializable

/**
 * @author myx
 * @date on 2019-11-23
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email mayuxuan@innobuddy.com
 */
data class DeliverSucEvent(
        var info: OrderInfo?,
        var position: Int?
) : Serializable
