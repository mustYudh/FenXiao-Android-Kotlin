package com.nhbs.fenxiao.module.center.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.module.center.adapter.SelectGoodsTypeAdapter
import com.nhbs.fenxiao.module.center.bean.Row
import com.nhbs.fenxiao.module.center.presenter.SelectGoodsTypePresenter
import com.nhbs.fenxiao.module.center.presenter.SelectGoodsTypeViewer
import com.nhbs.fenxiao.utils.setLinearLayoutAdapter
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.activity_select_goods_type_view.list

class SelectGoodsTypeActivity : BaseBarActivity(), SelectGoodsTypeViewer {



  @PresenterLifeCycle
  internal var presenter = SelectGoodsTypePresenter(this)

  private var adapter = SelectGoodsTypeAdapter()




  companion object {
    const val SELECTED_DATA_REQUEST_DATA = 1
    const val SELECTED_DATA_REQUEST_DATA_1 = 2
    const val SELECTED_DATA_REQUEST_DATA_2 = 3
    private const val FROM_TYPE = "from_type"
    const val SELECTED_DATA = "selected_data"
    fun getIntent(context: Context,type: Int): Intent {
      val intent = Intent(context,SelectGoodsTypeActivity::class.java)
      intent.putExtra(FROM_TYPE,type)
      return intent
    }
  }


  override fun setView(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_select_goods_type_view)
  }

  override fun loadData() {
    showLine(true)
    setTitle("选择类目")
    when(intent.getIntExtra(FROM_TYPE,1)) {
      1 -> presenter.gtGoodsType()
      2 -> presenter.getAdType()
    }

    list.setLinearLayoutAdapter(adapter)
    adapter.setOnItemClickListener { adapter, _, position ->
      val intent = Intent()
      intent.putExtra(SELECTED_DATA,adapter.data[position] as Row)
      setResult(Activity.RESULT_OK,intent)
      finish()
    }
  }


  override fun setType(data: List<Row>?) {
    adapter.setNewData(data)
  }
}
