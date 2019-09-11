package com.nhbs.fenxiao.module.mine.bean;

import java.util.List;

public class MineGroupBean {


    /**
     * userGroupTos : [{"id":"cd363f2339274ab2a42bf76a3a695ed5","userName":"用户70029219","realName":null,"userMobile":"15958126885","photo":"https://img.ivsky.com/img/tupian/pre/201811/17/kafeidian-001.jpg","createTime":1567070029219,"updateTime":1567070029219,"divideMoney":null},{"id":"d95fc257937047c3b3706419ad000015","userName":"用户80412143","realName":null,"userMobile":"13825555567","photo":"https://img.ivsky.com/img/tupian/pre/201811/17/kafeidian-001.jpg","createTime":1563180412143,"updateTime":1567804704448,"divideMoney":null},{"id":"da7cc77694424b4095ba390ad5615930","userName":"用户73967077","realName":null,"userMobile":"15967673737","photo":"https://img.ivsky.com/img/tupian/pre/201811/17/kafeidian-001.jpg","createTime":1565573967076,"updateTime":1565573967076,"divideMoney":null},{"id":"efa82cf7866b40ac9a4df7b02e74c546","userName":"用户90774253","realName":null,"userMobile":"15757129513","photo":"https://img.ivsky.com/img/tupian/pre/201811/17/kafeidian-001.jpg","createTime":1567490774253,"updateTime":1567752330946,"divideMoney":null},{"id":"f1d2d47f3354490fb2669f9167cfe356","userName":"用户19050058","realName":null,"userMobile":"15968170722","photo":"https://img.ivsky.com/img/tupian/pre/201811/17/kafeidian-001.jpg","createTime":1564819050057,"updateTime":1564819050057,"divideMoney":null}]
     * divideMoney : 0
     */

    public String divideMoney;
    public List<UserGroupTosBean> userGroupTos;

    public static class UserGroupTosBean {
        /**
         * id : cd363f2339274ab2a42bf76a3a695ed5
         * userName : 用户70029219
         * realName : null
         * userMobile : 15958126885
         * photo : https://img.ivsky.com/img/tupian/pre/201811/17/kafeidian-001.jpg
         * createTime : 1567070029219
         * updateTime : 1567070029219
         * divideMoney : null
         */

        public String id;
        public String userName;
        public String realName;
        public String userMobile;
        public String photo;
        public String createTime;
        public String updateTime;
        public String divideMoney;
    }
}
