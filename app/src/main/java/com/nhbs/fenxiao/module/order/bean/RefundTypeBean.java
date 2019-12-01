package com.nhbs.fenxiao.module.order.bean;

/**
 * @author myx
 * @date on 2019-12-01
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email mayuxuan@innobuddy.com
 */
public class RefundTypeBean {

    private String name;
    private boolean isChecked = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
