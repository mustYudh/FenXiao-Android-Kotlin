package com.nhbs.fenxiao.module.store.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseFragment
import com.nhbs.fenxiao.http.loading.NetLoadingDialog
import com.nhbs.fenxiao.module.mine.activity.BindAliPayActivity
import com.nhbs.fenxiao.module.mine.bean.MineUserInfoBean
import com.nhbs.fenxiao.module.store.presenter.IncomeAssetsPresenter
import com.nhbs.fenxiao.module.store.presenter.IncomeAssetsViewer
import com.nhbs.fenxiao.utils.dialog.IncomeAssetsDialog
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import com.yu.common.launche.LauncherHelper
import com.yu.common.mvp.PresenterLifeCycle
import com.yu.common.toast.ToastUtils
import com.yu.share.AuthLoginHelp
import com.yu.share.callback.AuthLoginCallback
import kotlinx.android.synthetic.main.fragment_income_assets_layout.*

/**
 * @author yudneghao
 * @date 2019-08-18
 */
class IncomeAssetsFragment : BaseFragment(), AuthLoginCallback, IncomeAssetsViewer {


    @PresenterLifeCycle
    private val mPresenter = IncomeAssetsPresenter(this)


    private var mAuthLoginHelp: AuthLoginHelp? = null

    companion object {
        private var fragment: Fragment? = null
        fun getFragment(): Fragment {
            return if (fragment == null) {
                fragment = IncomeAssetsFragment()
                fragment!!
            } else {
                fragment!!
            }

        }
    }

    override fun getContentViewId(): Int {
        return R.layout.fragment_income_assets_layout
    }

    override fun setView(savedInstanceState: Bundle?) {

    }

    override fun loadData() {
        mPresenter.getUserInfo()
        mAuthLoginHelp = AuthLoginHelp(activity)
        mAuthLoginHelp?.callback(this)
        bind_we_chat.setOnClickListener {
            val installWeChat = UMShareAPI.get(activity!!).isInstall(activity, SHARE_MEDIA.WEIXIN)
            if (installWeChat) {
                mAuthLoginHelp?.login(SHARE_MEDIA.WEIXIN)
            } else {
                ToastUtils.show("请先安装微信")
            }
        }
        bind_ali_pay.setOnClickListener {
            LauncherHelper.from(context).startActivityForResult(BindAliPayActivity::class.java, 1)
        }
    }


    override fun onStart(media: SHARE_MEDIA?) {
        NetLoadingDialog.showLoading(activity, false)

    }

    override fun onComplete(media: SHARE_MEDIA?, i: Int, map: MutableMap<String, String>?) {
        val openId = map?.get("openid")
        mPresenter.boundWinXin(openId)
    }

    override fun onError(media: SHARE_MEDIA?, i: Int, throwable: Throwable?) {
        NetLoadingDialog.dismissLoading()
    }

    override fun onCancel(media: SHARE_MEDIA?, i: Int) {
        NetLoadingDialog.dismissLoading()

    }


    override fun getUserInfoSuccess(info: MineUserInfoBean) {
        withdrawal.setOnClickListener {
            IncomeAssetsDialog(activity!!, info, object : AuthLoginCallback {
                override fun onStart(media: SHARE_MEDIA?) {
                    NetLoadingDialog.showLoading(activity, false)
                }

                override fun onComplete(media: SHARE_MEDIA?, i: Int, map: MutableMap<String, String>?) {
                    mPresenter.getUserInfo()
                }

                override fun onError(media: SHARE_MEDIA?, i: Int, throwable: Throwable?) {
                    NetLoadingDialog.dismissLoading()
                }

                override fun onCancel(media: SHARE_MEDIA?, i: Int) {
                    NetLoadingDialog.dismissLoading()
                }

            }).show()
        }
    }

    override fun boundWinXinSuccess() {
        mPresenter.getUserInfo()
        ToastUtils.show("绑定微信成功")
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            1 -> mPresenter.getUserInfo()
        }
    }


}