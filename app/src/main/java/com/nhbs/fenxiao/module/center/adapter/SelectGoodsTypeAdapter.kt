package com.nhbs.fenxiao.module.center.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.module.center.bean.Row

/**
 * @author yudenghao
 * @date 2019-07-14
 */
class SelectGoodsTypeAdapter : BaseQuickAdapter<Row, BaseViewHolder>(
    R.layout.item_goods_type_layout, null) {

  override fun convert(helper: BaseViewHolder?, item: Row?) {
    helper?.setText(R.id.type_text, item?.classify)
  }

}