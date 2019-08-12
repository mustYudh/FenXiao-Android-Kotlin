package com.nhbs.fenxiao.module.store.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.widget.EditText
import android.widget.TextView
import com.denghao.control.view.utils.UpdataCurrentFragment
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseBarFragment
import com.nhbs.fenxiao.data.UserProfile
import com.nhbs.fenxiao.module.home.StatusBarColorManager
import com.nhbs.fenxiao.module.store.adapter.MiniStoreGoodsPageAdapter
import com.nhbs.fenxiao.module.store.bean.ShopInfoBean
import com.nhbs.fenxiao.module.store.presenter.MiniStorePresenter
import com.nhbs.fenxiao.module.store.presenter.MiniStoreViewer
import com.nhbs.fenxiao.module.web.WebViewActivity
import com.nhbs.fenxiao.utils.DensityUtils
import com.nhbs.fenxiao.utils.checkTextEmpty
import com.yu.common.mvp.PresenterLifeCycle
import com.yu.common.navigation.StatusBarFontColorUtil
import com.yu.common.ui.BadgeView
import kotlinx.android.synthetic.main.fragment_mini_store_layout.describes
import kotlinx.android.synthetic.main.fragment_mini_store_layout.edit
import kotlinx.android.synthetic.main.fragment_mini_store_layout.tv_open_store
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView
import java.util.ArrayList

/**
 * @author yudenghao
 * @date 2019-06-04
 */
class MiniStoreFragment : BaseBarFragment(), MiniStoreViewer, UpdataCurrentFragment {


  @PresenterLifeCycle
  private val mPresenter = MiniStorePresenter(this)

  private val tabTitles = ArrayList<String>()
  private var mMagicIndicator: MagicIndicator? = null
  private var mViewPager: ViewPager? = null
  private var mPagerAdapter: MiniStoreGoodsPageAdapter? = null

  override fun getActionBarLayoutId(): Int {
    return R.layout.action_bar_page_fragment_mini_store_layout
  }

  override fun isImmersionBar(): Boolean {
    return true
  }

  override fun getContentViewId(): Int {
    return R.layout.fragment_mini_store_layout
  }

  override fun setView(savedInstanceState: Bundle?) {
    val badgeView = bindView<BadgeView>(R.id.badge_view)
    badgeView.setBadgeCount(99)
    mMagicIndicator = bindView(R.id.magic_indicator)
    mViewPager = bindView(R.id.view_pager)
    tabTitles.add("商品")
    tabTitles.add("活动")
    tabTitles.add("审核记录")
    initTab()
    tv_open_store.setOnClickListener {
      launchHelper.startActivity(WebViewActivity.callIntent(activity, "",
          "http://app.novobus.cn/openstore?token=" + UserProfile.getInstance().appToken))
    }
    edit.setOnClickListener {
      describes.isEnabled = true
    }

  }

  private fun initTab() {
    mPagerAdapter = MiniStoreGoodsPageAdapter(childFragmentManager)
    mViewPager?.adapter = mPagerAdapter
    val commonNavigator = CommonNavigator(activity)
    commonNavigator.adapter = object : CommonNavigatorAdapter() {
      override fun getCount(): Int {
        return tabTitles.size
      }

      override fun getTitleView(context: Context, index: Int): IPagerTitleView {
        val commonPagerTitleView = CommonPagerTitleView(context)
        commonPagerTitleView.setContentView(R.layout.item_top_tab_list)
        // 初始化
        val titleText = commonPagerTitleView.findViewById<TextView>(R.id.tv_title)
        titleText.text = tabTitles[index]

        commonPagerTitleView.onPagerTitleChangeListener =
            object : CommonPagerTitleView.OnPagerTitleChangeListener {
              override fun onSelected(index: Int, totalCount: Int) {
                titleText.textSize = 16f
                titleText.setTextColor(Color.parseColor("#000000"))
              }

              override fun onDeselected(index: Int, totalCount: Int) {
                titleText.textSize = 14f
                titleText.setTextColor(Color.parseColor("#A0A9BB"))
              }

              override fun onLeave(index: Int, totalCount: Int, leavePercent: Float,
                  leftToRight: Boolean) {
              }

              override fun onEnter(index: Int, totalCount: Int, enterPercent: Float,
                  leftToRight: Boolean) {
              }
            }
        commonPagerTitleView.setOnClickListener { view -> mViewPager!!.currentItem = index }
        return commonPagerTitleView
      }

      override fun getIndicator(context: Context): IPagerIndicator {
        val linePagerIndicator = LinePagerIndicator(context)
        linePagerIndicator.mode = LinePagerIndicator.MODE_EXACTLY
        linePagerIndicator.setColors(Color.parseColor("#FF3E2B"))
        linePagerIndicator.lineWidth = DensityUtils.dp2px(activity!!, 24f).toFloat()
        linePagerIndicator.lineHeight = DensityUtils.dp2px(activity!!, 2f).toFloat()
        return linePagerIndicator
      }
    }
    mMagicIndicator!!.navigator = commonNavigator
    ViewPagerHelper.bind(mMagicIndicator, mViewPager!!)

  }


  override fun onResume() {
    super.onResume()
    update(arguments)
  }

  override fun update(bundle: Bundle?) {
    val isMerchant = UserProfile.getInstance().isMerchant == 1
    bindView<TextView>(R.id.tv_open_store, !isMerchant)
    bindView<TextView>(R.id.tv_open_store_hint, !isMerchant)
    bindView<MagicIndicator>(R.id.magic_indicator, isMerchant)
    bindView<ViewPager>(R.id.view_pager, isMerchant)
  }


  override fun loadData() {
    if (UserProfile.getInstance().isMerchant == 1) {
      mPresenter.getShopInfo()
    }


  }


  override fun setShopInfo(info: ShopInfoBean?) {
    bindText<TextView>(R.id.province, info?.province)
    bindText<TextView>(R.id.shopName, info?.shopName)
    bindText<EditText>(R.id.describes, info?.describes)
    bindView<EditText>(R.id.describes_root, !info?.describes.checkTextEmpty())
    bindText<TextView>(R.id.usersNum, info?.usersNum.toString())
    bindText<TextView>(R.id.ordersNum, info?.ordersNum.toString())
    bindText<TextView>(R.id.priceNum, info?.priceNum.toString())
  }


  override fun onPageInTop() {
    super.onPageInTop()
    StatusBarColorManager.isDark = false
    StatusBarFontColorUtil.statusBarDarkMode(activity)
  }
}
