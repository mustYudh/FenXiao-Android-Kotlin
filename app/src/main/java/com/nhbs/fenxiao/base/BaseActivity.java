package com.nhbs.fenxiao.base;

import android.view.View;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;
import com.yu.common.framework.BasicActivity;

public abstract class BaseActivity extends BasicActivity {

    @Override
    protected void handleNetWorkError(View view) {
    }

    @Override protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    @Override protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
