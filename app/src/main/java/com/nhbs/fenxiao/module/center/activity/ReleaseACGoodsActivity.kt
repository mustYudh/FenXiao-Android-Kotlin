package com.nhbs.fenxiao.module.center.activity

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.R.id
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.module.center.presenter.ReleaseGoodsACActivityViewer
import com.nhbs.fenxiao.module.center.presenter.ReleaseGoodsACPresenter
import com.nhbs.fenxiao.utils.getCalendarPicker
import com.nhbs.fenxiao.utils.getTime
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.activity_release_goods_view.check_free_mail_btn
import kotlinx.android.synthetic.main.activity_release_goods_view.select_time
import kotlinx.android.synthetic.main.activity_release_goods_view.select_time_btn

class ReleaseACGoodsActivity : BaseBarActivity(), ReleaseGoodsACActivityViewer {

  @PresenterLifeCycle
  internal var presenter = ReleaseGoodsACPresenter(this)

  private var selectPromote = false

  override fun setView(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_release_goods_view)
  }

  override fun loadData() {
    initListener()
  }

  private fun initListener() {
    check_free_mail_btn.setOnClickListener {
      selectPromote = !selectPromote
      check_free_mail_btn.isSelected = selectPromote
      bindView<View>(id.line, selectPromote)
      bindView<LinearLayout>(id.promote_root, selectPromote)
    }
    select_time_btn.setOnClickListener {
      getCalendarPicker(activity) {
        select_time.text = getTime(it, "yyy-MM-dd")
      }
    }
  }
}
