//package com.viettel.vpmt.mobiletv.screen.home.adapter;
//
//import android.graphics.Rect;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//
///**
// * Decoration for Grid RecyclerViews
// * Created by neo on 3/24/2016.
// */
//public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
//    private int mColumnCount;
//    private int mSpacing;
//    private boolean mIncludeEdge;
//
//    public GridSpacingItemDecoration(int columnCount, int spacing, boolean includeEdge) {
//        this.mColumnCount = columnCount;
//        this.mSpacing = spacing;
//        this.mIncludeEdge = includeEdge;
//    }
//
//    @Override
//    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        int position = parent.getChildAdapterPosition(view); // item position
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
////            if (position >= mColumnCount) {
////                outRect.top = mSpacing; // item top
////            }
//        }
//    }
//}
