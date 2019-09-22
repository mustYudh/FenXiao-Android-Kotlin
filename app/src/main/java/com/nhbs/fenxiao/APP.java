package com.nhbs.fenxiao;

import android.text.TextUtils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.nhbs.fenxiao.data.UserProfile;
import com.nhbs.fenxiao.http.interceptor.CustomDynamicInterceptor;
import com.nhbs.fenxiao.http.interceptor.CustomExpiredInterceptor;
import com.nhbs.fenxiao.http.interceptor.CustomLoggingInterceptor;
import com.nhbs.fenxiao.http.interceptor.CustomResponseInterceptor;
import com.nhbs.fenxiao.im.NimSDKOptionConfig;
import com.nhbs.fenxiao.im.preference.NimAppCache;
import com.nhbs.fenxiao.utils.PickerViewUtils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.XHttpSDK;
import com.xuexiang.xhttp2.model.HttpHeaders;
import com.yu.common.CommonInit;
import com.yu.common.base.BaseApp;
import com.yu.share.ShareAuthSDK;

/**
 * @author yudneghao
 * @date 2019-06-03
 */
public class APP extends BaseApp {
  public static final int NET_TYPE = BuildConfig.API_MODE;
  public static final boolean DEBUG = APP.NET_TYPE == 0;
  private static APP instance;

  static {
    SmartRefreshLayout.setDefaultRefreshInitializer((context, layout) -> {
      layout.setReboundDuration(1000);
      layout.setHeaderHeight(50);
      layout.setDisableContentWhenLoading(true);
      layout.setDisableContentWhenRefresh(true);
    });
    SmartRefreshLayout.setDefaultRefreshHeaderCreator(
        (context, layout) -> new MaterialHeader(context).setColorSchemeResources(R.color.B3BAC7,
            R.color.lb_cm_gray_font, R.color.app_theme));
    SmartRefreshLayout.setDefaultRefreshFooterCreator(
        (context, layout) -> new ClassicsFooter(context));
  }

  @Override public void onCreate() {
    APP.instance = this;
    super.onCreate();
    CommonInit.init(this);
    ShareAuthSDK.init(this, DEBUG);
    PickerViewUtils.INSTANCE.initJsonData(this);
    initHttp();
    initIM();
  }

  private void initIM() {
    NimAppCache.setContext(this);
    NIMClient.init(this, loginInfo(), NimSDKOptionConfig.getSDKOptions(this));
    if (NIMUtil.isMainProcess(this)) {

    }
  }

  private LoginInfo loginInfo() {
    String account = UserProfile.getInstance().getAccount();
    String token = UserProfile.getInstance().getAppToken();

    if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
      NimAppCache.setAccount(account);
      return new LoginInfo(account, token);
    } else {
      return null;
    }
  }

  private void initHttp() {
    XHttpSDK.init(this);
    if (DEBUG) {
      XHttpSDK.debug();
      XHttpSDK.debug(new CustomLoggingInterceptor());
    }
    XHttpSDK.setBaseUrl(getBaseUrl());
    XHttpSDK.setSubUrl(getSubUrl());
    XHttpSDK.addInterceptor(new CustomDynamicInterceptor());
    XHttpSDK.addInterceptor(new CustomExpiredInterceptor());
    XHttpSDK.addInterceptor(new CustomResponseInterceptor());
    XHttp.getInstance().setTimeout(60000);
    XHttp.getInstance().setRetryCount(3);
    XHttp.getInstance().addCommonHeaders(getHttpHeaders());
  }

  private HttpHeaders getHttpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    return headers;
  }

  private String getBaseUrl() {
    if (APP.NET_TYPE == 1) {
      return "http://bapi.novobus.cn";
    } else if (APP.NET_TYPE == 2) {
      return "http://bapi.novobus.cn";
    } else {
      return "http://bapi.novobus.cn";
    }
  }

  public String getSubUrl() {
    return "/api";
  }

  public synchronized static APP getInstance() {
    return instance;
  }
}
