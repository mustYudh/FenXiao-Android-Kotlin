package com.nhbs.fenxiao.module.store.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseFragment
import com.nhbs.fenxiao.module.store.adapter.MiniStoreActivityAdapter
import com.nhbs.fenxiao.module.store.bean.MiniStoreActivityBean
import com.nhbs.fenxiao.module.store.presenter.MiniStoreActivityInfoPresenter
import com.nhbs.fenxiao.module.store.presenter.MiniStoreActivityInfoViewer
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.fragment_mini_store_goods_layout.refresh

/**
 * @author yudneghao
 * @date 2019-06-30
 */
class MiniStoreActivityFragment : BaseFragment(), MiniStoreActivityInfoViewer {


  @PresenterLifeCycle
  private val mPresenter = MiniStoreActivityInfoPresenter(this)
  private var mRecyclerView: RecyclerView? = null
  private var adapter = MiniStoreActivityAdapter()
  private var pageNum: Int = 1

  override fun getContentViewId(): Int {
    return R.layout.fragment_mini_store_goods_layout
  }

  override fun setView(savedInstanceState: Bundle?) {
    mRecyclerView = bindView(R.id.recycler_view)
    mRecyclerView?.layoutManager = LinearLayoutManager(activity)
    mRecyclerView?.adapter = adapter
    initListener()
  }

  override fun loadData() {
    mPresenter.getActivityList()
  }


  private fun initListener() {
    refresh?.setOnRefreshListener { refreshLayout ->
      mPresenter.getActivityList(0, refreshLayout, 0)
    }
    refresh?.setOnLoadMoreListener { refreshLayout ->
      pageNum++
      mPresenter.getActivityList(pageNum, refreshLayout, 1)
    }
  }

  override fun setActivityInfoList(list: List<MiniStoreActivityBean>?) {
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
  }


}