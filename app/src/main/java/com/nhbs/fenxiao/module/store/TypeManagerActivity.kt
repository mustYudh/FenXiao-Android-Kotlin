package com.nhbs.fenxiao.module.store

import android.os.Bundle
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.module.store.adapter.TypeManageListAdapter
import com.nhbs.fenxiao.module.store.bean.ClassTOS
import com.nhbs.fenxiao.module.store.pop.CreateNewTypePopUpWindow
import com.nhbs.fenxiao.module.store.presenter.TypeManagerPresenter
import com.nhbs.fenxiao.module.store.presenter.TypeManagerViewer
import com.nhbs.fenxiao.utils.checkTextEmpty
import com.nhbs.fenxiao.utils.setLinearLayoutAdapter
import com.nhbs.fenxiao.utils.showToast
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.activity_type_manager_view.*

class TypeManagerActivity : BaseBarActivity(), TypeManagerViewer {

  @PresenterLifeCycle
  internal var presenter = TypeManagerPresenter(this)
  private var createNewTypePop: CreateNewTypePopUpWindow? = null
  private val adapter = TypeManageListAdapter()

  override fun setView(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_type_manager_view)
  }

  override fun loadData() {
    setTitle("管理分类")
    create_new_type.setOnClickListener {
      createNewTypePop = CreateNewTypePopUpWindow(activity)
      with(createNewTypePop!!) {
        setCancelListener(android.view.View.OnClickListener {
          dismiss()
        })
        setAgreeListener(android.view.View.OnClickListener {
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
  }

  override fun onResume() {
    super.onResume()
    presenter.getGoodsCount()
  }

  override fun setGoodsTypeCount(classListTOS: List<ClassTOS>, notTypeCount: Int) {
    type_manage_list.setLinearLayoutAdapter(adapter)
    adapter.setOnItemChildClickListener { adapter, view, position ->
      if(view.id == R.id.img_change) {
        createNewTypePop = CreateNewTypePopUpWindow(activity)
        with(createNewTypePop!!) {
          setCancelListener(android.view.View.OnClickListener {
            dismiss()
          })
          setAgreeListener(android.view.View.OnClickListener {
            if (getInputInfo().checkTextEmpty()) {
              showToast("请输入分类名称")
            } else {
              presenter.changeTypeName(classListTOS[position].id,getInputInfo())
              dismiss()
            }

          })
        }
        createNewTypePop!!.setTitle("修改分类")
        createNewTypePop!!.showPopupWindow()
      }else if(view.id == R.id.img_delete) {
        presenter.deleteType(classListTOS[position].id)
      }
    }
    adapter.setNewData(classListTOS)
  }
}
