package com.nhbs.fenxiao.module.center.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.TextView
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.R.drawable
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.module.center.activity.SelectGoodsTypeActivity.Companion.SELECTED_DATA_REQUEST_DATA_1
import com.nhbs.fenxiao.module.center.activity.SelectGoodsTypeActivity.Companion.SELECTED_DATA_REQUEST_DATA_2
import com.nhbs.fenxiao.module.center.adapter.AddGoodsPhotoAdapter
import com.nhbs.fenxiao.module.center.bean.ReleaseAdParams
import com.nhbs.fenxiao.module.center.bean.Row
import com.nhbs.fenxiao.module.center.presenter.ReleaseAdvertisingPresenter
import com.nhbs.fenxiao.module.center.presenter.ReleaseAdvertisingViewer
import com.nhbs.fenxiao.module.view.RecycleItemSpace
import com.nhbs.fenxiao.utils.PickerViewUtils
import com.nhbs.fenxiao.utils.getCalendarPicker
import com.nhbs.fenxiao.utils.getInputText
import com.nhbs.fenxiao.utils.getTime
import com.nhbs.fenxiao.utils.selectPhoto
import com.nhbs.fenxiao.utils.setGridLayoutAdapter
import com.nhbs.fenxiao.utils.setfilters
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.activity_release_advertising_view.commission
import kotlinx.android.synthetic.main.activity_release_advertising_view.input_content
import kotlinx.android.synthetic.main.activity_release_advertising_view.input_title
import kotlinx.android.synthetic.main.activity_release_advertising_view.price
import kotlinx.android.synthetic.main.activity_release_advertising_view.release_ad
import kotlinx.android.synthetic.main.activity_release_advertising_view.select_time
import kotlinx.android.synthetic.main.activity_release_advertising_view.select_time_btn
import kotlinx.android.synthetic.main.activity_release_advertising_view.select_type_1
import kotlinx.android.synthetic.main.activity_release_advertising_view.tag_root
import kotlinx.android.synthetic.main.activity_release_advertising_view.type_name1
import kotlinx.android.synthetic.main.include_layout_release_goods_top.list

class ReleaseAdvertisingActivity : BaseBarActivity(), ReleaseAdvertisingViewer {


  @PresenterLifeCycle
  internal var mPresenter = ReleaseAdvertisingPresenter(this)
  private val mAdapter = AddGoodsPhotoAdapter()
  private var params = ReleaseAdParams()

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
    price.setfilters()
    commission.setfilters()
    mPresenter.getAdType()
  }


  @SuppressLint("SetTextI18n")
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
        select_time.text = getTime(it, "yyy-MM-dd")
      }
    }
    select_type_1.setOnClickListener {
      PickerViewUtils.showSelectCity(activity) { province, city, district ->
        type_name1.text = province + city + district
        params.province = province
        params.city = city
        params.district = district
      }
    }
    release_ad.setOnClickListener {
      params.title = input_title.getInputText()
      params.content = input_content.getInputText()
      params.grossSpread = price.getInputText()
      params.pvSpread = commission.getInputText()
      params.endTime = select_time.text.toString().trim()
      mPresenter.releaseAD(params,mAdapter.data as ArrayList<String>)
    }
  }


  override fun setType(rows: List<Row>) {
    tag_root.removeAllViews()
    for (item in rows) {
      val tagView = LayoutInflater.from(activity).inflate(R.layout.item_goods_type, tag_root, false)
      val tagTextView = tagView.findViewById<TextView>(R.id.tag)
      tagTextView.text = item.name
      tagTextView.isSelected = item.selected
      tag_root.addView(tagView)
      tagView.setOnClickListener {
        rows.forEach {
          it.selected = false
        }
        item.selected = true
        params.typeId = item.id
        setType(rows)
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
      when (requestCode) {
        PictureConfig.CHOOSE_REQUEST -> {
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
        SELECTED_DATA_REQUEST_DATA_1 -> {
//          val result = data?.getSerializableExtra(SelectGoodsTypeActivity.SELECTED_DATA) as Row
//          type_name1.text = result.classify
        }
        SELECTED_DATA_REQUEST_DATA_2 -> {


        }
      }
    }
  }
}
