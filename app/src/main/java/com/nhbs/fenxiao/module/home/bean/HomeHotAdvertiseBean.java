package com.nhbs.fenxiao.module.home.bean;

import java.math.BigDecimal;
import java.util.List;

public class HomeHotAdvertiseBean {


    public List<RowsBean> rows;

    public static class RowsBean {
        /**
         * imgs : http://nhbs.oss-cn-beijing.aliyuncs.com/02.jpg
         * total : 0
         * createTime : 1562315495745
         * id : 1
         * title : 测试下
         * pvSpread : 0.2
         * content : 测试消息广告通知
         */

        public String imgs;
        public int total;
        public long createTime;
        public String id;
        public String title;
        public BigDecimal pvSpread;
        public String content;
    }
}
