package com.nhbs.fenxiao.module.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.nhbs.fenxiao.base.BaseFragment
import com.nhbs.fenxiao.module.store.adapter.OrderListAdapter
import com.nhbs.fenxiao.module.store.bean.OrderListBean
import com.nhbs.fenxiao.utils.setLinearLayoutAdapter
import kotlinx.android.synthetic.main.fragment_order_manager.list
import kotlinx.android.synthetic.main.fragment_order_manager.status_root



/**
 * @author yudneghao
 * @date 2019-08-18
 */


class OrderManagerFragment : BaseFragment() {
  private var position: Int = 0
  private var adapter = OrderListAdapter()


  override fun getContentViewId(): Int {
    return com.nhbs.fenxiao.R.layout.fragment_order_manager
  }

  override fun setView(savedInstanceState: Bundle?) {

  }

  override fun loadData() {
    for (item in 0..3) {
      val statusTab = LayoutInflater.from(activity!!).inflate(com.nhbs.fenxiao.R.layout.item_goods_status,
          status_root, false)
      val statusRoot: LinearLayout = statusTab.findViewById(com.nhbs.fenxiao.R.id.tba_root)
      val text: TextView = statusTab.findViewById(com.nhbs.fenxiao.R.id.text)
      val count: TextView = statusTab.findViewById(com.nhbs.fenxiao.R.id.count)
      when (item) {
        0 -> {
          statusRoot.isSelected = true
          text.isSelected = true
          count.isSelected = true
          text.text = "待发货"
          count.text = "0"
        }
        1 -> {
          statusRoot.isSelected = false
          text.text = "待付款"
          count.text = "0"
        }
        2 -> {
          statusRoot.isSelected = false
          text.text = "已发货"
          count.text = "0"
        }
        3 -> {
          statusRoot.isSelected = false
          text.text = "退款/售后"
          count.text = "0"
        }
      }
      status_root.addView(statusTab)
    }

    for (i in 0 until 4) {
      status_root.getChildAt(i).setOnClickListener {
        for (item in 0..3) {
          val statusTab = status_root.getChildAt(item)
          val statusRoot: LinearLayout = statusTab.findViewById(com.nhbs.fenxiao.R.id.tba_root)
          val text: TextView = statusTab.findViewById(com.nhbs.fenxiao.R.id.text)
          val count: TextView = statusTab.findViewById(com.nhbs.fenxiao.R.id.count)
          statusTab.isEnabled = i != item
          statusRoot.isSelected = i == item
          text.isSelected = i == item
          count.isSelected = i == item
          position = i
        }
      }
    }

    list.setLinearLayoutAdapter(adapter)
    adapter.addData(OrderListBean())
  }


}