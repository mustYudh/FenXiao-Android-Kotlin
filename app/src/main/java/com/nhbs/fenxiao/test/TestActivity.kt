package com.nhbs.fenxiao.test

import android.os.Bundle
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.test.presenter.TestPresenter
import com.nhbs.fenxiao.test.presenter.TestViewer
import com.yu.common.mvp.PresenterLifeCycle


class TestActivity : BaseBarActivity(), TestViewer {

    @PresenterLifeCycle
    internal var presenter = TestPresenter(this)

    override fun setView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_test_view)
    }

    override fun loadData() {

    }
}
