package com.nhbs.fenxiao.module.store.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.module.store.bean.GoodsListBean
import com.yu.common.glide.ImageLoader


/**
 * @author yudneghao
 * @date 2019-09-15
 */
class NoTypeGoodsListAdapter : BaseQuickAdapter<GoodsListBean.GoodsInfoBean, BaseViewHolder>(R.layout.item_classification_goods,
    null) {

  override fun convert(helper: BaseViewHolder?, item: GoodsListBean.GoodsInfoBean?) {
    helper?.setText(R.id.text_name, item?.mName)?.setText(R.id.text_price, "¥${item?.mPrice}")
    ImageLoader.getInstance().displayImage(helper?.getView(R.id.img_logo), item?.mImgs)
    helper?.setGone(R.id.img_choose, true)
    helper?.getView<ImageView>(R.id.img_choose)?.isSelected = item?.isChecked!!
  }

}