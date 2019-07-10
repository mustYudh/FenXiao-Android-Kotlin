package com.nhbs.fenxiao.module.center.activity

import android.os.Bundle
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.module.center.presenter.ReleaseGoodsPresenter
import com.nhbs.fenxiao.module.center.presenter.ReleaseGoodsViewer
import com.yu.common.mvp.PresenterLifeCycle


class ReleaseGoodsActivity : BaseBarActivity(), ReleaseGoodsViewer {

    @PresenterLifeCycle
    internal var presenter = ReleaseGoodsPresenter(this)

    override fun setView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_new_release_goods_view)
    }

    override fun loadData() {

    }
}
