package com.nhbs.fenxiao.module.store.pop

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.module.store.adapter.TypePopListAdapter
import com.nhbs.fenxiao.module.store.bean.ClassTOS
import com.nhbs.fenxiao.utils.setLinearLayoutAdapter
import com.yu.common.windown.BasePopupWindow

/**
 * @author yudenghao
 * @date 2019-08-28
 */
class ChooseTypePopUpWindow(context: Context) : BasePopupWindow(context,
    View.inflate(context, R.layout.dialog_goods_add_classification, null),
    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) {

  private var adapter = TypePopListAdapter()
  private var typeId : String = ""

  init {
    isFocusable = true
  }

  override fun getBackgroundShadow(): View {
    return bindView(R.id.root)
  }

  override fun getContainer(): View {
    return bindView(R.id.container)
  }

  fun showList(list: List<ClassTOS>){

    bindView<RecyclerView>(R.id.recycle).setLinearLayoutAdapter(adapter)
    adapter.setNewData(list)
    adapter.setOnItemClickListener { adapter, view, position ->
      list.forEach {
        it.isChecked = false
      }
      list[position].isChecked = true
      typeId = list[position].id
      adapter.setNewData(list)
    }
  }

  fun getTypeId(): String {
    return typeId
  }

  fun setCancelListener(listener: View.OnClickListener): ChooseTypePopUpWindow {
    bindView<TextView>(R.id.text_cancel, listener)
    return this
  }

  fun setAgreeListener(listener: View.OnClickListener): ChooseTypePopUpWindow {
    bindView<TextView>(R.id.text_confirm, listener)
    return this
  }
}