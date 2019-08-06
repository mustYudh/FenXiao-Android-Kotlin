package com.nhbs.fenxiao.module.store.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseFragment
import com.nhbs.fenxiao.module.store.adapter.MiniStoreActivityAdapter
import com.nhbs.fenxiao.module.store.bean.MiniStoreActivityBean
import com.nhbs.fenxiao.module.store.presenter.MiniStoreActivityInfoPresenter
import com.nhbs.fenxiao.module.store.presenter.MiniStoreActivityInfoViewer
import com.yu.common.mvp.PresenterLifeCycle

/**
 * @author yudneghao
 * @date 2019-06-30
 */
class MiniStoreActivityFragment : BaseFragment(), MiniStoreActivityInfoViewer {


  @PresenterLifeCycle
  private val mPresenter = MiniStoreActivityInfoPresenter(this)
  private var mRecyclerView: RecyclerView? = null
  private var adapter = MiniStoreActivityAdapter()

  override fun getContentViewId(): Int {
    return R.layout.fragment_mini_store_goods_layout
  }

  override fun setView(savedInstanceState: Bundle?) {
    mRecyclerView = bindView(R.id.recycler_view)
    mRecyclerView?.layoutManager = LinearLayoutManager(activity)
    mRecyclerView?.adapter = adapter
  }

  override fun loadData() {
    mPresenter.getGoodsInfoList()
  }


  override fun setGoodsInfoList(list: List<MiniStoreActivityBean>) {
    adapter.setNewData(list)
  }
}