package com.nhbs.fenxiao.module.store.activity

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.design.widget.TabLayout.OnTabSelectedListener
import android.support.design.widget.TabLayout.Tab
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.R.layout
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.module.store.activity.presenter.OrderManagerPresenter
import com.nhbs.fenxiao.module.store.activity.presenter.OrderManagerViewer
import com.nhbs.fenxiao.module.store.adapter.OrderManagerAdapter
import com.yu.common.mvp.PresenterLifeCycle
import com.yu.common.ui.BadgeView
import com.yu.common.ui.ProxyDrawable
import com.yu.common.ui.Res
import com.yu.common.utils.DensityUtil
import kotlinx.android.synthetic.main.order_manager_title_bar_layout.ic_come_assets
import kotlinx.android.synthetic.main.order_manager_title_bar_layout.order_manager
import java.util.ArrayList

class OrderManagerActivity : BaseBarActivity(), OrderManagerViewer, OnTabSelectedListener {


  private var mViewPager: ViewPager? = null
  private var mTabLayout: TabLayout? = null
  private var mAdapter: OrderManagerAdapter? = null
  private var mBadgeViews: ArrayList<BadgeView>? = null

  override fun getActionBarLayoutId(): Int {
    return layout.order_manager_title_bar_layout
  }

  @PresenterLifeCycle
  internal var presenter = OrderManagerPresenter(this)
  private val tabTitles = ArrayList<String>()


  override fun setView(savedInstanceState: Bundle?) {
    setContentView(layout.activity_order_manager_view)
    initView()
    order_manager.setOnClickListener {
      order_manager.isSelected = true
      ic_come_assets.isSelected = false
    }

    ic_come_assets.setOnClickListener {
      order_manager.isSelected = false
      ic_come_assets.isSelected = true
    }
  }

  private fun initView() {
    order_manager.isSelected = true
    ic_come_assets.isSelected = false
    mViewPager = bindView(R.id.view_pager)
    tabTitles.add("进行中")
    tabTitles.add("已完成")
    mTabLayout = bindView<TabLayout>(R.id.magic_indicator)
    val tabStrip = mTabLayout?.getChildAt(0)
    if (tabStrip != null) {
      val proxyDrawable = ProxyDrawable(tabStrip, 2)
      proxyDrawable.setIndicatorColor(Res.color(R.color.red))
      proxyDrawable.setIndicatorPaddingLeft(DensityUtil.dip2px(activity, 70F))
      proxyDrawable.setIndicatorPaddingRight(DensityUtil.dip2px(activity, 70F))
      proxyDrawable.setRadius(DensityUtil.dip2px(2f))
      proxyDrawable.setIndicatorPaddingTop(DensityUtil.dip2px(activity, -3f))

      tabStrip.background = proxyDrawable
      mTabLayout?.addOnTabSelectedListener(this)
    }
    mAdapter = OrderManagerAdapter(supportFragmentManager)
    mViewPager?.adapter = mAdapter
    mTabLayout?.setupWithViewPager(mViewPager)
    for (i in 0 until 2) {
      val tab = mTabLayout?.getTabAt(i)
      if (tab != null) {
        val view = View.inflate(activity, layout.item_tab_order_manager, null)
        val textView = view.findViewById<TextView>(R.id.title)
        if (i == 0) {
          textView.text = "进行中"
        } else {
          textView.text = "已完成"
          textView.setTextColor(Res.color(R.color.A0A9BB))
        }
        tab.customView = view
      }
    }
    initBadgeViews()
    setUpTabBadge()
  }


  override fun onTabUnselected(tab: Tab?) {
    val view = tab?.customView
    if (view != null) {
      val textView: TextView = view.findViewById(R.id.title)
      textView.setTextColor(Res.color(R.color.A0A9BB))
    }
  }

  override fun onTabSelected(tab: Tab?) {
    val view = tab?.customView
    if (view != null) {
      val textView: TextView = view.findViewById(R.id.title)
      textView.setTextColor(Res.color(R.color.red))
    }
  }

  override fun onTabReselected(p0: Tab?) {
  }

  override fun loadData() {

  }


  private fun initBadgeViews() {
    if (mBadgeViews == null) {
      mBadgeViews = ArrayList()
      for (i in 0 until 2) {
        val tmp = BadgeView(activity)
        tmp.setBadgeMargin(110, 8, 0, 0)
        tmp.setPadding(10, 1, 10, 1)
        tmp.textSize = 10F
        tmp.setBackground(DensityUtil.dip2px(4F).toFloat(), Color.parseColor("#FF2751"))
        tmp.setTextColor(Color.parseColor("#FFFFFF"))
        mBadgeViews?.add(tmp)
      }
    }
  }

  private fun setUpTabBadge() {
    for (i in 0 until 2) {
      mBadgeViews?.get(i)?.setTargetView((mTabLayout?.getChildAt(0) as ViewGroup).getChildAt(i))
      if (i == 0) {
        mBadgeViews?.get(i)?.text = 12.toString()
      } else {
        mBadgeViews?.get(i)?.text = "99+"
      }

    }
  }

}
