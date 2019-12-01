package com.nhbs.fenxiao.module.product.bean;

import java.util.List;

public class MerchandiseClassBean {


    public List<RowsBean> rows;

    public static class RowsBean {
        /**
         * classify : 服饰
         * id : 1
         */

        public String classify;
        public String id;
        public boolean isIs_select;
    }
}
