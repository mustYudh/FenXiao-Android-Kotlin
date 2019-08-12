package com.nhbs.fenxiao.module.order.bean;

import java.util.List;

public class MineOrderListBean {


    public List<RowsBean> rows;

    public static class RowsBean {
        /**
         * receivingTime : 0
         * orderId : 275892757828997120
         * totalPrice : 0
         * mobile : 17752721509
         * updateTime : 1565027293614
         * title : 速干短袖
         * type : 2
         * userName : 用户62473766
         * userId : 0d78fb9f25f94265b960f1e6258e7fcb
         * addressId : cb7aa6e833bc423798c4eca8e37ced2d
         * number : 0
         * expressNumber :
         * createTime : 1564993226995
         * price : 68
         * id : 276612784303640576
         * shopId : 271445551621345280
         * status : 1
         */

        public int receivingTime;
        public String orderId;
        public String totalPrice;
        public String mobile;
        public long updateTime;
        public String title;
        public int type;
        public String userName;
        public String userId;
        public String addressId;
        public int number;
        public String expressNumber;
        public long createTime;
        public String price;
        public String id;
        public String shopId;
        public String tagOne;
        public String tagTwo;
        public String shopImage;
        public String goodsImage;
        public String status;
    }
}
