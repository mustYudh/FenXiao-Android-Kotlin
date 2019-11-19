package com.nhbs.fenxiao.module.store.activity.type

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.module.store.activity.presenter.TypeAddGoodsPresenter
import com.nhbs.fenxiao.module.store.activity.presenter.TypeAddGoodsViewer
import com.nhbs.fenxiao.module.store.adapter.OtherTypeGoodsListAdapter
import com.nhbs.fenxiao.module.store.bean.GetOtherGoodsParams
import com.nhbs.fenxiao.module.store.bean.OtherTypeGoodsBean
import com.nhbs.fenxiao.utils.setLinearLayoutAdapter
import com.nhbs.fenxiao.utils.showToast
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.activity_add_goods_classification.*

class TypeAddGoodsActivity : BaseBarActivity(), TypeAddGoodsViewer {

  private var params = GetOtherGoodsParams()
  @PresenterLifeCycle
  internal var presenter = TypeAddGoodsPresenter(this)
  private var typeId : String? = null
  private val adapter = OtherTypeGoodsListAdapter()
  private var listData = ArrayList<OtherTypeGoodsBean.GoodsInfoBean>()
  private var chooseId : String? = ""

  companion object {
    private const val TYPE_ID = "type_id"
    fun getIntent(context: Context, typeId:String): Intent {
      val intent = Intent(context, TypeAddGoodsActivity::class.java)
      intent.putExtra(TYPE_ID,typeId)
      return intent
    }
  }

  override fun setView(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_add_goods_classification)
  }

  override fun loadData() {
    setTitle("添加商品")
    adapter.setEmptyView(R.layout.layout_empty)
    typeId = intent.getStringExtra(TYPE_ID)
    params.classId = typeId
    initListener()
    presenter.getOtherGoodsList(params)
  }

  private fun initListener() {
    text_confirm.setOnClickListener {
      chooseId = ""
      listData.forEach {
        if (it.isChecked!!) {
          chooseId = chooseId + "," + it.id
        }
      }
      if (TextUtils.isEmpty(chooseId)) {
        showToast("请选择商品")
      }else {
        presenter.addGoods(chooseId!!.substring(1), typeId.toString())
      }
    }
  }

  override fun onResume() {
    super.onResume()
  }

  override fun setOtherGoodsInfoList(list: List<OtherTypeGoodsBean.GoodsInfoBean>?) {
    listData.clear()
    if (list != null) {
      listData.addAll(list)
    }
    goods_list.setLinearLayoutAdapter(adapter)
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
  override fun addGoodsSuc() {
    finish()
  }

}
