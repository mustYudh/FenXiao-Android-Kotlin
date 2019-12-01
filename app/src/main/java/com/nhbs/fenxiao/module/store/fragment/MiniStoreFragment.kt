package com.nhbs.fenxiao.module.store.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.denghao.control.view.utils.UpdataCurrentFragment
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.Observer
import com.netease.nimlib.sdk.msg.MsgService
import com.netease.nimlib.sdk.msg.MsgServiceObserve
import com.netease.nimlib.sdk.msg.model.RecentContact
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseBarFragment
import com.nhbs.fenxiao.data.UserProfile
import com.nhbs.fenxiao.module.home.StatusBarColorManager
import com.nhbs.fenxiao.module.store.activity.OrderManagerActivity
import com.nhbs.fenxiao.module.store.activity.RedactStoreActivity
import com.nhbs.fenxiao.module.store.adapter.MiniStoreGoodsPageAdapter
import com.nhbs.fenxiao.module.store.bean.ShopInfoBean
import com.nhbs.fenxiao.module.store.presenter.MiniStorePresenter
import com.nhbs.fenxiao.module.store.presenter.MiniStoreViewer
import com.nhbs.fenxiao.module.web.WebViewActivity
import com.nhbs.fenxiao.utils.DensityUtils
import com.nhbs.fenxiao.utils.checkTextEmpty
import com.yu.common.glide.ImageLoader
import com.yu.common.mvp.PresenterLifeCycle
import com.yu.common.navigation.StatusBarFontColorUtil
import com.yu.common.ui.BadgeView
import kotlinx.android.synthetic.main.fragment_mini_store_layout.*
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView
import java.util.*

/**
 * @author yudenghao
 * @date 2019-06-04
 */
class MiniStoreFragment : BaseBarFragment(), MiniStoreViewer, UpdataCurrentFragment {


    @PresenterLifeCycle
    private val mPresenter = MiniStorePresenter(this)

    private val tabTitles = ArrayList<String>()
    private var shopId: String? = ""
    private var mMagicIndicator: MagicIndicator? = null
    private var mViewPager: ViewPager? = null
    private var mPagerAdapter: MiniStoreGoodsPageAdapter? = null
    private var mService = NIMClient.getService(MsgServiceObserve::class.java)
    var badgeView: BadgeView? = null
    var mMessageObserver: Observer<List<RecentContact>> = Observer { contacts: List<RecentContact> ->
        getUnReadMessageCont()
    }


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

    }

    private fun initListener() {
        tv_open_store.setOnClickListener {
            launchHelper.startActivity(WebViewActivity.callIntent(activity, "",
                    "http://app.novobus.cn/openstore?token=" + UserProfile.getInstance().appToken))
        }
        order_manager.setOnClickListener {
            launchHelper.startActivity(OrderManagerActivity::class.java)
        }
        edit.setOnClickListener {
            launchHelper.startActivity(RedactStoreActivity.getIntent(activity, shopId))
        }
    }

    private fun initTab() {
        mPagerAdapter = MiniStoreGoodsPageAdapter(childFragmentManager)
        mViewPager?.adapter = mPagerAdapter
        mViewPager?.offscreenPageLimit = 3
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
                linePagerIndicator.roundRadius = 4F
                linePagerIndicator.lineWidth = DensityUtils.dp2px(activity!!, 40f).toFloat()
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
        mPresenter.getShopInfo()
        getUnReadMessageCont()
    }


    override fun loadData() {
        badgeView = bindView(R.id.badge_view)
        mService.observeRecentContact(mMessageObserver, true)
        mMagicIndicator = bindView(R.id.magic_indicator)
        mViewPager = bindView(R.id.view_pager)
        tabTitles.add("商品")
        tabTitles.add("活动")
        tabTitles.add("审核记录")
        initTab()
        initListener()
        getUnReadMessageCont()
    }


    override fun setShopInfo(info: ShopInfoBean?) {
        openStoreStatus(true)
        shopId = info?.shopId
        bindText<TextView>(R.id.province, if (info?.province.checkTextEmpty()) "${info?.province}${info?.city}${info?.district}" else "请选择地址")
        bindText<TextView>(R.id.shopName, info?.shopName)
        bindText<TextView>(R.id.describes, info?.describes)

        bindText<TextView>(R.id.usersNum, info?.usersNum.toString())
        bindText<TextView>(R.id.ordersNum, info?.ordersNum.toString())
        bindText<TextView>(R.id.priceNum, info?.priceNum.toString())
        bindView<View>(R.id.red_hint, info?.status == 1)
        bindText<TextView>(R.id.state, if (info?.status == 1) "未认证" else "已认证")
        ImageLoader.getInstance().displayImage(header, info?.headImage, R.drawable.ic_normal_header)
    }

    override fun needOpenStore() {
        openStoreStatus(false)
    }

    private fun openStoreStatus(
            isMerchant: Boolean) {
        bindView<TextView>(R.id.tv_open_store, !isMerchant)
        bindView<TextView>(R.id.tv_open_store_hint, !isMerchant)
        bindView<MagicIndicator>(R.id.magic_indicator, isMerchant)
        bindView<ViewPager>(R.id.view_pager, isMerchant)
        bindView<ViewPager>(R.id.order_info_root, isMerchant)
        bindView<TextView>(R.id.province, isMerchant)
        bindView<TextView>(R.id.state, isMerchant)
        bindView<LinearLayout>(R.id.describes_root, isMerchant)
    }


    override fun onPageInTop() {
        super.onPageInTop()
        StatusBarColorManager.isDark = false
        StatusBarFontColorUtil.statusBarDarkMode(activity)
    }


    override fun getUnReadMessageCont() {
        val unreadNum = NIMClient.getService(MsgService::class.java).totalUnreadCount
        badgeView?.setHint(unreadNum)

    }

    override fun onDestroy() {
        mService.observeRecentContact(mMessageObserver, false)
        super.onDestroy()
    }
}
