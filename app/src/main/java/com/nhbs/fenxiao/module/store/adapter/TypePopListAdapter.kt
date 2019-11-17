package com.nhbs.fenxiao.module.store.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.module.store.bean.ClassTOS


/**
 * @author yudneghao
 * @date 2019-09-15
 */
class TypePopListAdapter : BaseQuickAdapter<ClassTOS, BaseViewHolder>(R.layout.item_choose_goods_classification,
    null) {

  override fun convert(helper: BaseViewHolder?, item: ClassTOS?) {
    helper?.setText(R.id.text_name, item?.classify)?.setText(R.id.text_count, "${item?.total}件商品")
    helper?.getView<ImageView>(R.id.img_choose)?.isSelected = item?.isChecked!!
  }

}