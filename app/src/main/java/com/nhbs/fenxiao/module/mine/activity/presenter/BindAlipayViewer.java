package com.nhbs.fenxiao.module.mine.activity.presenter;

import com.yu.common.mvp.Viewer;


public interface BindAlipayViewer extends Viewer {
    void sendVerCodeSuccess();
    
    void boundAliAccountSuccess();
}
