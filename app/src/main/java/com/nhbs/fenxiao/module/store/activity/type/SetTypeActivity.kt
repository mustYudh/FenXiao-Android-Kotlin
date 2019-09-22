package com.nhbs.fenxiao.module.store.activity.type

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.module.store.NotSetTypeActivity
import com.nhbs.fenxiao.module.store.TypeManagerActivity
import com.nhbs.fenxiao.module.store.adapter.TypeCountListAdapter
import com.nhbs.fenxiao.module.store.bean.ClassTOS
import com.nhbs.fenxiao.module.store.pop.CreateNewTypePopUpWindow
import com.nhbs.fenxiao.module.store.presenter.SetTypePresenter
import com.nhbs.fenxiao.module.store.presenter.SetTypeViewer
import com.nhbs.fenxiao.utils.checkTextEmpty
import com.nhbs.fenxiao.utils.setLinearLayoutAdapter
import com.nhbs.fenxiao.utils.showToast
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.activity_set_type_view.add_new_type
import kotlinx.android.synthetic.main.activity_set_type_view.no_set_type
import kotlinx.android.synthetic.main.activity_set_type_view.no_type_goods_count
import kotlinx.android.synthetic.main.activity_set_type_view.type_count_list_count
import kotlinx.android.synthetic.main.activity_set_type_view.type_manager

class SetTypeActivity : BaseBarActivity(), SetTypeViewer {

  @PresenterLifeCycle
  internal var presenter = SetTypePresenter(this)
  private var createNewTypePop: CreateNewTypePopUpWindow? = null
  private val adapter = TypeCountListAdapter()
  override fun setView(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_set_type_view)
  }

  //  件商品
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
          if (getInputInfo().checkTextEmpty()) {
            showToast("请输入分类名称")
          } else {
            presenter.createNewTypeName(getInputInfo())
            dismiss()
          }

        })
        showPopupWindow()
      }


    }
    type_manager.setOnClickListener {
      launchHelper.startActivity(TypeManagerActivity::class.java)
    }
    adapter.setOnItemClickListener { adapter, _, position ->
        val data = adapter.data[position] as ClassTOS
        launchHelper.startActivity(TypeInfoActivity.getIntent(activity,data.classify))
    }
  }

  override fun onResume() {
    super.onResume()
    presenter.getGoodsCount()
  }


  @SuppressLint("SetTextI18n")
  override fun setGoodsTypeCount(classListTOS: List<ClassTOS>, notTypeCount: Int) {
    no_type_goods_count.text = "${notTypeCount}件商品"
    type_count_list_count.setLinearLayoutAdapter(adapter)
    adapter.setNewData(classListTOS)
  }
}
