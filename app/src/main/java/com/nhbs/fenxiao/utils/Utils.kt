package com.nhbs.fenxiao.utils

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @author yudenghao
 * @date 2019-07-13
 */


fun RecyclerView.setLinearLayoutAdapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
    noScroller: Boolean?) {
  layoutManager = object : LinearLayoutManager(context) {
    override fun canScrollVertically(): Boolean {
      return noScroller ?: super.canScrollVertically()
    }
  }

  setAdapter(adapter)
}


fun RecyclerView.setGridLayoutAdapter(count: Int,
    adapter: RecyclerView.Adapter<BaseViewHolder>, noScroller: Boolean?) {
  layoutManager = object : GridLayoutManager(context, count, LinearLayoutManager.VERTICAL, false) {
    override fun canScrollVertically(): Boolean {
      return noScroller ?: super.canScrollVertically()
    }
  }
  setAdapter(adapter)
}



fun RecyclerView.setGridLayoutAdapter(manager: GridLayoutManager,
    adapter: RecyclerView.Adapter<BaseViewHolder>) {
  layoutManager = manager
  setAdapter(adapter)
}


