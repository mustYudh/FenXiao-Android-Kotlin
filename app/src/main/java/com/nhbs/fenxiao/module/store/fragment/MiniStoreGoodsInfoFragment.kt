package com.nhbs.fenxiao.module.store.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseFragment
import com.nhbs.fenxiao.module.store.adapter.MiniStoreGoodsInfoAdapter
import com.nhbs.fenxiao.module.store.bean.MiniStoreGoodsInfoBaen
import com.nhbs.fenxiao.module.store.presenter.MiniStoreGoodsInfoPresenter
import com.nhbs.fenxiao.module.store.presenter.MiniStoreGoodsInfoViewer
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.fragment_mini_store_goods_layout.count
import kotlinx.android.synthetic.main.fragment_mini_store_goods_layout.picker_time
import kotlinx.android.synthetic.main.fragment_mini_store_goods_layout.time_picker
import kotlinx.android.synthetic.main.fragment_mini_store_goods_layout.type

/**
 * @author yudneghao
 * @date 2019-06-30
 */
class MiniStoreGoodsInfoFragment : BaseFragment(), MiniStoreGoodsInfoViewer {


  private var selectedType = 0
  private var selectedOther = true

  private var timeType = 0

  @PresenterLifeCycle
  private val mPresenter = MiniStoreGoodsInfoPresenter(this)
  private var mRecyclerView: RecyclerView? = null
  private var adapter = MiniStoreGoodsInfoAdapter()

  override fun getContentViewId(): Int {
    return R.layout.fragment_mini_store_goods_layout
  }

  override fun setView(savedInstanceState: Bundle?) {
    mRecyclerView = bindView(R.id.recycler_view)
    picker_time.isSelected = selectedType == 0
    time_picker.rotation = 0f
    mRecyclerView?.layoutManager = LinearLayoutManager(activity)
    mRecyclerView?.adapter = adapter
    initListener()
  }

  private fun initListener() {
    picker_time.setOnClickListener {
      setTabSelectedView(0)
    }
    count.setOnClickListener {
      setTabSelectedView(1)
    }
    type.setOnClickListener {
      setTabSelectedView(2)
    }
  }

  override fun loadData() {
    mPresenter.getGoodsInfoList()
  }


  override fun setGoodsInfoList(list: List<MiniStoreGoodsInfoBaen>) {
    adapter.setNewData(list)
  }


  private fun setTabSelectedView(pickerType: Int) {
    when (pickerType) {
      0 -> {
        picker_time.isSelected = true
        time_picker.rotation = 180f
        count.isSelected = false
        type.isSelected = false
        if (selectedOther) {

        } else {

        }
        selectedOther = false
      }
      1 -> {
        picker_time.isSelected = false
        count.isSelected = true
        type.isSelected = false
        selectedOther = true
      }
      2 -> {
        picker_time.isSelected = false
        count.isSelected = false
        type.isSelected = true
        selectedOther = true
      }
    }
  }
}