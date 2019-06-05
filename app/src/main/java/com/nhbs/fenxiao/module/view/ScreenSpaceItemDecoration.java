package com.nhbs.fenxiao.module.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yu.common.utils.DensityUtil;


public class ScreenSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public ScreenSpaceItemDecoration(Context context, int space) {
        this.space = DensityUtil.dip2px(context, space);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.right = 0;
        outRect.top = space;
        //瀑布流专属分割线
        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        /**
         * 根据params.getSpanIndex()来判断左右边确定分割线
         * 第一列设置左边距为space，右边距为space/2  （第二列反之）
         */
        if (params.getSpanIndex() % 2 == 0) {
            outRect.left = space;
        } else {
            outRect.left = space;
        }
    }
}
