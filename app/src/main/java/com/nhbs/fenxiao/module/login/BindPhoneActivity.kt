package com.nhbs.fenxiao.module.login

import android.annotation.SuppressLint
import android.os.Bundle

import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.module.login.presenter.BindPhonePresenter
import com.nhbs.fenxiao.module.login.presenter.BindPhoneViewer
import com.nhbs.fenxiao.utils.getInputText
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.activity_bind_phone_view.input_ver_code
import kotlinx.android.synthetic.main.activity_bind_phone_view.login
import kotlinx.android.synthetic.main.activity_bind_phone_view.phone_number
import kotlinx.android.synthetic.main.activity_bind_phone_view.send_ver_code


class BindPhoneActivity : BaseBarActivity(), BindPhoneViewer {


  @PresenterLifeCycle
  internal var mPresenter = BindPhonePresenter(this)

  override fun setView(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_bind_phone_view)
  }

  override fun loadData() {
    setTitle("绑定手机号")
    login.setOnClickListener {
      mPresenter.login(phone_number.getInputText(), input_ver_code.getInputText())
    }
    send_ver_code.setOnClickListener {
      mPresenter.sendVerCode(phone_number.getInputText())
    }

  }


  @SuppressLint("SetTextI18n")
  override fun sendVerCodeSuccess(time: Long) {
    if (time < 59) {
      send_ver_code.text = "重新发送 (${59 - time})"
    } else {
      send_ver_code.text = "发送验证码"
    }
    send_ver_code.isEnabled = time.equals(59)
  }
}
