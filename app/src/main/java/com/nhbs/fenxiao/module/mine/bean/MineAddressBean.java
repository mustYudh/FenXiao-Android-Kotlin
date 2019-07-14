package com.nhbs.fenxiao.module.mine.bean;

import java.io.Serializable;
import java.util.List;

public class MineAddressBean implements Serializable {
    public List<ListBean> rows;

    public static class ListBean implements Serializable {
        /**
         * id : 948f9066576446bc853cb77927b366e6
         * userName : 测试
         * mobile : 17752721509
         * address : 北京市北京市东城区
         * specificAddress : 哦哦哦
         * status : 1
         * createTime : 1563087517632
         * updateTime : 1563087517632
         * userId : 0d78fb9f25f94265b960f1e6258e7fcb
         * type : 1
         */

        public String id;
        public String userName;
        public String mobile;
        public String address;
        public String specificAddress;
        public int status;
        public long createTime;
        public long updateTime;
        public String userId;
        public int type;
    }
}
