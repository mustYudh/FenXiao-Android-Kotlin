package com.nhbs.fenxiao.module.store.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.module.store.bean.GoodsListBean.GoodsInfoBean
import com.nhbs.fenxiao.utils.getMoney
import com.nhbs.fenxiao.utils.getTime
import com.yu.common.glide.ImageLoader

/**
 * @author yudneghao
 * @date 2019-06-30
 */
class MiniStoreGoodsInfoAdapter :
    BaseQuickAdapter<GoodsInfoBean, BaseViewHolder>((R.layout.item_mini_store_goods_info_layout), null) {
  override fun convert(helper: BaseViewHolder?, item: GoodsInfoBean?) {

    ImageLoader.getInstance().displayImage(helper?.getView(R.id.goods_image),
        if (item?.mImgs?.contains(",")!!) item.mImgs.split(",")[0] else item.mImgs)
    helper?.setText(R.id.title, item.mTitle)?.setText(R.id.price, "¥${item.mPrice?.getMoney()}")
        ?.setText(R.id.goods_number, "总销量:${item.number}")
        ?.setText(R.id.commit_time, item.createTime.getTime())?.addOnClickListener(
            R.id.shelves_goods)
        ?.addOnClickListener(R.id.edit_goods)
  }

}