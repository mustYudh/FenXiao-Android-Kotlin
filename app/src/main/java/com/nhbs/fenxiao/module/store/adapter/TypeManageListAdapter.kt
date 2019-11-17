package com.nhbs.fenxiao.module.store.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.module.store.bean.ClassTOS


/**
 * @author yudneghao
 * @date 2019-09-15
 */
class TypeManageListAdapter : BaseQuickAdapter<ClassTOS, BaseViewHolder>(R.layout.item_manage_classification,
    null) {

  override fun convert(helper: BaseViewHolder?, item: ClassTOS?) {
    helper?.setText(R.id.text_name, item?.classify)?.addOnClickListener(R.id.img_change)?.addOnClickListener(R.id.img_delete)
  }


}