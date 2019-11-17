package com.nhbs.fenxiao.module.store

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.module.store.adapter.NoTypeGoodsListAdapter
import com.nhbs.fenxiao.module.store.bean.ClassTOS
import com.nhbs.fenxiao.module.store.bean.GetGoodsParamsNew
import com.nhbs.fenxiao.module.store.bean.GoodsListBean
import com.nhbs.fenxiao.module.store.pop.ChooseTypePopUpWindow
import com.nhbs.fenxiao.module.store.presenter.NotSetTypePresenter
import com.nhbs.fenxiao.module.store.presenter.NotSetTypeViewer
import com.nhbs.fenxiao.utils.checkTextEmpty
import com.nhbs.fenxiao.utils.setLinearLayoutAdapter
import com.nhbs.fenxiao.utils.showToast
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.activity_not_type_layout.*

class NotSetTypeActivity : BaseBarActivity(), NotSetTypeViewer {

  private var params = GetGoodsParamsNew()
  @PresenterLifeCycle
  internal var presenter = NotSetTypePresenter(this)
  private var typeId : String? = null
  private val adapter = NoTypeGoodsListAdapter()
  private var chooseTypePopUpWindow: ChooseTypePopUpWindow? = null
  private var chooseId : String = ""

  companion object {
    private const val TYPE_ID = "type_id"
    fun getIntent(context: Context, typeId: String?): Intent {
      val intent = Intent(context, NotSetTypeActivity::class.java)
      intent.putExtra(TYPE_ID,typeId)
      return intent
    }
  }

  override fun setView(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_not_type_layout)
  }

  override fun loadData() {
    setTitle("未分类")
    typeId = intent.getStringExtra(TYPE_ID)
    params.classId = typeId
    initListener()
    presenter.getGoodsListNew(params)
  }

  private fun initListener() {
    ll_choose_type.setOnClickListener {
      if (chooseId.checkTextEmpty()) {
        showToast("请选择商品")
      }else {
        presenter.getTypeGoodsCount()
      }
    }
  }

  @SuppressLint("SetTextI18n")
  override fun setGoodsInfoList(list: List<GoodsListBean.GoodsInfoBean>?) {
    goods_info.setLinearLayoutAdapter(adapter)
    adapter.setNewData(list)
    adapter.setOnItemClickListener { adapter, view, position ->
      chooseId = ""

      if (list?.get(position)?.isChecked!!) {
        list[position].isChecked = false
      }else {
        list[position].isChecked = true
      }

      list?.forEach {
        if (it.isChecked) {
          chooseId = chooseId + "," + it.id
        }
      }

      adapter.setNewData(list)
    }
  }

  override fun showChooseTypeDialog(classListTOS: List<ClassTOS>) {
    chooseTypePopUpWindow = ChooseTypePopUpWindow(activity)
    with(chooseTypePopUpWindow!!) {
      setCancelListener(android.view.View.OnClickListener {
        dismiss()
      })
      setAgreeListener(android.view.View.OnClickListener {
        if (getTypeId().checkTextEmpty()) {
          showToast("请选择分类")
        } else {
          presenter.addGoods(chooseId.substring(1),getTypeId(),chooseTypePopUpWindow)
          dismiss()
        }
      })
    }
    chooseTypePopUpWindow!!.showList(classListTOS)
    chooseTypePopUpWindow!!.showPopupWindow()
  }

  override fun addGoodsSuc() {
    presenter.getGoodsListNew(params)
  }

}
