package com.denghao.control;

import android.support.v4.app.Fragment;

/**
 * @author yudneghao
 * @date 2018/6/12
 */

public interface TabItem {

  Fragment getCurrentFragment();



  int getPosition();

  String getTag();

  void setMessageHint(int count);


}
