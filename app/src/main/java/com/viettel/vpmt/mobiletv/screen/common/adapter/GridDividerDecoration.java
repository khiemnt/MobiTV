package com.viettel.vpmt.mobiletv.screen.common.adapter;

import com.viettel.vpmt.mobiletv.common.util.CompatibilityUtil;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by neo on 5/29/2016.
 */
public class GridDividerDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = { android.R.attr.listDivider };

//    private Drawable mDivider;
    private int mInsets;
    private int mSpanCount;

    public GridDividerDecoration(Context context, int spanCount) {
        mSpanCount = spanCount;
//        TypedArray a = context.obtainStyledAttributes(ATTRS);
//        mDivider = a.getDrawable(0);
//        a.recycle();

//        mInsets = context.getResources().getDimensionPixelSize(R.dimen.padding_item_common) / 2;
        mInsets = CompatibilityUtil.getItemSpacing(context);
    }

//    @Override
//    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
////        drawVertical(c, parent);
////        drawHorizontal(c, parent);
//    }

    /** Draw dividers at each expected grid interval */
//    public void drawVertical(Canvas c, RecyclerView parent) {
//        if (parent.getChildCount() == 0) return;
//
//        final int childCount = parent.getChildCount();
//
//        for (int i = 0; i < childCount; i++) {
//            final View child = parent.getChildAt(i);
//            final RecyclerView.LayoutParams params =
//                    (RecyclerView.LayoutParams) child.getLayoutParams();
//
//            final int left = child.getLeft() - params.leftMargin - mInsets;
//            final int right = child.getRight() + params.rightMargin + mInsets;
//            final int top = child.getBottom() + params.bottomMargin + mInsets;
//            final int bottom = top + mDivider.getIntrinsicHeight();
//            mDivider.setBounds(left, top, right, bottom);
//            mDivider.draw(c);
//        }
//    }

    /** Draw dividers to the right of each child view */
//    public void drawHorizontal(Canvas c, RecyclerView parent) {
//        final int childCount = parent.getChildCount();
//
//        for (int i = 0; i < childCount; i++) {
//            final View child = parent.getChildAt(i);
//            final RecyclerView.LayoutParams params =
//                    (RecyclerView.LayoutParams) child.getLayoutParams();
//
//            final int left = child.getRight() + params.rightMargin + mInsets;
//            final int right = left + mDivider.getIntrinsicWidth();
//            final int top = child.getTop() - params.topMargin - mInsets;
//            final int bottom = child.getBottom() + params.bottomMargin + mInsets;
//            mDivider.setBounds(left, top, right, bottom);
//            mDivider.draw(c);
//        }
//    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //We can supply forced insets for each item view here in the Rect
        int position = parent.getChildAdapterPosition(view);
//        outRect.set(mInsets / 2, mInsets / 2, mInsets / 2, mInsets / 2);
        if (position % mSpanCount == 0) {
            outRect.set(0, 0, mInsets, mInsets);
        } else if (position % mSpanCount == mSpanCount - 1) {
            outRect.set(0, 0, mInsets, mInsets);
        } else {
            outRect.set(0, 0, mInsets, mInsets);
        }
    }
}