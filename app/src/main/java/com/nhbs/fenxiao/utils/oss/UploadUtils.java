package com.nhbs.fenxiao.utils.oss;

import android.annotation.SuppressLint;
import android.app.Activity;
import com.nhbs.fenxiao.http.loading.NetLoadingDialog;
import com.nhbs.fenxiao.utils.oss.bean.OssConfig;
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
  public static void uploadFile(Activity context, ArrayList<String> fileUrl, String fileName, String fileType, GetUploadResult callBack) {

    //XHttpProxy.proxy(AppApiServices.class).getOssConfig()
    //    .subscribeWith(new NoTipRequestSubscriber<OssConfig>() {
    //      @Override protected void onSuccess(OssConfig config) {
            final int[] position = { 0 };
            ArrayList<String> fileList = new ArrayList<>();
            Observable.fromIterable(fileUrl)
                .flatMap(
                    (Function<String, ObservableSource<PersistenceResponse>>) url ->
                        Observable.just(url)
                            .map(selectFileUrl -> UploadImage.uploadFile(context, fileName + position[0] + "." + fileType, selectFileUrl,new OssConfig())))
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PersistenceResponse>() {
                  @Override public void onSubscribe(Disposable d) {
                    NetLoadingDialog.showLoading(context, false);
                  }

                  @Override public void onNext(PersistenceResponse response) {
                    position[0]++;
                    if (response != null && response.success) {
                      fileList.add(response.cloudUrl);
                    }
                  }

                  @Override public void onError(Throwable e) {
                    NetLoadingDialog.dismissLoading();
                  }

                  @Override public void onComplete() {
                    NetLoadingDialog.dismissLoading();
                    if (callBack != null) {
                      callBack.result(fileList);
                    }
                  }

                  @Override protected void finalize() throws Throwable {
                    super.finalize();
                    NetLoadingDialog.dismissLoading();
                  }
                });
          //}
        //});
  }

  public interface GetUploadResult {
    void result(ArrayList<String> fileList);
  }
}
