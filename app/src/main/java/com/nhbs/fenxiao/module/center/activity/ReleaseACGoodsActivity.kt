package com.nhbs.fenxiao.module.center.activity

import android.annotation.SuppressLint
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
import com.nhbs.fenxiao.module.center.bean.ReleaseActivityParams
import com.nhbs.fenxiao.module.center.presenter.ReleaseGoodsACActivityViewer
import com.nhbs.fenxiao.module.center.presenter.ReleaseGoodsACPresenter
import com.nhbs.fenxiao.utils.*
import com.nhbs.fenxiao.utils.oss.UploadUtils
import com.yu.common.glide.ImageLoader
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.activity_release_goods_view.*

class ReleaseACGoodsActivity : BaseBarActivity(), ReleaseGoodsACActivityViewer {


  @PresenterLifeCycle
  internal var presenter = ReleaseGoodsACPresenter(this)
  private var pop1: SetPrizePopupWindow? = null
  private var pop2: SetPrizePopupWindow? = null
  private var pop3: SetPrizePopupWindow? = null
  private var params = ReleaseActivityParams()

  private var selectPromote = false

  private var currentSelect = -1
  override fun setView(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_release_goods_view)
  }


  override fun loadData() {
    setTitle("发布活动")
    promotion_costs.setfilters()
    commission.setfilters()
    initListener()
  }

  @SuppressLint("SetTextI18n")
  private fun initListener() {
    check_free_mail_btn.setOnClickListener {
      selectPromote = !selectPromote
      params.isGeneralize = if (selectPromote) 0 else 1
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
        params.drawTime = it.time.toString()
        select_time.text = getTime(it, "yyy-MM-dd")
      }
    }
    pop1 = SetPrizePopupWindow(activity) {
      first_goods.visibility = View.GONE
      first_goods_root.visibility = View.VISIBLE
      ImageLoader.getInstance().displayImage(first_goods_res, it.prizeRes)
      first_goods_name.text = it.prizeName
      first_goods_count.text = "奖品数量：${it.prizeCount}"
      params.firstPrizeImgs = it.prizeRes
      params.firstPrizeName = it.prizeName
      params.firstPrizeNum = it.prizeCount.toInt()
    }

    pop2 = SetPrizePopupWindow(activity) {
      second_goods.visibility = View.GONE
      second_goods_root.visibility = View.VISIBLE
      ImageLoader.getInstance().displayImage(second_goods_res, it.prizeRes)
      second_goods_name.text = it.prizeName
      second_goods_count.text = "奖品数量：${it.prizeCount}"
      params.accessitImgs = it.prizeRes
      params.accessitName = it.prizeName
      params.accessitNum = it.prizeCount.toInt()
    }
    pop3 = SetPrizePopupWindow(activity) {
      last_goods.visibility = View.GONE
      last_goods_root.visibility = View.VISIBLE
      ImageLoader.getInstance().displayImage(last_goods_res, it.prizeRes)
      last_goods_name.text = it.prizeName
      last_goods_count.text = "奖品数量：${it.prizeCount}"
      params.thirdPrizeImgs = it.prizeRes
      params.thirdPrizeName = it.prizeName
      params.thirdPrizeNum = it.prizeCount.toInt()
    }


    first_goods.setOnClickListener {
      currentSelect = 1
      pop1?.showPopupWindow()
    }
    second_goods.setOnClickListener {
      currentSelect = 2
      pop2?.showPopupWindow()
    }
    last_goods.setOnClickListener {
      currentSelect = 3
      pop3?.showPopupWindow()
    }
    first_goods_edit.setOnClickListener {
      pop1?.showPopupWindow()
    }
    second_goods_edit.setOnClickListener {
      pop2?.showPopupWindow()
    }
    last_goods_edit.setOnClickListener {
      pop3?.showPopupWindow()
    }
    release_ac_goods.setOnClickListener {
      params.aName = activity_title.getInputText()
      params.content = activity_content.getInputText()
      params.grossSpread = promotion_costs.getInputText()
      params.pvPrice = commission.getInputText()
      presenter.releaseActivity(params)
    }


    select_type_1.setOnClickListener {
      PickerViewUtils.showSelectCity(activity) { province, city, district ->
        type_name1.text = province + city + district
        params.province = province
        params.city = city
        params.district = district
      }
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
            when (currentSelect) {
              1 -> {
                pop1?.setSelectImageView(fileList[0])
              }
              2 -> {
                pop2?.setSelectImageView(fileList[0])
              }
              3 -> {
                pop3?.setSelectImageView(fileList[0])
              }
            }
          }
        }
      }
    }
  }

}
