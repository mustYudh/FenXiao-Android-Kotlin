package com.nhbs.fenxiao.module.store.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.module.store.bean.ClassTOS


/**
 * @author yudneghao
 * @date 2019-09-15
 */
class TypeCountListAdapter : BaseQuickAdapter<ClassTOS, BaseViewHolder>(R.layout.item_type_layout,
    null) {

  override fun convert(helper: BaseViewHolder?, item: ClassTOS?) {
    helper?.setText(R.id.type_name, item?.classify)?.setText(R.id.type_count, "${item?.total}件商品")
  }

}