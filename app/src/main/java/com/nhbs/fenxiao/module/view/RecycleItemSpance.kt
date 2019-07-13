package com.nhbs.fenxiao.module.view

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View


/**
 * @author yudenghao
 * @date 2019-07-14
 */
class RecycleItemSpance(private var space: Int, private var bottomSpace: Int) : RecyclerView.ItemDecoration() {
  override fun getItemOffsets(outRect: Rect, view: View,
      parent: RecyclerView, state: RecyclerView.State) {
    val childCount = parent.childCount
    val itemPosition = parent.getChildAdapterPosition(view)
    val itemCount = state.itemCount
    outRect.left = space
    outRect.right = space
    outRect.bottom = space
    outRect.top = space

    if (itemCount > 0 && itemPosition == itemCount - 1) {
      outRect.bottom = bottomSpace
    }
  }
}