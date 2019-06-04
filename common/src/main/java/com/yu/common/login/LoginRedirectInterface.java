package com.yu.common.login;

import android.os.Bundle;
import com.yu.common.mvp.Viewer;

/**
 * @author yudneghao
 * @date 2019-06-04
 */
public interface LoginRedirectInterface extends Viewer {
    public Bundle getLoginExtraBundle();
    public String getRedirectOtherAction();
    public String getRedirectActivityClassName();
}
