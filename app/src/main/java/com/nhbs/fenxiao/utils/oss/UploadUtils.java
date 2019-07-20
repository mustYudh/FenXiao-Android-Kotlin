package com.nhbs.fenxiao.utils.oss;

import android.annotation.SuppressLint;
import android.app.Activity;
import com.nhbs.fenxiao.http.api.AppApiServices;
import com.nhbs.fenxiao.http.subscriber.NoTipRequestSubscriber;
import com.nhbs.fenxiao.utils.oss.bean.OssConfig;
import com.xuexiang.xhttp2.XHttpProxy;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;

/**
 * @author yudenghao
 * @date 2019-07-15
 */
@SuppressLint("CheckResult")

public class UploadUtils {
  public static void uploadFile(Activity context, ArrayList<String> fileUrl, String fileName,
      String fileType, GetUploadResult callBack) {

    XHttpProxy.proxy(AppApiServices.class)
        .getOssConfig()
        .subscribeWith(new NoTipRequestSubscriber<OssConfig>() {
          @Override protected void onSuccess(OssConfig config) {
            final int[] position = { 0 };
            Observable.fromIterable(fileUrl)
                .flatMap(new Function<String, ObservableSource<PersistenceResponse>>() {
                  @Override public ObservableSource<PersistenceResponse> apply(String url)
                      throws Exception {
                    return Observable.just(url)
                    .subscribeOn(Schedulers.io())
                        .map(new Function<String, PersistenceResponse>() {
                      @Override public PersistenceResponse apply(String selectFileUrl)
                          throws Exception {
                        return UploadImage.uploadFile(context,
                            fileName + position[0] + "." + fileType, selectFileUrl, config);
                      }
                    });
                  }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PersistenceResponse>() {
                  @Override public void onSubscribe(Disposable d) {

                  }

                  @Override public void onNext(PersistenceResponse response) {

                  }

                  @Override public void onError(Throwable e) {

                  }

                  @Override public void onComplete() {

                  }
                });
          }
        });
  }

  public interface GetUploadResult {
    void result(ArrayList<String> fileList);
  }
}
