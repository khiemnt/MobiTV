package com.viettel.vpmt.mobiletv.screen.home.adapter;

import com.viettel.vpmt.mobiletv.base.log.Logger;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Decoration for Grid RecyclerViews
 * Created by neo on 3/24/2016.
 */
public class GridItemDecoration extends RecyclerView.ItemDecoration {
    private int mSpacing;
    private int mSpan;
    private int mRecyclerViewPadding;

    public GridItemDecoration(int spacing, int span, int recyclerViewPadding) {
        this.mSpacing = spacing;
        mSpan = span;
        mRecyclerViewPadding = recyclerViewPadding;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        Logger.e("position " + position);
        Logger.e("mSpan " + mSpan);

        int diff = position % mSpan;
        if (diff != 0) {
            outRect.left = mSpacing * diff;
            outRect.right = -mSpacing* diff;
            Logger.i("@@!=0 " + mSpacing);
        } else {
            outRect.left = mRecyclerViewPadding;
            outRect.right = -mRecyclerViewPadding;
        }
        outRect.top = mSpacing;
//        outRect.left = mSpacing;
//        outRect.bottom = mSpacing;

//        outRect.set(mSpacing,mSpacing,mSpacing,mSpacing);

//        int column = position % mColumnCount; // item column
//
//        if (mIncludeEdge) {
//            outRect.left = mSpacing - column * mSpacing / mColumnCount; // mSpacing - column * ((1f / mColumnCount) * mSpacing)
//            outRect.right = (column + 1) * mSpacing / mColumnCount; // (column + 1) * ((1f / mColumnCount) * mSpacing)
//
//            if (position < mColumnCount) { // top edge
//                outRect.top = mSpacing;
//            }
//            outRect.bottom = mSpacing; // item bottom
//        } else {
//            outRect.left = column * mSpacing / mColumnCount; // column * ((1f / mColumnCount) * mSpacing)
//            outRect.right = mSpacing - (column + 1) * mSpacing / mColumnCount; // mSpacing - (column + 1) * ((1f /    mColumnCount) * mSpacing)
//            if (position >= mColumnCount) {
//                outRect.top = mSpacing; // item top
//            }
//        }
    }
}
