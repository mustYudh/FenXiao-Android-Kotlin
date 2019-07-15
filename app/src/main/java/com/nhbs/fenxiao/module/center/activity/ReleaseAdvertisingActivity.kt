package com.nhbs.fenxiao.module.center.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.R.drawable
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.module.center.adapter.AddGoodsPhotoAdapter
import com.nhbs.fenxiao.module.center.presenter.ReleaseAdvertisingPresenter
import com.nhbs.fenxiao.module.center.presenter.ReleaseAdvertisingViewer
import com.nhbs.fenxiao.module.view.RecycleItemSpace
import com.nhbs.fenxiao.utils.getCalendarPicker
import com.nhbs.fenxiao.utils.getTime
import com.nhbs.fenxiao.utils.selectPhoto
import com.nhbs.fenxiao.utils.setGridLayoutAdapter
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.activity_release_advertising_view.select_time
import kotlinx.android.synthetic.main.activity_release_advertising_view.select_time_btn
import kotlinx.android.synthetic.main.include_layout_release_goods_top.list

class ReleaseAdvertisingActivity : BaseBarActivity(), ReleaseAdvertisingViewer {

  @PresenterLifeCycle
  internal var mPresenter = ReleaseAdvertisingPresenter(
      this)
  private val mAdapter = AddGoodsPhotoAdapter()


  override fun setView(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_release_advertising_view)
  }

  override fun loadData() {
    initView()
    initListener()
  }

  private fun initView() {
    showLine(true)
    setTitle("发布广告")
    setBackIcon(drawable.ic_close)
    list.addItemDecoration(RecycleItemSpace(8, 0))
    list.setGridLayoutAdapter(4, mAdapter, true)
    mAdapter.addData("")
  }


  private fun initListener() {
    mAdapter.setOnItemChildClickListener { adapter, view, position ->
      when (view.id) {
        R.id.delete_photo -> {
          adapter.remove(position)
        }
        R.id.add_photo -> {
          if (position == adapter.itemCount - 1 && TextUtils.isEmpty(
                  adapter.data[position] as String) && adapter.itemCount < 6) {
            activity.selectPhoto(6 - adapter.itemCount)
          }
        }
      }
    }

    select_time_btn.setOnClickListener {
      getCalendarPicker(activity) {
        select_time.text = getTime(it,"yyy-MM-dd")
      }
    }
  }


  override fun setReleaseAdvertisingImage(url: ArrayList<String>) {
    url.addAll(mAdapter.data)
    mAdapter.setNewData(url)
  }


  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == RESULT_OK) {
      if (requestCode == PictureConfig.CHOOSE_REQUEST) {
        val selectList = PictureSelector.obtainMultipleResult(data)
        if (selectList != null && selectList.size > 0) {
          val list = ArrayList<String>()
          for (url in selectList) {
            if (!TextUtils.isEmpty(url.compressPath)) {
              list.add(url.compressPath)
            }
          }
          mPresenter.addNewPhoto(list)
        }
      }
    }
  }
}
