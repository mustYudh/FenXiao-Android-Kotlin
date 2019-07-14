package com.nhbs.fenxiao.module.center.adapter

import android.text.TextUtils
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.nhbs.fenxiao.R
import com.yu.common.glide.ImageLoader

/**
 * @author yudenghao
 * @date 2019-07-14
 */


class AddGoodsPhotoAdapter : BaseQuickAdapter<String, BaseViewHolder>(
    R.layout.item_add_goods_photo_layout, null) {

  override fun convert(helper: BaseViewHolder?, item: String?) {
    val imageView = helper
        ?.addOnClickListener(R.id.add_photo)
        ?.addOnClickListener(R.id.delete_photo)
        ?.setVisible(R.id.tag, itemCount >= 2 && helper.layoutPosition == 0)
        ?.setVisible(R.id.delete_photo, itemCount <= 6 && helper.layoutPosition != itemCount - 1)
        ?.setVisible(R.id.add_photo,  TextUtils.isEmpty("") && itemCount < 6)
        ?.setVisible(R.id.show_photo, itemCount <= 6 && helper.layoutPosition != itemCount - 1)
        ?.getView<ImageView>(R.id.show_photo)
    if (!TextUtils.isEmpty(item)) {
      ImageLoader.getInstance().displayImage(imageView, item)
    }

  }

}