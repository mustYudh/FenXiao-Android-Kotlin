package com.nhbs.fenxiao.module.center.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.R.drawable
import com.nhbs.fenxiao.base.BaseBarActivity
import com.nhbs.fenxiao.module.center.adapter.AddGoodsPhotoAdapter
import com.nhbs.fenxiao.module.center.bean.ReleaseGoodsParams
import com.nhbs.fenxiao.module.center.bean.Row
import com.nhbs.fenxiao.module.center.presenter.ReleaseGoodsPresenter
import com.nhbs.fenxiao.module.center.presenter.ReleaseGoodsViewer
import com.nhbs.fenxiao.module.store.bean.GoodsListBean.GoodsInfoBean
import com.nhbs.fenxiao.module.view.RecycleItemSpace
import com.nhbs.fenxiao.utils.getInputText
import com.nhbs.fenxiao.utils.getMoney
import com.nhbs.fenxiao.utils.selectPhoto
import com.nhbs.fenxiao.utils.setGridLayoutAdapter
import com.nhbs.fenxiao.utils.setfilters
import com.yu.common.mvp.PresenterLifeCycle
import kotlinx.android.synthetic.main.activity_new_release_goods_view.check_free_mail
import kotlinx.android.synthetic.main.activity_new_release_goods_view.check_free_mail_btn
import kotlinx.android.synthetic.main.activity_new_release_goods_view.commission
import kotlinx.android.synthetic.main.activity_new_release_goods_view.door_to_door_delivery
import kotlinx.android.synthetic.main.activity_new_release_goods_view.free_mail
import kotlinx.android.synthetic.main.activity_new_release_goods_view.goods_tag_1
import kotlinx.android.synthetic.main.activity_new_release_goods_view.goods_tag_2
import kotlinx.android.synthetic.main.activity_new_release_goods_view.mail
import kotlinx.android.synthetic.main.activity_new_release_goods_view.price
import kotlinx.android.synthetic.main.activity_new_release_goods_view.release
import kotlinx.android.synthetic.main.activity_new_release_goods_view.select_goods_type
import kotlinx.android.synthetic.main.activity_new_release_goods_view.since_the_lift
import kotlinx.android.synthetic.main.activity_new_release_goods_view.type_name
import kotlinx.android.synthetic.main.include_layout_release_goods_top.goods_info
import kotlinx.android.synthetic.main.include_layout_release_goods_top.goods_name
import kotlinx.android.synthetic.main.include_layout_release_goods_top.list


class ReleaseGoodsActivity : BaseBarActivity(), ReleaseGoodsViewer {


  @PresenterLifeCycle
  internal var mPresenter = ReleaseGoodsPresenter(this)
  private val mAdapter = AddGoodsPhotoAdapter()
  private var freeMail = false
  private var dealWay = -1
  private var goodsTypeId = ""
  private var editGoods = false


  companion object {
    private const val GOODS_INFO = "goods_info"
    fun getIntent(context: Context, goods: GoodsInfoBean): Intent {
      val intent = Intent(context, ReleaseGoodsActivity::class.java)
      intent.putExtra(GOODS_INFO, goods)
      return intent
    }
  }

  override fun setView(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_new_release_goods_view)
  }

  override fun loadData() {
    initView()
    initListener()
    val goodsInfo = intent.getSerializableExtra(GOODS_INFO)
    if (goodsInfo != null) {
      getGoodsInfo(goodsInfo as GoodsInfoBean)
    }

  }

  private fun initView() {
    showLine(true)
    setTitle("发布")
    setBackIcon(drawable.ic_close)
    price.setfilters()
    commission.setfilters()
    free_mail.setfilters()
    list.addItemDecoration(RecycleItemSpace(8, 0))
    list.setGridLayoutAdapter(4, mAdapter, true)
    mAdapter.addData("")
  }

  private fun initListener() {
//    setRightMenu("保存") {}
    mAdapter.setOnItemChildClickListener { adapter, view, position ->
      when (view.id) {
        R.id.delete_photo -> {
          adapter.remove(position)
        }
        R.id.add_photo -> {
          if (position == adapter.itemCount - 1 && TextUtils.isEmpty(
                  adapter.data[position] as String) && adapter.itemCount < 6) {
            activity.selectPhoto(6 - adapter.itemCount)
          }
        }
      }
    }

    check_free_mail_btn.setOnClickListener {
      freeMail = !freeMail
      check_free_mail.isSelected = freeMail
      if (freeMail) {
        free_mail.setText("")
      }
    }
    since_the_lift.setOnClickListener {
      dealWay = 1
      since_the_lift.isSelected = true
      door_to_door_delivery.isSelected = false
      mail.isSelected = false
    }
    door_to_door_delivery.setOnClickListener {
      dealWay = 2
      since_the_lift.isSelected = false
      door_to_door_delivery.isSelected = true
      mail.isSelected = false
    }
    mail.setOnClickListener {
      dealWay = 3
      since_the_lift.isSelected = false
      door_to_door_delivery.isSelected = false
      mail.isSelected = true
    }
    select_goods_type.setOnClickListener {
      launchHelper.startActivityForResult(SelectGoodsTypeActivity.getIntent(activity, 1),
          SelectGoodsTypeActivity.SELECTED_DATA_REQUEST_DATA)
    }

    release.setOnClickListener {
      val params = ReleaseGoodsParams()
      var mImages = ""
      mAdapter.data.forEachIndexed { index, result ->
        mImages += "$result${if (index < mAdapter.data.size - 2) "," else ""}"
      }
      params.mImgs = mImages
      params.mContent = goods_info.getInputText()
      params.mPrice = price.getInputText()
      params.commission = commission.getInputText()
      params.postage = free_mail.getInputText()
      params.classId = goodsTypeId
      params.dealWay = dealWay.toString()
      params.tagOne = goods_tag_1.getInputText()
      params.tagTwo = goods_tag_2.getInputText()
      params.mName = goods_name.getInputText()
      mPresenter.releaseGoods(params, mAdapter.data as ArrayList<String>)
    }

    free_mail.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {
        if (freeMail && !TextUtils.isEmpty(s.toString().trim())) {
          freeMail = false
          check_free_mail.isSelected = freeMail
        }
      }

      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

      }

    })

  }

  override fun getGoodsInfo(info: GoodsInfoBean?) {
    editGoods = true
    goods_name.setText(info?.mTitle)
    goods_info.setText(info?.mContent)
    price.setText(info?.mPrice?.getMoney())
    commission.setText(info?.commission?.getMoney())
    free_mail.setText(info?.postage?.getMoney())
    check_free_mail.isSelected = TextUtils.isEmpty(info?.postage?.getMoney())
    type_name.text = info?.typeName
    since_the_lift.isSelected = info?.dealWay == 1
    door_to_door_delivery.isSelected = info?.dealWay == 2
    mail.isSelected = info?.dealWay == 3
    goods_tag_1.setText(info?.tagOne)
    goods_tag_2.setText(info?.tagTwo)

  }


  override fun setReleaseGoodsImage(url: ArrayList<String>) {
    url.addAll(mAdapter.data)
    mAdapter.setNewData(url)
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
      } else if (requestCode == SelectGoodsTypeActivity.SELECTED_DATA_REQUEST_DATA) {
        val result = data?.getSerializableExtra(SelectGoodsTypeActivity.SELECTED_DATA) as Row
        type_name.text = result.classify
        goodsTypeId = result.id
      }
    }
  }


}
