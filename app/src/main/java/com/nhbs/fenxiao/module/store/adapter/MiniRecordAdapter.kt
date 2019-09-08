package com.nhbs.fenxiao.module.store.adapter

import android.annotation.SuppressLint
import android.text.TextUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.nhbs.fenxiao.R
import com.nhbs.fenxiao.module.store.bean.MiniStoreActivityBean
import com.shehuan.niv.NiceImageView
import com.yu.common.glide.ImageLoader
import com.yu.common.ui.CircleImageView
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
    val header = helper?.getView<CircleImageView>(R.id.header)
    helper?.setVisible(R.id.first_root, !TextUtils.isEmpty(item?.images))
        ?.setText(R.id.first_prize_name, item?.firstPrizeName)
        ?.setVisible(R.id.access_it_root, !TextUtils.isEmpty(item?.accessitImgs))
        ?.setText(R.id.access_it_name, item?.accessitName)
        ?.setVisible(R.id.last_root, !TextUtils.isEmpty(item?.thirdPrizeImgs))
        ?.setText(R.id.third_prize_name, item?.thirdPrizeName)
        ?.setText(R.id.name, if (item?.type == 1) item?.name else item?.aName)
        ?.setText(R.id.content, item?.content)
        ?.setText(R.id.draw_time, format.format(item?.drawTime))
        ?.setGone(R.id.address, !TextUtils.isEmpty(item?.province))
        ?.setText(R.id.address, "${item?.province}${item?.city}${item?.district}")
        ?.setBackgroundColor(R.id.info, Res.color(R.color.white))?.setText(R.id.info,
            getStatus(item?.type, item?.status))
        ?.setText(R.id.create_time, createTimeFormat.format(item?.createTime))
    val images = item?.images?.split(",")
    if (item?.type == 1) {
      helper?.setVisible(R.id.create_time, true)
      if (images?.size!! > 0) {
        ImageLoader.getInstance().displayImage(firstImg, images[0])
      }
      ImageLoader.getInstance().displayImage(secondImg, item.accessitImgs)
      ImageLoader.getInstance().displayImage(lastImg, item.thirdPrizeImgs)
    } else if (item?.type == 2) {
      helper?.setVisible(R.id.draw_time, false)?.setGone(R.id.tag1, false)?.setGone(R.id.tag2,
          false)?.setGone(R.id.tag3, false)?.setVisible(R.id.first_root, true)?.setVisible(
          R.id.access_it_root, true)?.setVisible(R.id.last_root, true)
      val imagesSize = images?.size!!
      if (imagesSize >= 1) {
        ImageLoader.getInstance().displayImage(firstImg, images[0])
      } else if (imagesSize >= 2) {
        ImageLoader.getInstance().displayImage(secondImg, images[1])
      } else if (imagesSize >= 3) {
        ImageLoader.getInstance().displayImage(lastImg, images[2])
      }
    }

    ImageLoader.getInstance().displayImage(header, item?.shopImage)

  }

  private fun getStatus(type: Int?, status: Int?): String {
    if (type == 1) {
      when (status) {
        0 -> {
          return "未开奖"
        }
        1 -> {
          return "已开奖"
        }
        2 -> {
          return "待审核"
        }
        3 -> {
          return "审核不通过"
        }
        4 -> {
          return "审核通过"
        }
        else -> {
          return ""
        }
      }
    } else {
      when (status) {
        0 -> {
          return "下架"
        }
        1 -> {
          return "正常销售"
        }
        2 -> {
          return "待审核"
        }
        3 -> {
          return "驳回"
        }
        else -> {
          return ""
        }
      }
    }
  }

}