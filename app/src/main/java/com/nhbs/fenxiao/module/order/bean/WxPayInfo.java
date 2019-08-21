package com.nhbs.fenxiao.module.order.bean;

import java.io.Serializable;

public class WxPayInfo implements Serializable {

    /**
     * appId : wxb9892c638e16cdca
     * nonceStr : 6UMDOtsbVRnVOqOzV4OdJO7uQ53Y4R0G
     * packageValue : Sign=WXPay
     * partnerId : 1544661201
     * prepayId : wx21154812436578bc0ce06cac1648914900
     * sign : 7C90AD6AB79CF26B2BA77E7D7C6F6400
     * timeStamp : 1566373692
     */

    public String appId;
    public String nonceStr;
    public String packageValue;
    public String partnerId;
    public String prepayId;
    public String sign;
    public String timeStamp;
}
