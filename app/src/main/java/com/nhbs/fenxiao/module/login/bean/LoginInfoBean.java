package com.nhbs.fenxiao.module.login.bean;

import java.io.Serializable;

/**
 * @author yudneghao
 * @date 2019-07-08
 */
public class LoginInfoBean implements Serializable {

    /**
     * user : {"isAgency":0,"openId":null,"photo":null,"isMerchant":0,"userName":null,"realName":null,"balance":null,"userMobile":"13534376664","idCardFace":null,"idCardNum":null,"id":"6499c7a3339140d488a2a2fdc3999ebc","invitePeopleCode":"","idCardBack":null,"invitationCode":"28AD6664","status":1}
     * token : 8e761d357e344ce7b9821549b2860f15
     */

    public UserBean user;
    public String token;


    public static class UserBean implements Serializable {
        /**
         * isAgency : 0
         * openId : null
         * photo : null
         * isMerchant : 0
         * userName : null
         * realName : null
         * balance : null
         * userMobile : 13534376664
         * idCardFace : null
         * idCardNum : null
         * id : 6499c7a3339140d488a2a2fdc3999ebc
         * invitePeopleCode :
         * idCardBack : null
         * invitationCode : 28AD6664
         * status : 1
         */

        public int isAgency;
        public String openId;
        public String photo;
        public int isMerchant;
        public String userName;
        public String realName;
        public String balance;
        public String userMobile;
        public String idCardFace;
        public String idCardNum;
        public String id;
        public String invitePeopleCode;
        public String idCardBack;
        public String invitationCode;
        public int status;


    }
}

