package com.nhbs.fenxiao.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.EditText
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.chad.library.adapter.base.BaseViewHolder
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.nhbs.fenxiao.R
import com.yu.common.toast.ToastUtils
import com.yu.common.ui.Res
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

/**
 * @author yudenghao
 * @date 2019-07-13
 */


fun RecyclerView.setLinearLayoutAdapter(adapter: RecyclerView.Adapter<BaseViewHolder>, noScroller: Boolean? = false) {
  layoutManager = object : LinearLayoutManager(context) {
    override fun canScrollVertically(): Boolean {
      return noScroller ?: super.canScrollVertically()
    }
  }

  setAdapter(adapter)
}


fun RecyclerView.setGridLayoutAdapter(count: Int,
    adapter: RecyclerView.Adapter<BaseViewHolder>, noScroller: Boolean?) {
  layoutManager = object : GridLayoutManager(context, count, LinearLayoutManager.VERTICAL, false) {
    override fun canScrollVertically(): Boolean {
      return noScroller ?: super.canScrollVertically()
    }
  }
  setAdapter(adapter)
}



fun RecyclerView.setGridLayoutAdapter(manager: GridLayoutManager,
    adapter: RecyclerView.Adapter<BaseViewHolder>) {
  layoutManager = manager
  setAdapter(adapter)
}

fun Activity.selectPhoto(max :Int? = 1) {
  PictureSelector.create(this)
      .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
      .maxSelectNum(max!!)// 最大图片选择数量 int
      .imageSpanCount(4)// 每行显示个数 int
      .selectionMode(if (max > 0)  PictureConfig.MULTIPLE else PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
      .previewImage(true)// 是否可预览图片 true or false
      .previewVideo(true)// 是否可预览视频 true or false
      .enablePreviewAudio(true) // 是否可播放音频 true or false
      .isCamera(true)// 是否显示拍照按钮 true or false
      .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
      .isZoomAnim(false)// 图片列表点击 缩放效果 默认true
      .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
      .enableCrop(false)// 是否裁剪 true or false
      .compress(true)// 是否压缩 true or false
      .isGif(false)// 是否显示gif图片 true or false
      .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
      .minimumCompressSize(100)// 小于100kb的图片不压缩
      .synOrAsy(true)//同步true或异步false 压缩 默认同步
      .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
      .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
      .isDragFrame(false)// 是否可拖动裁剪框(固定)
      .forResult(PictureConfig.CHOOSE_REQUEST)//结果回调onActivityResult code
}



fun getCalendarPicker(context: Context, getTime: (time: Date) -> Unit) {
  val selectedDate = Calendar.getInstance()//系统当前时间
  val startDate = Calendar.getInstance()
  startDate.set(2000, 1, 23)
  val endDate = Calendar.getInstance()
  endDate.set(2069, 2, 28)
  val pvCustomLunar = TimePickerBuilder(context, OnTimeSelectListener { date, _ ->
    getTime(date)
  })
      .setDate(selectedDate)
      .setRangDate(startDate, endDate)
      .setDividerColor(Res.color(R.color.line_color))
      .setTitleColor(Res.color(R.color.material_blue_grey_800))
      .setSubmitColor(Res.color(R.color.material_blue_grey_800))
      .setCancelColor(Res.color(R.color.material_blue_grey_800))
      .setTitleBgColor(Res.color(R.color.white))
      .setBgColor(Res.color(R.color.white))
      .setTitleSize(14)
      .setSubCalSize(14)
      .setContentTextSize(14)
      .setType(booleanArrayOf(true, true, true, false, false, false))
      .setTitleText("请选择")
      .build()
  pvCustomLunar.show()
}




@SuppressLint("SimpleDateFormat")
fun getTime(date: Date, fmort: String): String {
  val format = SimpleDateFormat(fmort)
  return format.format(date)
}



fun EditText.getInputText(): String {
  return text.toString().trim()
}


fun showToast(text: String) {
  ToastUtils.show(text)
}


fun showToast(context: Context,text: String) {
  ToastUtils.show(context,text)
}




