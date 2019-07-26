package com.nhbs.fenxiao.wxapi;

import android.os.Bundle;
import com.nhbs.fenxiao.R;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;


/**
 * @author yudneghao
 * @date 2019-04-30
 */
public class WXPayEntryActivity extends WXEntryActivity implements IWXAPIEventHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResp(BaseResp baseResp) {
        finish();
        overridePendingTransition(R.anim.activity_alpha_out, R.anim.activity_alpha_out);
    }
}
