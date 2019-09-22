package com.nhbs.fenxiao.im.init;

import android.text.TextUtils;
import com.netease.nim.uikit.api.model.main.OnlineStateContentProvider;
import com.nhbs.fenxiao.im.preference.NimAppCache;

/**
 * Created by hzchenkang on 2017/3/31.
 */

public class OnlineStateContentProvider
    implements com.netease.nim.uikit.api.model.main.OnlineStateContentProvider {

    @Override
    public String getSimpleDisplay(String account) {
        String content = getDisplayContent(account, true);
        if (!TextUtils.isEmpty(content)) {
            content = "[" + content + "]";
        }
        return content;
    }

    @Override
    public String getDetailDisplay(String account) {
        return getDisplayContent(account, false);
    }

    private String getDisplayContent(String account, boolean simple) {
        if (account == null || account.equals(NimAppCache.getAccount())) {
            return "";
        }

        // 被过滤掉的直接显示在线，如机器人
        if (OnlineStateEventSubscribe.subscribeFilter(account)) {
            return "在线";
        }

        // 检查是否订阅过
        OnlineStateEventManager.checkSubscribe(account);

        OnlineState onlineState = OnlineStateEventCache.getOnlineState(account);
        return OnlineStateEventManager.getOnlineClientContent(NimAppCache.getContext(), onlineState, simple);
    }
}
