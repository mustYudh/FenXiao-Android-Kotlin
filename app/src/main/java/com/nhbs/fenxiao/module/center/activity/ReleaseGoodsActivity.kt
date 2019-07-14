package com.nhbs.fenxiao.module.center.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.module.center.adapter.AddGoodsPhotoAdapter
import com.nhbs.fenxiao.module.center.presenter.ReleaseGoodsPresenter
import com.nhbs.fenxiao.module.center.presenter.ReleaseGoodsViewer
import com.nhbs.fenxiao.module.view.RecycleItemSpace
import com.nhbs.fenxiao.utils.selectPhoto
import com.nhbs.fenxiao.utils.setGridLayoutAdapter
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.include_layout_release_goods_top.list


class ReleaseGoodsActivity : BaseBarActivity(), ReleaseGoodsViewer {


  @PresenterLifeCycle
  internal var mPresenter = ReleaseGoodsPresenter(this)
  private val mAdapter = AddGoodsPhotoAdapter()

  override fun setView(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_new_release_goods_view)
  }

  override fun loadData() {
    showLine(true)
    setTitle("发布")
    setBackIcon(R.drawable.ic_close)
    list.addItemDecoration(RecycleItemSpace(8, 0))
    list.setGridLayoutAdapter(4, mAdapter, true)
    setRightMenu("保存") {}
    mAdapter.addData(0, "")
    mAdapter.setOnItemChildClickListener { adapter, view, position ->
      when (view.id) {
        R.id.delete_photo -> {
          adapter.remove(position)
          mAdapter.notifyDataSetChanged()
        }
        R.id.add_photo -> {
          if (position == adapter.itemCount - 1 && TextUtils.isEmpty(
                  adapter.data[position] as String) && adapter.itemCount < 6) {
            activity.selectPhoto(6 - adapter.itemCount)
          }
        }
      }
    }
  }


  override fun setReleaseGoodsImage(url: List<String>) {
    mAdapter.addData(mAdapter.itemCount - 1, url)
  }


  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == RESULT_OK) {
      if (requestCode == PictureConfig.CHOOSE_REQUEST) {
        val selectList = PictureSelector.obtainMultipleResult(data)
        if (selectList != null && selectList.size > 0) {
          val list = ArrayList<String>()
          for (url in selectList) {
            list.add(url.compressPath)
          }
          mPresenter.addNewPhoto(list)
        }
      }

    }
  }


}
