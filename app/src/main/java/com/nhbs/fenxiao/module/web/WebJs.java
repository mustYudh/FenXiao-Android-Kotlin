package com.nhbs.fenxiao.module.web;

import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import com.nhbs.fenxiao.data.UserProfile;
import com.yu.common.web.BaseWebJs;

/**
 * @author chenwei
 * @date 2017/8/30
 */
public class WebJs extends BaseWebJs {
  private WebView webView;

  public WebJs(Activity activity, WebView webView) {
    super(activity, webView);
    this.webView = webView;
  }

  //去店铺首页
  @JavascriptInterface public void backShop(String json) {
    UserProfile.getInstance().setIsMerchant(1);
    getActivity().finish();
  }
}
