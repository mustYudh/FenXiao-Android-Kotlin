package com.nhbs.fenxiao.module.order.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.nhbs.fenxiao.R;

/**
 * @author myx
 * @date on 2019-12-01
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email mayuxuan@innobuddy.com
 */
public class ChooseRefundTypeDialog extends Dialog {

    public ChooseRefundTypeDialog(@NonNull Context context, int style ) {
        super(context, style);
        setContentView(R.layout.dialog_refund_type);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        window.setWindowAnimations(R.style.share_animation);
        window.setAttributes(params);
    }
}
