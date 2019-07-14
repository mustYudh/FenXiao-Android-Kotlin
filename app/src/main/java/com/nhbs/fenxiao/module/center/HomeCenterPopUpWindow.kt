package com.nhbs.fenxiao.module.center

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.app.Activity
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Build.VERSION
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.module.center.activity.ReleaseACGoodsActivity
import com.nhbs.fenxiao.module.center.activity.ReleaseAdvertisingActivity
import com.nhbs.fenxiao.module.center.activity.ReleaseGoodsActivity
import com.nhbs.fenxiao.module.home.StatusBarColorManager
import com.nhbs.fenxiao.utils.DensityUtils
import com.yu.common.launche.LauncherHelper
import com.yu.common.navigation.StatusBarFontColorUtil
import com.yu.common.windown.BasePopupWindow
import com.zhouwei.blurlibrary.EasyBlur


/**
 * @author yudenghao
 * @date 2019-06-25
 */
class HomeCenterPopUpWindow(context: Activity) : BasePopupWindow(
    context,
    View.inflate(context, R.layout.home_center_pop_up_window_layout, null),
    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
), View.OnClickListener {


    var activity: Activity? = null
    var cancelView: ImageView? = null
    var releaseGoods: LinearLayout? = null
    var advertising: LinearLayout? = null
    var releaseActivity: LinearLayout? = null

    init {
        isClippingEnabled = false
        activity = context
        releaseGoods = bindView(R.id.release_goods, this)
        advertising = bindView(R.id.advertising, this)
        releaseActivity = bindView(R.id.activity, this)
        this.height = DensityUtils.getDisplayHeight(getContext())
        if (VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                val mLayoutInScreen = PopupWindow::class.java.getDeclaredField("mLayoutInScreen")
                mLayoutInScreen.isAccessible = true
                mLayoutInScreen.set(this, true)
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

        }
        val activityView = context.window.decorView
        activityView.isDrawingCacheEnabled = true
        activityView.destroyDrawingCache()
        activityView.buildDrawingCache()
        val bmp = activityView.drawingCache

        val bitmap = BitmapFactory.decodeResource(activity!!.resources, R.drawable.ic_center_tab_bg)
        val finalBitmap = EasyBlur.with(activity)
            .bitmap(bmp) //要模糊的图片
            .radius(15)//模糊半径
            .blur()
        val background = bindView<ImageView>(R.id.pop_bg)
        background.setImageBitmap(finalBitmap)
        cancelView = bindView(R.id.cancel) {
            dismiss(null)
        }

    }



    private fun dismiss(call: (() -> Unit)?) {
        if (StatusBarColorManager.isDark) {
            StatusBarFontColorUtil.StatusBarLightMode(activity)
        } else {
            StatusBarFontColorUtil.statusBarDarkMode(activity)
        }
        val array = floatArrayOf(DensityUtils.dp2px(context, 45F).toFloat(), DensityUtils.dp2px(context, 0F).toFloat())
        showAnimator(cancelView!!, "rotation", 200, array, null) {
            cancelView!!.visibility = View.GONE
            bindView<LinearLayout>(R.id.top_view).visibility = View.GONE
            StatusBarFontColorUtil.statusBarDarkMode(activity!!)
            val array1 =
                floatArrayOf(DensityUtils.dp2px(context, -220F).toFloat(), DensityUtils.dp2px(context, 88F).toFloat())
            val array2 =
                floatArrayOf(DensityUtils.dp2px(context, -220F).toFloat(), DensityUtils.dp2px(context, 88F).toFloat())
            val array3 =
                floatArrayOf(DensityUtils.dp2px(context, -220F).toFloat(), DensityUtils.dp2px(context, 88F).toFloat())
            showAnimator(releaseGoods!!, "translationY", 200, array1, DecelerateInterpolator())
            showAnimator(advertising!!, "translationY", 300, array2, DecelerateInterpolator())
            showAnimator(releaseActivity!!, "translationY", 400, array3, DecelerateInterpolator()) {
                if(call != null) { call() }
                super.dismiss()
            }
        }

    }

    override fun getBackgroundShadow(): View? {
        return null
    }

    override fun getContainer(): View? {
        return null
    }


    override fun showPopupWindow() {
        val array = floatArrayOf(DensityUtils.dp2px(context, 0F).toFloat(), DensityUtils.dp2px(context, 45F).toFloat())
        StatusBarFontColorUtil.statusBarDarkMode(activity!!)
        val array1 =
            floatArrayOf(DensityUtils.dp2px(context, -88F).toFloat(), DensityUtils.dp2px(context, -220F).toFloat())
        val array2 =
            floatArrayOf(DensityUtils.dp2px(context, -88F).toFloat(), DensityUtils.dp2px(context, -220F).toFloat())
        val array3 =
            floatArrayOf(DensityUtils.dp2px(context, -88F).toFloat(), DensityUtils.dp2px(context, -220F).toFloat())
        showAnimator(cancelView!!, "rotation", 200, array, BounceInterpolator()) {
            showAnimator(releaseGoods!!, "translationY", 200, array1, BounceInterpolator())
            showAnimator(advertising!!, "translationY", 300, array2, BounceInterpolator())
            showAnimator(releaseActivity!!, "translationY", 400, array3, BounceInterpolator())
        }
        super.showPopupWindow()
    }


    private fun showAnimator(
        view: View,
        name: String,
        time: Long,
        distance: FloatArray,
        interpolator: TimeInterpolator?,
        listener: () -> Unit? = {}
    ) {
        val anim = ObjectAnimator.ofFloat(view, name, *distance)
        anim.duration = time
        anim.interpolator = interpolator
        anim.start()
        anim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                listener()
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }

        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.release_goods -> {
                dismiss { LauncherHelper.from(activity).startActivity(ReleaseGoodsActivity::class.java) }
            }
            R.id.advertising -> {
                dismiss { LauncherHelper.from(activity).startActivity(ReleaseAdvertisingActivity::class.java) }
            }
            R.id.activity -> {
                dismiss { LauncherHelper.from(activity).startActivity(ReleaseACGoodsActivity::class.java) }
            }
        }
    }

}