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
import com.nhbs.fenxiao.module.store.activity.presenter.TypeInfoPresenter
import com.nhbs.fenxiao.module.store.activity.presenter.TypeInfoViewer
import com.nhbs.fenxiao.module.store.bean.GetGoodsParams
import com.nhbs.fenxiao.module.store.bean.GoodsListBean.GoodsInfoBean
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.activity_type_info_view.refresh


class TypeInfoActivity : BaseBarActivity(), TypeInfoViewer {
  private var params = GetGoodsParams()


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
    params.classId = typeName
    setTitle(typeName)
    val spannableString = SpannableString("编辑")
    val colorSpan = ForegroundColorSpan(Color.parseColor("#FF3539"))
    spannableString.setSpan(colorSpan, 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
    setRightMenu(spannableString) {

    }
    presenter.getGoodsList(params,null)
    refresh.setOnRefreshListener {
      pageNum = 1
      params.pageNum = pageNum
      presenter.getGoodsList(params,it,0)
    }

    refresh.setOnLoadMoreListener {
      pageNum++
      params.pageNum = pageNum
      presenter.getGoodsList(params,it,0)
    }
  }


  override fun setGoodsInfoList(list: List<GoodsInfoBean>?) {

  }

}