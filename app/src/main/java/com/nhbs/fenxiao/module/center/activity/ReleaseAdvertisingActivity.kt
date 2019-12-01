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
import com.nhbs.fenxiao.utils.*
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.activity_release_advertising_view.*
import kotlinx.android.synthetic.main.include_layout_release_goods_top.list

class ReleaseAdvertisingActivity : BaseBarActivity(), ReleaseAdvertisingViewer {


  @PresenterLifeCycle
  internal var mPresenter = ReleaseAdvertisingPresenter(this)
  private val mAdapter = AddGoodsPhotoAdapter()
  private var params = ReleaseAdParams()
  private var selectedFriends = false
  private var selectedWeiBo = false
  private var selectedQQ = false

  override fun setView(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_release_advertising_view)
  }

  override fun loadData() {
    initView()
    initListener()
    mPresenter.getAdType()
  }

  private fun initView() {
    showLine(true)
    setTitle("发布广告")
    setBackIcon(drawable.ic_close)
    list.addItemDecoration(RecycleItemSpace(8, 0))
    list.setGridLayoutAdapter(4, mAdapter, true)
    mAdapter.addData("")
    pvSpread.setfilters()
    grossSpread.setfilters()
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

    endTime.setOnClickListener {
      getCalendarPicker(activity) {
        params.endTime = it.time.toString()
        endTime.setText(getTime(it, "yyy-MM-dd"))
      }
    }
    startTime.setOnClickListener {
      getCalendarPicker(activity) {
        params.startTime = it.time.toString()
        startTime.setText(getTime(it, "yyy-MM-dd"))
      }
    }

    friends.setOnClickListener {
      selectedFriends = !selectedFriends
      friends.isSelected = selectedFriends
    }

    wei_bo.setOnClickListener {
      selectedWeiBo = !selectedWeiBo
      wei_bo.isSelected = selectedWeiBo
    }

    qq.setOnClickListener {
      selectedQQ = !selectedQQ
      qq.isSelected = selectedQQ
    }


    address.setOnClickListener {
      PickerViewUtils.showSelectCity(activity) { province, city, district ->
        address.text = province + city + district
        params.province = province
        params.city = city
        params.district = district
      }
    }

    release_ad.setOnClickListener {
      params.title = input_title.getInputText()
      params.content = input_content.getInputText()
      params.grossSpread = grossSpread.getInputText()
      params.pvSpread = pvSpread.getInputText()
      params.number = number.getInputText().toInt()
      params.number = phoneNumber.getInputText().toInt()
      params.phoneNumber = phoneNumber.getInputText()
      val data = ArrayList<String>()
      if (selectedFriends) {
        data.add("朋友圈")
      }
      if (selectedWeiBo) {
        data.add("微博")
      }
      if (selectedQQ) {
        data.add("QQ空间")
      }
      var shareType = ""
      data.forEachIndexed { index, type ->
        shareType += "${type}${if (index != data.size - 1) "," else ""}"
      }
      params.shareType = shareType
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
//          val result = data?.getSerializableExtra(SelectGoodsTypeActivity.SELECTED_DATA) as OrderInfo
//          type_name1.text = result.classify
        }
        SELECTED_DATA_REQUEST_DATA_2 -> {


        }
      }
    }
  }
}
