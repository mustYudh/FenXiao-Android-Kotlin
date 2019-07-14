package com.nhbs.fenxiao.module.view

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import com.nhbs.fenxiao.utils.DensityUtils


/**
 * @author yudenghao
 * @date 2019-07-14
 */
class RecycleItemSpace(private var space: Int, private var bottomSpace: Int) : RecyclerView.ItemDecoration() {

  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
    val itemPosition = parent.getChildAdapterPosition(view)
    val itemCount = state.itemCount
    outRect.left = DensityUtils.dp2px(view.context,space.toFloat())
    outRect.right = DensityUtils.dp2px(view.context,space.toFloat())
    outRect.bottom = DensityUtils.dp2px(view.context,space.toFloat())
    outRect.top = DensityUtils.dp2px(view.context,space.toFloat())

    if (itemCount > 0 && itemPosition == itemCount - 1) {
      outRect.bottom = DensityUtils.dp2px(view.context,bottomSpace.toFloat())
    }
  }
}