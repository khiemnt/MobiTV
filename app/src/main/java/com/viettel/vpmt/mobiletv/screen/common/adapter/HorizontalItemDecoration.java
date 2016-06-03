package com.viettel.vpmt.mobiletv.screen.common.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Decoration for Horizontal RecyclerViews
 * Created by neo on 3/24/2016.
 */
public class HorizontalItemDecoration extends RecyclerView.ItemDecoration {
    private int mSpace;

    public HorizontalItemDecoration(int space) {
        this.mSpace = space / 2;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.top = mSpace;
        outRect.right = mSpace;
        outRect.bottom = mSpace;

        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.left = 0;
        } else {
            outRect.left = mSpace;
        }
    }
}
