package com.nhbs.fenxiao;

import android.text.TextUtils;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.api.UIKitOptions;
import com.netease.nim.uikit.business.contact.core.query.PinYin;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.nhbs.fenxiao.data.UserProfile;
import com.nhbs.fenxiao.http.interceptor.CustomDynamicInterceptor;
import com.nhbs.fenxiao.http.interceptor.CustomExpiredInterceptor;
import com.nhbs.fenxiao.http.interceptor.CustomLoggingInterceptor;
import com.nhbs.fenxiao.http.interceptor.CustomResponseInterceptor;
import com.nhbs.fenxiao.im.NimSDKOptionConfig;
import com.nhbs.fenxiao.im.custom.ChatRoomSessionHelper;
import com.nhbs.fenxiao.im.custom.SessionHelper;
import com.nhbs.fenxiao.im.init.NIMInitManager;
import com.nhbs.fenxiao.im.init.APPOnlineStateContentProvider;
import com.nhbs.fenxiao.im.preference.NimAppCache;
import com.nhbs.fenxiao.im.preference.UserPreferences;
import com.nhbs.fenxiao.im.push.PushContentProvider;
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
      PinYin.init(this);
      PinYin.validate();
      initUIKit();
      NIMClient.toggleNotification(UserPreferences.getNotificationToggle());
      // 云信sdk相关业务初始化
      NIMInitManager.getInstance().init(true);
    }
  }

  private void initUIKit() {
    // 初始化
    NimUIKit.init(this, buildUIKitOptions());

    // IM 会话窗口的定制初始化。
    SessionHelper.init();

    // 聊天室聊天窗口的定制初始化。
    ChatRoomSessionHelper.init();



    // 添加自定义推送文案以及选项，请开发者在各端（Android、IOS、PC、Web）消息发送时保持一致，以免出现通知不一致的情况
    NimUIKit.setCustomPushContentProvider(new PushContentProvider());

    NimUIKit.setOnlineStateContentProvider(new APPOnlineStateContentProvider());


  }

  private UIKitOptions buildUIKitOptions() {
    UIKitOptions options = new UIKitOptions();
    // 设置app图片/音频/日志等缓存目录
    options.appCacheDir = NimSDKOptionConfig.getAppCacheDir(this) + "/app";
    return options;
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
