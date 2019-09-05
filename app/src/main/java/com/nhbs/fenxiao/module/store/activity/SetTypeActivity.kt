package com.nhbs.fenxiao.module.store.activity

import android.os.Bundle
import android.view.View
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.module.store.NotSetTypeActivity
import com.nhbs.fenxiao.module.store.TypeManagerActivity
import com.nhbs.fenxiao.module.store.pop.CreateNewTypePopUpWindow
import com.nhbs.fenxiao.module.store.presenter.SetTypePresenter
import com.nhbs.fenxiao.module.store.presenter.SetTypeViewer
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.activity_set_type_view.add_new_type
import kotlinx.android.synthetic.main.activity_set_type_view.no_set_type
import kotlinx.android.synthetic.main.activity_set_type_view.type_manager

class SetTypeActivity : BaseBarActivity(), SetTypeViewer {

  @PresenterLifeCycle
  internal var presenter = SetTypePresenter(this)
  private var createNewTypePop: CreateNewTypePopUpWindow? = null
  override fun setView(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_set_type_view)
  }

  override fun loadData() {
    setTitle("分类")
    initListener()

  }

  private fun initListener() {
    no_set_type.setOnClickListener {
      launchHelper.startActivity(NotSetTypeActivity::class.java)
    }
    add_new_type.setOnClickListener {
      createNewTypePop = CreateNewTypePopUpWindow(activity)
      with(createNewTypePop!!) {
        setCancelListener(View.OnClickListener {
          dismiss()
        })
        setAgreeListener(View.OnClickListener {
          dismiss()
        })
        showPopupWindow()
      }


    }
    type_manager.setOnClickListener {
        launchHelper.startActivity(TypeManagerActivity::class.java)
    }
  }
}
