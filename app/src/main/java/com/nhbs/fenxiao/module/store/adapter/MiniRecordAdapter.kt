package com.nhbs.fenxiao.module.store.adapter

import android.annotation.SuppressLint
import android.text.TextUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.module.store.bean.MiniStoreActivityBean
import com.shehuan.niv.NiceImageView
import com.yu.common.glide.ImageLoader
import com.yu.common.ui.Res
import java.text.SimpleDateFormat

/**
 * @author yudenghao
 * @date 2019-08-28
 */
class MiniRecordAdapter : BaseQuickAdapter<MiniStoreActivityBean, BaseViewHolder>(
    (R.layout.item_mini_store_acitivty_info_layout), null) {
  @SuppressLint("SimpleDateFormat")
  override fun convert(helper: BaseViewHolder?, item: MiniStoreActivityBean?) {
    val format = SimpleDateFormat("MM月dd日HH mm:ss自动开奖")
    val createTimeFormat = SimpleDateFormat("MM月dd日HH mm:ss：ms提交")
    val firstImg = helper?.getView<NiceImageView>(R.id.first_prize_img)
    val secondImg = helper?.getView<NiceImageView>(R.id.access_it_img)
    val lastImg = helper?.getView<NiceImageView>(R.id.third_prize_img)
    helper?.setVisible(R.id.first_root, !TextUtils.isEmpty(item?.firstPrizeImgs))
        ?.setText(R.id.first_prize_name, item?.firstPrizeName)
        ?.setVisible(R.id.access_it_root, !TextUtils.isEmpty(item?.accessitImgs))
        ?.setText(R.id.access_it_name, item?.accessitName)
        ?.setVisible(R.id.last_root, !TextUtils.isEmpty(item?.thirdPrizeImgs))
        ?.setText(R.id.third_prize_name, item?.thirdPrizeName)
        ?.setText(R.id.name, item?.aName)
        ?.setText(R.id.content, item?.content)
        ?.setText(R.id.draw_time, format.format(item?.drawTime))
        ?.setText(R.id.address, "${item?.province}${item?.city}${item?.district}")
        ?.setBackgroundColor(R.id.info, Res.color(R.color.white))
        ?.setVisible(R.id.create_time,true)
        ?.setText(R.id.create_time,createTimeFormat.format(item?.createTime))
    ImageLoader.getInstance().displayImage(firstImg, item?.firstPrizeImgs)
    ImageLoader.getInstance().displayImage(secondImg, item?.accessitImgs)
    ImageLoader.getInstance().displayImage(lastImg, item?.thirdPrizeImgs)
  }
}