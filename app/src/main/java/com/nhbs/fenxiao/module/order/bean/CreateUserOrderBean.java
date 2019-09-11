package com.nhbs.fenxiao.module.order.bean;

public class CreateUserOrderBean {

    /**
     * code : 2000
     * msg : 操作成功
     * data : {"id":"276577388119199744","orderId":"275892757828997120","title":"速干短袖","userId":"0d78fb9f25f94265b960f1e6258e7fcb","status":0,"type":2,"price":68,"createTime":1564984787887,"updateTime":null,"addressId":"cb7aa6e833bc423798c4eca8e37ced2d","expressNumber":null,"receivingTime":null,"number":1,"totalPrice":"68","shopId":"271445551621345280","color":"速干","dealWay":3,"size":"黑色"}
     * total : null
     */

    public int code;
    public String msg;
    public DataBean data;
    public Object total;

    public static class DataBean {
        /**
         * id : 276577388119199744
         * orderId : 275892757828997120
         * title : 速干短袖
         * userId : 0d78fb9f25f94265b960f1e6258e7fcb
         * status : 0
         * type : 2
         * price : 68
         * createTime : 1564984787887
         * updateTime : null
         * addressId : cb7aa6e833bc423798c4eca8e37ced2d
         * expressNumber : null
         * receivingTime : null
         * number : 1
         * totalPrice : 68
         * shopId : 271445551621345280
         * color : 速干
         * dealWay : 3
         * size : 黑色
         */

        public String id;
        public String orderId;
        public String title;
        public String userId;
        public String status;
        public String type;
        public String price;
        public String createTime;
        public String updateTime;
        public String addressId;
        public String expressNumber;
        public String receivingTime;
        public String number;
        public String totalPrice;
        public String shopId;
        public String color;
        public String dealWay;
        public String size;
    }
}
