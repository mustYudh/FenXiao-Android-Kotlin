package com.nhbs.fenxiao.module.store.activity.type

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.module.product.bean.FindMerchandiseListBean.RowsBean
import com.nhbs.fenxiao.module.store.activity.presenter.TypeInfoPresenter
import com.nhbs.fenxiao.module.store.activity.presenter.TypeInfoViewer
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.activity_type_info_view.refresh


class TypeInfoActivity : BaseBarActivity(), TypeInfoViewer {


  @PresenterLifeCycle
  internal var presenter = TypeInfoPresenter(this)
  private var typeName: String? = null
  private var pageNum = 1

  companion object {
    private const val TYPE_NAME = "type_name"
    fun getIntent(context: Context,typeNmae: String): Intent {
      val intent = Intent(context,TypeInfoActivity::class.java)
      intent.putExtra(TYPE_NAME,typeNmae)
      return intent
    }
  }



  override fun setView(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_type_info_view)
  }

  override fun loadData() {
    typeName = intent.getStringExtra(TYPE_NAME)
    setTitle(typeName)
    val spannableString = SpannableString("编辑")
    val colorSpan = ForegroundColorSpan(Color.parseColor("#FF3539"))
    spannableString.setSpan(colorSpan, 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
    setRightMenu(spannableString) {

    }
    presenter.getMerchandiseClassList(typeName!!,pageNum,null)
    refresh.setOnRefreshListener {
      pageNum = 1
      presenter.getMerchandiseClassList(typeName!!,pageNum,it,0)
    }

    refresh.setOnLoadMoreListener {
      pageNum++
      presenter.getMerchandiseClassList(typeName!!,pageNum,it,1)
    }
  }

  override fun getMerchandiseClassListSuccess(rows: List<RowsBean>?) {

  }
}
