package com.nhbs.fenxiao.module.store.activity.type

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.view.View
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.module.store.activity.presenter.TypeInfoPresenter
import com.nhbs.fenxiao.module.store.activity.presenter.TypeInfoViewer
import com.nhbs.fenxiao.module.store.adapter.TypeGoodsListAdapter
import com.nhbs.fenxiao.module.store.bean.GetGoodsParamsNew
import com.nhbs.fenxiao.module.store.bean.GoodsListBean.GoodsInfoBean
import com.nhbs.fenxiao.utils.setLinearLayoutAdapter
import com.nhbs.fenxiao.utils.showToast
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.activity_type_info_view.*


class TypeInfoActivity : BaseBarActivity(), TypeInfoViewer {

//  private var params = GetGoodsParams()
  private var paramsN = GetGoodsParamsNew()
  @PresenterLifeCycle
  internal var presenter = TypeInfoPresenter(this)
  private var typeName: String? = null
  private var typeId: String? = null
  private var chooseId: String? = ""
  private var pageNum = 1
  private val adapter = TypeGoodsListAdapter()
  private var listData = ArrayList<GoodsInfoBean>()

  companion object {
    private const val TYPE_ID = "type_id"
    private const val TYPE_NAME = "type_name"
    fun getIntent(context: Context,typeNmae: String,typeId:String): Intent {
      val intent = Intent(context,TypeInfoActivity::class.java)
      intent.putExtra(TYPE_ID,typeId)
      intent.putExtra(TYPE_NAME,typeNmae)
      return intent
    }
  }

  override fun setView(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_type_info_view)
  }

  override fun onResume() {
    super.onResume()
//    presenter.getGoodsList(params)
    presenter.getGoodsListNew(paramsN)
  }

  override fun loadData() {
    typeName = intent.getStringExtra(TYPE_NAME)
    typeId = intent.getStringExtra(TYPE_ID)
//    params.classId = typeId
    paramsN.classId = typeId
//    params.pageNum = pageNum
    setTitle(typeName)
    val spannableString = SpannableString("编辑")
    val colorSpan = ForegroundColorSpan(Color.parseColor("#FF3539"))
    spannableString.setSpan(colorSpan, 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
    setRightMenu(spannableString) {
      if (listData != null && listData.size > 0 && adapter != null) {
        var correntStatus = listData[0].isShowCheck
        if (correntStatus) {
          val spannableString1 = SpannableString("编辑")
          val colorSpan = ForegroundColorSpan(Color.parseColor("#FF3539"))
          spannableString1.setSpan(colorSpan, 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
          setRightMenuText(spannableString1)
          text_add.visibility = View.VISIBLE
          ll_manage.visibility = View.GONE
        }else {
          val spannableString2 = SpannableString("取消")
          val colorSpan = ForegroundColorSpan(Color.parseColor("#FF3539"))
          spannableString2.setSpan(colorSpan, 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
          setRightMenuText(spannableString2)
          text_add.visibility = View.GONE
          ll_manage.visibility = View.VISIBLE
        }
        listData.forEach {
          it.isShowCheck = !correntStatus
          it.isChecked = false
        }
        adapter.setNewData(listData)
      }else {
        showToast("暂无商品")
      }
    }

    text_all.setOnClickListener {
      if (listData != null && listData.size > 0 && adapter != null) {
        listData.forEach {
          it.isChecked = true
        }
        adapter.setNewData(listData)
      }
    }

    text_delete.setOnClickListener {
      chooseId = ""
      listData.forEach {
        if (it.isChecked!!) {
          chooseId = chooseId + "," + it.id
        }
      }
      if (TextUtils.isEmpty(chooseId)) {
        showToast("请选择商品")
      }else {
        presenter.deleteTypeGoods(chooseId!!.substring(1),typeId.toString())
      }

    }

    text_add.setOnClickListener {
      if (typeId != null) {
        launchHelper.startActivity(TypeAddGoodsActivity.getIntent(activity, typeId!!))
      }
    }
  }

  @SuppressLint("SetTextI18n")
  override fun setGoodsInfoList(list: List<GoodsInfoBean>?) {
    listData.clear()
    if (list != null) {
      listData.addAll(list)
    }
    goods_info.setLinearLayoutAdapter(adapter)
    adapter.setNewData(list)
    adapter.setOnItemClickListener { adapter, view, position ->
      if (list?.get(position)?.isChecked!!) {
        list[position].isChecked = false
      }else {
        list[position].isChecked = true
      }
      adapter.setNewData(list)
    }
  }

  override fun deleteGoodsSuc() {
    presenter.getGoodsListNew(paramsN)
    val spannableString1 = SpannableString("编辑")
    val colorSpan = ForegroundColorSpan(Color.parseColor("#FF3539"))
    spannableString1.setSpan(colorSpan, 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
    setRightMenuText(spannableString1)
    text_add.visibility = View.VISIBLE
    ll_manage.visibility = View.GONE
  }

}
