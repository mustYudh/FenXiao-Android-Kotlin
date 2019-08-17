package com.nhbs.fenxiao.module.store.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseFragment
import com.nhbs.fenxiao.module.store.adapter.MiniStoreGoodsInfoAdapter
import com.nhbs.fenxiao.module.store.bean.MiniStoreGoodsInfoBaen
import com.nhbs.fenxiao.module.store.presenter.MiniStoreGoodsInfoPresenter
import com.nhbs.fenxiao.module.store.presenter.MiniStoreGoodsInfoViewer
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.fragment_mini_store_goods_layout.count
import kotlinx.android.synthetic.main.fragment_mini_store_goods_layout.picker_time
import kotlinx.android.synthetic.main.fragment_mini_store_goods_layout.select_type
import kotlinx.android.synthetic.main.fragment_mini_store_goods_layout.time_picker
import kotlinx.android.synthetic.main.fragment_mini_store_goods_layout.type
import kotlinx.android.synthetic.main.fragment_mini_store_goods_layout.type_btn
import kotlinx.android.synthetic.main.fragment_mini_store_goods_layout.type_icon

/**
 * @author yudneghao
 * @date 2019-06-30
 */
class MiniStoreGoodsInfoFragment : BaseFragment(), MiniStoreGoodsInfoViewer {


  private var selectedType = 0

  private var timeType = 0
  private var timeSelected = true

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
    select_type.visibility = View.VISIBLE
    initListener()
  }

  private fun initListener() {
    picker_time.setOnClickListener {
      setTabSelectedView(0)
    }
    count.setOnClickListener {
      setTabSelectedView(1)
    }
    type_btn.setOnClickListener {
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
        count.isSelected = false
        type.isSelected = false
        type_icon.isSelected = false
        if (timeSelected) {
          timeType = if (timeType == 0) 1 else 0
        }
        time_picker.setImageResource(
            if (timeType == 0) R.drawable.ic_time_picker_down else R.drawable.ic_time_picker_up)
        timeSelected = true

      }
      1 -> {
        picker_time.isSelected = false
        count.isSelected = true
        type.isSelected = false
        type_icon.isSelected = false
        time_picker.setImageResource(R.drawable.ic_time_picker_normal)
        timeSelected = false
      }
      2 -> {
        picker_time.isSelected = false
        count.isSelected = false
        type.isSelected = true
        type_icon.isSelected = true
        time_picker.setImageResource(R.drawable.ic_time_picker_normal)
        timeSelected = false
      }
    }
  }
}