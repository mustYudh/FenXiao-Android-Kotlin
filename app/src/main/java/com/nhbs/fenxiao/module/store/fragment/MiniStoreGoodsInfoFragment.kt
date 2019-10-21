package com.nhbs.fenxiao.module.store.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.mylhyl.circledialog.CircleDialog
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseFragment
import com.nhbs.fenxiao.module.center.activity.ReleaseGoodsActivity
import com.nhbs.fenxiao.module.store.activity.type.SetTypeActivity
import com.nhbs.fenxiao.module.store.adapter.MiniStoreGoodsInfoAdapter
import com.nhbs.fenxiao.module.store.bean.GetGoodsParams
import com.nhbs.fenxiao.module.store.bean.GoodsListBean.GoodsInfoBean
import com.nhbs.fenxiao.module.store.presenter.MiniStoreGoodsInfoPresenter
import com.nhbs.fenxiao.module.store.presenter.MiniStoreGoodsInfoViewer
import com.nhbs.fenxiao.utils.setLinearLayoutAdapter
import com.nhbs.fenxiao.utils.showToast
import com.scwang.smartrefresh.layout.SmartRefreshLayout
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
  private val SET_TYPE_REQUEST = 100

  @PresenterLifeCycle
  private val mPresenter = MiniStoreGoodsInfoPresenter(this)
  private var mRecyclerView: RecyclerView? = null
  private var adapter = MiniStoreGoodsInfoAdapter()
  private var refresh: SmartRefreshLayout? = null

  private var params = GetGoodsParams()
  private var pageNum: Int = 1
  private var timePickerData: List<GoodsInfoBean>? = null
  private var orderTypeTime = 1
  private var orderTypeOther = -1

  override fun getContentViewId(): Int {
    return R.layout.fragment_mini_store_goods_layout
  }

  override fun setView(savedInstanceState: Bundle?) {
    mRecyclerView = bindView(R.id.recycler_view)
    picker_time.isSelected = selectedType == 0
    time_picker.rotation = 0f
    mRecyclerView?.layoutManager = LinearLayoutManager(activity)
    mRecyclerView?.setLinearLayoutAdapter(adapter,true)
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
      launchHelper.startActivityForResult(
          SetTypeActivity::class.java, SET_TYPE_REQUEST)
    }
  }

  override fun loadData() {
    refresh = bindView(R.id.refresh)
    mPresenter.getGoodsList(params, null, 0, true)
    refresh?.setOnRefreshListener { refreshLayout ->
      params.pageNum = 1
      mPresenter.getGoodsList(params, refreshLayout, 0)
    }
    refresh?.setOnLoadMoreListener { refreshLayout ->
      params.pageNum = pageNum++
      mPresenter.getGoodsList(params, refreshLayout, 1)
    }
    adapter.setOnItemChildClickListener { adapter, view, position ->
      val data = adapter.data[position] as GoodsInfoBean
      when (view.id) {
        R.id.edit_goods -> {
          launchHelper.startActivity(ReleaseGoodsActivity.getIntent(activity!!, data))
        }
        R.id.shelves_goods -> {
          CircleDialog.Builder()
              .setTitle("温馨提示")
              .setText("\n确定下架该商品吗?\n")
              .setPositive("确定") {
                mPresenter.pullDownGoods(data.id!!, position)
              }.setNegative("取消", null)
              .show(fragmentManager)


        }
      }
    }
  }


  override fun setGoodsInfoList(list: List<GoodsInfoBean>?, isPickerTime: Boolean) {
    if (list != null && list.size > 0) {
      if (pageNum == 1) {
        adapter.setNewData(list)
      } else {
        adapter.addData(list)
      }
    } else {
      if (pageNum == 1) {
        adapter.emptyView = View.inflate(activity, R.layout.empty_layout, null)
      }
    }
    if (isPickerTime) {
      timePickerData = adapter.data
    }
  }


  private fun setTabSelectedView(pickerType: Int) {
    when (pickerType) {
      0 -> {
        picker_time.isSelected = true
        count.isSelected = false
        type.isSelected = false
        type_icon.isSelected = false
        time_picker.setImageResource(
            if (orderTypeTime == 1) R.drawable.ic_time_picker_down else R.drawable.ic_time_picker_up)
        if (orderTypeOther == -1) {
          orderTypeTime = if (orderTypeTime == 1) 2 else 1
          time_picker.setImageResource(
              if (orderTypeTime == 1) R.drawable.ic_time_picker_down else R.drawable.ic_time_picker_up)
          params.orderType = orderTypeTime
          mPresenter.getGoodsList(params, null, 0, true)
        } else {
          adapter.setNewData(timePickerData)
          orderTypeOther = -1
        }

      }
      1 -> {
        orderTypeOther = 2
        picker_time.isSelected = false
        count.isSelected = true
        type.isSelected = false
        type_icon.isSelected = false
        time_picker.setImageResource(R.drawable.ic_time_picker_normal)
        params.orderType = orderTypeOther
        mPresenter.getGoodsList(params, null, 0)
      }
      2 -> {
        orderTypeOther = 3
        picker_time.isSelected = false
        count.isSelected = false
        type.isSelected = true
        type_icon.isSelected = true
        time_picker.setImageResource(R.drawable.ic_time_picker_normal)
        launchHelper.startActivity(SetTypeActivity::class.java)
      }
    }
  }

  override fun pullDownGoodsSuccess(position: Int) {
    showToast("下架成功")
    adapter.remove(position)
    if (adapter.data.size == 0) {
      adapter.emptyView = View.inflate(activity, R.layout.empty_layout, null)
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == SET_TYPE_REQUEST && resultCode == Activity.RESULT_OK) {
      setTabSelectedView(1)
    }
  }
}