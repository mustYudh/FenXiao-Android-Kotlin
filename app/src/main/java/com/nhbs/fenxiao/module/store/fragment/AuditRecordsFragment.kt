package com.nhbs.fenxiao.module.store.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseFragment
import com.nhbs.fenxiao.module.store.adapter.MiniRecordAdapter
import com.nhbs.fenxiao.module.store.bean.MiniStoreActivityBean
import com.nhbs.fenxiao.module.store.presenter.AuditRecordsPresenter
import com.nhbs.fenxiao.module.store.presenter.AuditRecordsViewer
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.fragment_mini_store_goods_layout.refresh

/**
 * @author yudneghao
 * @date 2019-08-17
 */
class AuditRecordsFragment : BaseFragment(), AuditRecordsViewer {

  @PresenterLifeCycle
  private var mPresenter = AuditRecordsPresenter(this)
  private var adapter = MiniRecordAdapter()
  private var mRecyclerView: RecyclerView? = null
  private var pageNum: Int = 1

  override fun setView(savedInstanceState: Bundle?) {
    mRecyclerView = bindView(R.id.recycler_view)
    mRecyclerView?.layoutManager = LinearLayoutManager(activity)
    mRecyclerView?.adapter = adapter
    initListener()
  }

  override fun loadData() {
    mPresenter.getRecordList()

  }

  override fun getContentViewId(): Int {
    return R.layout.fragment_mini_store_goods_layout
  }

  private fun initListener() {
    refresh?.setOnRefreshListener { refreshLayout ->
      pageNum = 1
      mPresenter.getRecordList(pageNum, refreshLayout, 0)
    }
    refresh?.setOnLoadMoreListener { refreshLayout ->
      pageNum++
      mPresenter.getRecordList(pageNum, refreshLayout, 1)
    }

  }


  override fun setRecordInfoList(list: List<MiniStoreActivityBean>?) {
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