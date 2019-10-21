package com.nhbs.fenxiao.utils.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.module.mine.activity.BindAliPayActivity;
import com.nhbs.fenxiao.module.mine.activity.MineWithdrawActivity;
import com.nhbs.fenxiao.module.mine.bean.MineUserInfoBean;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.toast.ToastUtils;
import com.yu.common.windown.BaseDialog;
import com.yu.share.AuthLoginHelp;
import com.yu.share.callback.AuthLoginCallback;

/**
 * @author yudneghao
 * @date 2019-10-21
 */
public class IncomeAssetsDialog extends BaseDialog {

    private int type = 1;

    public IncomeAssetsDialog(@NonNull Activity context, MineUserInfoBean mineUserInfoBean, AuthLoginCallback authLoginCallback) {
        super(context,R.style.Dialog);
        AuthLoginHelp mAuthLoginHelp = new AuthLoginHelp(context);
        mAuthLoginHelp.callback(authLoginCallback);
        setContentView(R.layout.dialog_withdraw);
        setCanceledOnTouchOutside(true);
        bindView(R.id.iv_close, v -> dismiss());
        ImageView iv_ali = bindView(R.id.iv_ali);
        ImageView iv_wx = bindView(R.id.iv_wx);

        bindView(R.id.rl_ali, v -> {
            iv_ali.setImageResource(R.drawable.ic_circle_select);
            iv_wx.setImageResource(R.drawable.ic_circle_normal);
            type = 1;
        });
        bindView(R.id.rl_wx, v -> {
            iv_wx.setImageResource(R.drawable.ic_circle_select);
            iv_ali.setImageResource(R.drawable.ic_circle_normal);
            type = 2;
        });
        bindView(R.id.tv_commit, v -> {
            if (mineUserInfoBean != null) {
                switch (type) {
                    case 1:
                        if (mineUserInfoBean.aliStatus != null && mineUserInfoBean.aliStatus.equals("1")) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("WITHDRAW_TYPE", type);
                            bundle.putString("WITHDRAW_MONEY", mineUserInfoBean.balance);
                            LauncherHelper.from(context).startActivity(MineWithdrawActivity.class, bundle);
                        } else {
                            LauncherHelper.from(context).startActivityForResult(BindAliPayActivity.class, 1);
                        }
                        break;
                    case 2:
                        if (mineUserInfoBean.winStatus != null && mineUserInfoBean.winStatus.equals("1")) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("WITHDRAW_TYPE", type);
                            bundle.putString("WITHDRAW_MONEY", mineUserInfoBean.balance);
                            LauncherHelper.from(context).startActivity(MineWithdrawActivity.class, bundle);
                        } else {
                            //绑定微信
                            boolean installWeChat =
                                    UMShareAPI.get(context).isInstall(context, SHARE_MEDIA.WEIXIN);
                            if (installWeChat) {
                                mAuthLoginHelp.login(SHARE_MEDIA.WEIXIN);
                            } else {
                                ToastUtils.show("请先安装微信");
                            }
                        }
                        break;
                    default:
                }
            } else {
                ToastUtils.show("用户数据异常");
            }
            dismiss();
        });
        setOnDismissListener(dialog -> type = 1);

        Window win = getWindow();
        assert win != null;
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        win.setAttributes(lp);
    }

}
