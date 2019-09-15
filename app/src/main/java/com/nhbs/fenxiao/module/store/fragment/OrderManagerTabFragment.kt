package com.nhbs.fenxiao.module.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseFragment
import com.nhbs.fenxiao.module.store.adapter.OrderListAdapter
import com.nhbs.fenxiao.module.store.bean.OrderCountBean
import com.nhbs.fenxiao.module.store.bean.OrderInfo
import com.nhbs.fenxiao.module.store.bean.QueryShopKeeperOrdersParams
import com.nhbs.fenxiao.module.store.pop.EditPricePopupWindow
import com.nhbs.fenxiao.module.store.presenter.OrderManagerPresenter
import com.nhbs.fenxiao.module.store.presenter.OrderManagerViewer
import com.nhbs.fenxiao.utils.setLinearLayoutAdapter
import com.nhbs.fenxiao.utils.showToast
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.fragment_order_tab_manager.list
import kotlinx.android.synthetic.main.fragment_order_tab_manager.refresh
import kotlinx.android.synthetic.main.fragment_order_tab_manager.status_root


/**
 * @author yudneghao
 * @date 2019-08-18
 */


class OrderManagerTabFragment : BaseFragment(), OrderManagerViewer {


  @PresenterLifeCycle
  private val mPresenter = OrderManagerPresenter(this)
  var params0: QueryShopKeeperOrdersParams = QueryShopKeeperOrdersParams()
  var params1: QueryShopKeeperOrdersParams = QueryShopKeeperOrdersParams()
  var params2: QueryShopKeeperOrdersParams = QueryShopKeeperOrdersParams()
  var params3: QueryShopKeeperOrdersParams = QueryShopKeeperOrdersParams()


  private var position: Int = 0
  val adapter0 = OrderListAdapter(0)
  val adapter1 = OrderListAdapter(1)
  val adapter2 = OrderListAdapter(2)
  val adapter3 = OrderListAdapter(3)

  override fun getContentViewId(): Int {
    return R.layout.fragment_order_tab_manager
  }

  override fun setView(savedInstanceState: Bundle?) {

  }

  override fun loadData() {
    for (item in 0..3) {
      val statusTab = LayoutInflater.from(activity!!).inflate(R.layout.item_goods_status, status_root, false)
      val statusRoot: LinearLayout = statusTab.findViewById(R.id.tba_root)
      val text: TextView = statusTab.findViewById(R.id.text)
      val count: TextView = statusTab.findViewById(R.id.count)
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
          val statusRoot: LinearLayout = statusTab.findViewById(R.id.tba_root)
          val text: TextView = statusTab.findViewById(R.id.text)
          val count: TextView = statusTab.findViewById(R.id.count)
          statusTab.isEnabled = i != item
          statusRoot.isSelected = i == item
          text.isSelected = i == item
          count.isSelected = i == item
          position = i
          when (position) {
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

    refresh.setOnRefreshListener {
      when (position) {
        0 -> {
          params0.pageNum = 0
          mPresenter.findMyShopMerchandiseList(0, params0, it, 0)
        }
        1 -> {
          params1.pageNum = 0
          mPresenter.findMyShopMerchandiseList(1, params1, it, 0)
        }
        2 -> {
          params2.pageNum = 0
          mPresenter.findMyShopMerchandiseList(2, params2, it, 0)
        }
        3 -> {
          params3.pageNum = 0
          mPresenter.findMyShopMerchandiseList(3, params3, it, 0)
        }
      }
    }
    refresh.setOnLoadMoreListener {
      when (position) {
        0 -> {
          params0.pageNum = params0.pageNum + 1
          mPresenter.findMyShopMerchandiseList(0, params0, it, 1)
        }
        1 -> {
          params1.pageNum = params1.pageNum + 1
          mPresenter.findMyShopMerchandiseList(1, params1, it, 1)
        }
        2 -> {
          params2.pageNum = params2.pageNum + 1
          mPresenter.findMyShopMerchandiseList(2, params2, it, 1)
        }
        3 -> {
          params3.pageNum = params3.pageNum + 1
          mPresenter.findMyShopMerchandiseList(3, params3, it, 1)
        }
      }
    }

    adapter0.setOnItemChildClickListener { adapter, view, position ->
      when (view.id) {
        R.id.status_btn -> {
          val info = adapter.data[position] as OrderInfo
          mPresenter.goSendGoods(info, position)
        }
      }
    }


    adapter1.setOnItemChildClickListener { adapter, view, position ->
      val info = adapter.data[position] as OrderInfo
      when (view.id) {
        R.id.status_btn -> {
          val editPrice = EditPricePopupWindow(activity!!,info) { goodsPrice, packagePrice ->
            mPresenter.updateOrderPrice(info, goodsPrice, packagePrice, position)
          }
          editPrice.showPopupWindow()
        }
      }
    }


    adapter2.setOnItemChildClickListener { adapter, view, position ->
      when (view.id) {
        R.id.status_btn -> {

        }
      }
    }

    adapter3.setOnItemChildClickListener { adapter, view, position ->
      when (view.id) {
        R.id.status_btn -> {

        }
      }
    }

    params1.status = 1
    params2.status = 4
    params3.status = 5
    mPresenter.getOrdersCount()
    mPresenter.findMyShopMerchandiseList(0, params0)
    mPresenter.findMyShopMerchandiseList(1, params1)
    mPresenter.findMyShopMerchandiseList(2, params2)
    mPresenter.findMyShopMerchandiseList(3, params3)

  }


  override fun getGoodsInfo(rows: List<OrderInfo>, type: Int, position: Int) {
    when (position) {
      0 -> {
        if (type == 0) {
          adapter0.setNewData(rows)
        } else {
          adapter0.addData(rows)
        }
      }
      1 -> {
        if (type == 0) {
          adapter1.setNewData(rows)
        } else {
          adapter1.addData(rows)
        }
      }
      2 -> {
        if (type == 0) {
          adapter2.setNewData(rows)
        } else {
          adapter2.addData(rows)
        }
      }
      3 -> {
        if (type == 0) {
          adapter3.setNewData(rows)
        } else {
          adapter3.addData(rows)
        }
      }
    }
  }


  override fun setOrdersCount(count: OrderCountBean?) {
    for (i in 0 until 4) {
      val statusTab = status_root.getChildAt(i)
      val tabCount: TextView = statusTab.findViewById(R.id.count)
      when(i) {
        0 -> {
          tabCount.text = count?.tabOne.toString()
        }
        1 -> {
          tabCount.text = count?.tabTwo.toString()
        }
        2 -> {
          tabCount.text = count?.tabThree.toString()
        }
        3 -> {
          tabCount.text = count?.tabFour.toString()
        }
      }
    }
  }


  override fun goSendGoodsSuccess(info: OrderInfo, position: Int) {
    adapter0.remove(position)
    adapter3.addData(info)
    showToast("发货成功")
    mPresenter.getOrdersCount()
  }


  override fun updateOrderPriceSuccess(info: OrderInfo, position: Int) {
    adapter1.setData(position, info)
    showToast("修改成功")
    mPresenter.getOrdersCount()
  }


}