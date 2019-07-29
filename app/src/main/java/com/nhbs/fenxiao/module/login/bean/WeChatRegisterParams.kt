package com.nhbs.fenxiao.module.login.bean

import java.io.Serializable

/**
 * @author yudenghao
 * @date 2019-07-30
 */
data class WeChatRegisterParams(
    var openId: String,
    var nickName: String,
    var headImg: String,
    var mobile: String,
    var code: String,
    var invitePeopleCode: String
): Serializable