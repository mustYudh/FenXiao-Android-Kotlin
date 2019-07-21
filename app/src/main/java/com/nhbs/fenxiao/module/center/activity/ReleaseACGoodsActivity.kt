package com.nhbs.fenxiao.module.center.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.R.id
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.module.center.SetPrizePopupWindow
import com.nhbs.fenxiao.module.center.presenter.ReleaseGoodsACActivityViewer
import com.nhbs.fenxiao.module.center.presenter.ReleaseGoodsACPresenter
import com.nhbs.fenxiao.utils.getCalendarPicker
import com.nhbs.fenxiao.utils.getTime
import com.nhbs.fenxiao.utils.oss.UploadUtils
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.activity_release_goods_view.check_free_mail_btn
import kotlinx.android.synthetic.main.activity_release_goods_view.commission
import kotlinx.android.synthetic.main.activity_release_goods_view.first_goods
import kotlinx.android.synthetic.main.activity_release_goods_view.last_goods
import kotlinx.android.synthetic.main.activity_release_goods_view.promotion_costs
import kotlinx.android.synthetic.main.activity_release_goods_view.second_goods
import kotlinx.android.synthetic.main.activity_release_goods_view.select_time
import kotlinx.android.synthetic.main.activity_release_goods_view.select_time_btn

class ReleaseACGoodsActivity : BaseBarActivity(), ReleaseGoodsACActivityViewer {

  @PresenterLifeCycle
  internal var presenter = ReleaseGoodsACPresenter(this)
  private var pop: SetPrizePopupWindow? = null

  private var selectPromote = false

  override fun setView(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_release_goods_view)
  }

  override fun loadData() {
    title = "发布活动"
    pop = SetPrizePopupWindow(activity)
    initListener()
  }

  private fun initListener() {
    check_free_mail_btn.setOnClickListener {
      selectPromote = !selectPromote
      check_free_mail_btn.isSelected = selectPromote
      bindView<View>(id.line, selectPromote)
      bindView<LinearLayout>(id.promote_root, selectPromote)
      if (!selectPromote) {
        promotion_costs.setText("")
        commission.setText("")
      }
    }
    select_time_btn.setOnClickListener {
      getCalendarPicker(activity) {
        select_time.text = getTime(it, "yyy-MM-dd")
      }
    }

    first_goods.setOnClickListener {
      pop?.showPopupWindow()
    }
    second_goods.setOnClickListener {
      pop?.showPopupWindow()
    }
    last_goods.setOnClickListener {
      pop?.showPopupWindow()
    }
  }


  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == RESULT_OK) {
      if (requestCode == PictureConfig.CHOOSE_REQUEST) {
        val selectList = PictureSelector.obtainMultipleResult(data)
        if (selectList != null && selectList.size > 0) {
          val list = ArrayList<String>()
          for (url in selectList) {
            list.add(url.compressPath)
          }
          UploadUtils.uploadFile(activity, list, "JiangPing", "png") { fileList ->
            
//            pop?.setSelectImageView(fileList[0])
          }
        }
      }
    }
  }

}
