package com.nhbs.fenxiao.module.home.bean;

import java.util.List;

public class HomeBannerBean {


    public List<RowsBean> rows;

    public static class RowsBean {
        /**
         * createTime : 1562315495745
         * id : 270068863478009860
         * title : 测试
         * type : 0
         * url : http://nhbs.oss-cn-beijing.aliyuncs.com/02.jpg
         * skipUrl : http://nhbs.oss-cn-beijing.aliyuncs.com/02.jpg
         */

        public long createTime;
        public long id;
        public String title;
        public String type;
        public String url;
        public String skipUrl;
    }
}
