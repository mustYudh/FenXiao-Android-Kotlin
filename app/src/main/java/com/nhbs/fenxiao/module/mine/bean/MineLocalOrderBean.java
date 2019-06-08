package com.nhbs.fenxiao.module.mine.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

public class MineLocalOrderBean implements MultiItemEntity, Serializable {
    public int itemType;

    @Override
    public int getItemType() {
        return itemType;
    }


}
