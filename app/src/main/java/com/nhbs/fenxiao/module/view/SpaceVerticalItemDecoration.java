package com.nhbs.fenxiao.module.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class SpaceVerticalItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpaceVerticalItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = space;
    }
}
