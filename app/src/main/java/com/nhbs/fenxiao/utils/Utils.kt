package com.nhbs.fenxiao.utils

import android.app.Activity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseViewHolder
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType

/**
 * @author yudenghao
 * @date 2019-07-13
 */


fun RecyclerView.setLinearLayoutAdapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
    noScroller: Boolean?) {
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


