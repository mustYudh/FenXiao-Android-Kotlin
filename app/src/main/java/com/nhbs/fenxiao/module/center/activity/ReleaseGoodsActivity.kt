package com.nhbs.fenxiao.module.center.activity

import android.os.Bundle
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.module.center.adapter.AddGoodsPhotoAdapter
import com.nhbs.fenxiao.module.center.presenter.ReleaseGoodsPresenter
import com.nhbs.fenxiao.module.center.presenter.ReleaseGoodsViewer
import com.nhbs.fenxiao.module.view.RecycleItemSpance
import com.nhbs.fenxiao.utils.setGridLayoutAdapter
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.include_layout_release_goods_top.list


class ReleaseGoodsActivity : BaseBarActivity(), ReleaseGoodsViewer {
  private val mAdapter = AddGoodsPhotoAdapter()
  @PresenterLifeCycle
  internal var presenter = ReleaseGoodsPresenter(this)

  override fun setView(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_new_release_goods_view)
  }

  override fun loadData() {
    showLine(true)
    setBackIcon(R.drawable.ic_close)
    list.setGridLayoutAdapter(4, mAdapter, true)
    list.addItemDecoration(RecycleItemSpance(8, 8))
    setRightMenu("保存") {}
    mAdapter.addData(0, "1")
    mAdapter.setOnItemChildClickListener { adapter, view, position ->
      when(view.id) {
        R.id.delete_photo -> {
          adapter.remove(position)
        }
        R.id.add_photo -> {

        }
      }
    }
  }


}
