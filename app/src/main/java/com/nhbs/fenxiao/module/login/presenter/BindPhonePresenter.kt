package com.nhbs.fenxiao.module.login.presenter

import android.annotation.SuppressLint
import android.text.TextUtils
import com.nhbs.fenxiao.data.UserProfile
import com.nhbs.fenxiao.http.api.AppApiServices
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber
import com.nhbs.fenxiao.module.login.bean.LoginInfoBean
import com.nhbs.fenxiao.utils.showToast
import com.xuexiang.xhttp2.XHttpProxy
import com.yu.common.framework.BaseViewPresenter
import com.yu.common.utils.PhoneUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


@SuppressLint("CheckResult")
class BindPhonePresenter(viewer: BindPhoneViewer) : BaseViewPresenter<BindPhoneViewer>(viewer) {

  private var timer: Disposable? = null


  fun sendVerCode(phone: String) {
    if (TextUtils.isEmpty(phone)) {
      showToast("手机号输入不能为空")
      return
    }
    if (!PhoneUtils.isPhoneLegal(phone)) {
      showToast("请输入正确的手机号码")
      return
    }

    XHttpProxy.proxy(AppApiServices::class.java)
        .sendVerCode(phone)
        .subscribeWith(object : LoadingRequestSubscriber<Any>(activity, false) {
          override fun onSuccess(o: Any) {
            showToast("发送成功")
            if (timer == null) {
              timer = Observable.interval(0, 1, TimeUnit.SECONDS)
                  .take(60)
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe {
                    getViewer()?.sendVerCodeSuccess(it)
                  }
            }
          }
        })
  }


  fun login(phone: String, code: String) {
    if (TextUtils.isEmpty(phone)) {
      showToast("手机号输入不能为空")
      return
    }
    if (!PhoneUtils.isPhoneLegal(phone)) {
      showToast("请输入正确的手机号码")
      return
    }
    if (TextUtils.isEmpty(code)) {
      showToast("验证码输入不能为空")
      return
    }
    if (!TextUtils.isEmpty(code)) {
      XHttpProxy.proxy(AppApiServices::class.java).login(phone, code, "")
          .subscribeWith(object : LoadingRequestSubscriber<LoginInfoBean>(activity, false) {
            override fun onSuccess(loginInfoBean: LoginInfoBean) {
              assert(getViewer() != null)
              UserProfile.getInstance().appLogin(loginInfoBean)

            }
          })

    }
  }


  override fun willDestroy() {
    super.willDestroy()
    if (timer != null) {
      timer!!.dispose()
      timer = null
    }
  }

}