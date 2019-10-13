package com.nhbs.fenxiao.module.mine.bean;

import java.util.List;

public class MineSpreadLogsListBean {

        public List<RowsBean> rows;

        public static class RowsBean {
            /**
             * imgs : http://nhbs.oss-cn-beijing.aliyuncs.com/02.jpg
             * shareCount : 5
             * createTime : 1562315495745
             * endTime : 1564617600000
             * id : 271975273757413123
             * shopId : 271998019224735744
             * title : 广告
             * grossSpread : 11
             * pvSpread : 0.2
             * userId : 6499c7a3339140d488a2a2fdc3999ebc
             * content : 测3息广告通知
             */

            public String imgs;
            public String shareCount;
            public Long createTime;
            public Long endTime;
            public String id;
            public String shopId;
            public String title;
            public String grossSpread;
            public String pvSpread;
            public String userId;
            public String content;
        }
}
