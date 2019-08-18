package com.nhbs.fenxiao.module.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.nhbs.fenxiao.base.BaseFragment
import com.nhbs.fenxiao.module.store.adapter.OrderListAdapter
import com.nhbs.fenxiao.module.store.bean.OrderListBean
import com.nhbs.fenxiao.utils.setLinearLayoutAdapter
import kotlinx.android.synthetic.main.fragment_order_tab_manager.list
import kotlinx.android.synthetic.main.fragment_order_tab_manager.status_root


/**
 * @author yudneghao
 * @date 2019-08-18
 */


class OrderManagerTabFragment : BaseFragment() {
  private var position: Int = 0
  val adapter0 = OrderListAdapter(0)
  val adapter1 = OrderListAdapter(1)
  val adapter2 = OrderListAdapter(2)
  val adapter3 = OrderListAdapter(3)

  override fun getContentViewId(): Int {
    return com.nhbs.fenxiao.R.layout.fragment_order_tab_manager
  }

  override fun setView(savedInstanceState: Bundle?) {

  }

  override fun loadData() {
    for (item in 0..3) {
      val statusTab = LayoutInflater.from(activity!!).inflate(
          com.nhbs.fenxiao.R.layout.item_goods_status,
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
          when(position) {
            0 -> {
              list.setLinearLayoutAdapter(adapter0)
            }
            1 -> {
              list.setLinearLayoutAdapter(adapter1)
            }
            2 -> {
              list.setLinearLayoutAdapter(adapter2)
            }
            3 -> {
              list.setLinearLayoutAdapter(adapter3)
            }
          }
        }
      }
    }

    list.setLinearLayoutAdapter(adapter0)
    adapter0.addData(OrderListBean())
    adapter1.addData(OrderListBean())
    adapter2.addData(OrderListBean())
    adapter3.addData(OrderListBean())
  }


}