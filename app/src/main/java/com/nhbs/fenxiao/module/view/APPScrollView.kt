package com.hzrcht.seaofflowers.module.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView

/**
 * @author yudneghao
 * @date 2019-07-24
 */
class APPScrollView: ScrollView {

  private var scrollHeight = 0

  var listener: OnScrollListener? = null

  interface OnScrollListener {
    //    fun onScrollUp() //上滑
//    fun onScrollDown() //下滑
    fun scrollHeight(h: Int)
    fun actionUp(h: Int)
  }

  constructor(context: Context) : super(context)

  constructor(context: Context, abstractSet: AttributeSet) : super(context, abstractSet)

  constructor(context: Context, abstractSet: AttributeSet, defStyleAttr: Int) : super(context,
      abstractSet, defStyleAttr)

  override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
    super.onScrollChanged(l, t, oldl, oldt)

    if (listener != null) {
      if (t - oldt <= 2) {
//        listener?.onScrollDown()
      }
      if (oldt - t >= 2) {
//        listener?.onScrollUp()
      }
      scrollHeight = t
      listener?.scrollHeight(t)
    }
  }


  @SuppressLint("ClickableViewAccessibility")
  override fun onTouchEvent(event: MotionEvent?): Boolean {
    when(event?.action) {
      MotionEvent.ACTION_UP -> {
        listener?.actionUp(scrollHeight)
      }
    }
    return super.onTouchEvent(event)
  }


  fun setOnScrollChangedCallback(listener: OnScrollListener) {
    this.listener = listener
  }
}
