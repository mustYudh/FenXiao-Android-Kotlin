package com.nhbs.fenxiao.module.store.presenter

import com.nhbs.fenxiao.module.mine.bean.MineUserInfoBean
import com.yu.common.mvp.Viewer

/**
 * @author yudneghao
 * @date 2019-10-21
 */
interface IncomeAssetsViewer : Viewer {

    fun getUserInfoSuccess(info: MineUserInfoBean)

    fun boundWinXinSuccess()
}