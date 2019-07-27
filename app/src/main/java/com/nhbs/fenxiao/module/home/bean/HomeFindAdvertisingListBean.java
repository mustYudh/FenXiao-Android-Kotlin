package com.nhbs.fenxiao.module.home.bean;

import java.math.BigDecimal;
import java.util.List;

public class HomeFindAdvertisingListBean {


    public List<RowsBean> rows;

    public static class RowsBean {
        /**
         * imgs : http://nhbs.oss-cn-beijing.aliyuncs.com/02.jpg
         * city : 全部
         * typeName : 招商
         * title : 广告测试下
         * grossSpread : 1
         * userId : d95fc257937047c3b3706419ad000015
         * content : 测试111告通知
         * province : 全部
         * createTime : 1563887558320
         * district : 全部
         * typeId : 4
         * endTime : 1564617600000
         * id : 271975273757231122
         * shopId : 271997446576410624
         * pvSpread : 0.2
         * status : 2
         */

        public String imgs;
        public String city;
        public String typeName;
        public String title;
        public int grossSpread;
        public String userId;
        public String content;
        public String province;
        public long createTime;
        public String district;
        public String typeId;
        public long endTime;
        public String id;
        public String shopId;
        public BigDecimal pvSpread;
        public int status;
    }
}
