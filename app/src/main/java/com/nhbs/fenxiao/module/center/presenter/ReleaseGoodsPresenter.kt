package com.nhbs.fenxiao.module.center.presenter

import android.annotation.SuppressLint
import android.text.TextUtils
import com.nhbs.fenxiao.http.api.AppApiServices
import com.nhbs.fenxiao.http.subscriber.LoadingRequestSubscriber
import com.nhbs.fenxiao.module.center.bean.ReleaseGoodsParams
import com.nhbs.fenxiao.utils.checkTextEmpty
import com.nhbs.fenxiao.utils.oss.UploadUtils
import com.nhbs.fenxiao.utils.showToast
import com.xuexiang.xhttp2.XHttp
import com.xuexiang.xhttp2.model.ApiResult
import com.xuexiang.xhttp2.utils.HttpUtils
import com.yu.common.framework.BaseViewPresenter
import com.yu.common.utils.RxSchedulerUtils


@SuppressLint("CheckResult")
class ReleaseGoodsPresenter(viewer: ReleaseGoodsViewer) : BaseViewPresenter<ReleaseGoodsViewer>(
    viewer) {


  fun addNewPhoto(url: ArrayList<String>) {
    getViewer()?.setReleaseGoodsImage(url)

  }


  fun releaseGoods(params: ReleaseGoodsParams, url: ArrayList<String>) {
    if (params.checkEmpty()) {
      UploadUtils.uploadFile(activity, url, "FaBuShangPing", "png") { fileList ->
        params.mImgs = ""
        fileList.forEachIndexed { index, result ->
          params.mImgs += "$result${if (index < fileList.size - 1) "," else ""}"
        }
        XHttp.custom(AppApiServices::class.java)
            .releaseGoods(HttpUtils.getJsonRequestBody(params))
            .compose(RxSchedulerUtils._io_main_o<ApiResult<Any>>())
            .subscribeWith(object : LoadingRequestSubscriber<ApiResult<Any>>(activity, false) {
              override fun onSuccess(t: ApiResult<Any>?) {
                if (t?.code == 2000) {
                  showToast("发布成功")
                  activity?.finish()
                } else if (!t?.msg.checkTextEmpty()) {
                  showToast(t?.msg!!)
                }
              }

            })
      }
    }
  }

  fun editGoodsInfo(params: ReleaseGoodsParams, url: ArrayList<String>) {
    val paramsImages = ArrayList<String>()
    val uploadImages = ArrayList<String>()
    if (params.checkEmpty()) {
      url.forEach {
        if (it.startsWith("http")) {
          paramsImages.add(it)
        } else {
          if (!TextUtils.isEmpty(it)) {
            uploadImages.add(it)
          }

        }
      }
      if (uploadImages.size > 0) {
        UploadUtils.uploadFile(activity, uploadImages, "FaBuShangPing-Edit", "png") { fileList ->
          fileList.addAll(paramsImages)
          params.mImgs = ""
          paramsImages.forEachIndexed { index, result ->
            params.mImgs += "$result${if (index < paramsImages.size - 1) "," else ""}"
          }
          edit(params)
        }
      } else {
        params.mImgs = ""
        paramsImages.forEachIndexed { index, result ->
          params.mImgs += "$result${if (index < paramsImages.size - 1) "," else ""}"
        }
        edit(params)
      }
    }
  }

  private fun edit(params: ReleaseGoodsParams) {
    XHttp.custom(AppApiServices::class.java)
        .editGoods(HttpUtils.getJsonRequestBody(params))
        .compose(RxSchedulerUtils._io_main_o<ApiResult<Any>>())
        .subscribeWith(object : LoadingRequestSubscriber<ApiResult<Any>>(activity, false) {
          override fun onSuccess(t: ApiResult<Any>?) {
            if (t?.code == 2000) {
              showToast("修改成功")
              activity?.finish()
            } else if (!t?.msg.checkTextEmpty()) {
              showToast(t?.msg!!)
            }
          }

        })
  }
}