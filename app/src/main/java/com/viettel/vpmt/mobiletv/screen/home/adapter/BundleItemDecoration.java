package com.viettel.vpmt.mobiletv.screen.home.adapter;

import com.viettel.vpmt.mobiletv.base.log.Logger;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Decoration for Grid RecyclerViews
 * Created by neo on 3/24/2016.
 */
public class BundleItemDecoration extends RecyclerView.ItemDecoration {
    private int mSpacing;
    private int mSpan;

    public BundleItemDecoration(int spacing, int span) {
        this.mSpacing = spacing;
        mSpan = span;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        Logger.e("position " + position);
        Logger.e("mSpan " + mSpan);
        if (position % mSpan > 0 ) {
            outRect.left = mSpacing;
        }
        outRect.top = mSpacing;
    }
}
