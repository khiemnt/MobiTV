package com.viettel.vpmt.mobiletv.screen.bundle;

import com.viettel.vpmt.mobiletv.common.util.CompatibilityUtil;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Bundle Divider Decoration
 * Created by neo on 5/29/2016.
 */
public class BundleDividerDecoration extends RecyclerView.ItemDecoration {


//    private Drawable mDivider;
    private int mSpacing;
    private int mSpanCount;
    private int mMargin;

    public BundleDividerDecoration(Context context, int spanCount) {
        mSpanCount = spanCount;
//        TypedArray a = context.obtainStyledAttributes(ATTRS);
//        mDivider = a.getDrawable(0);
//        a.recycle();

//        mSpacing = context.getResources().getDimensionPixelSize(R.dimen.padding_item_common) / 2;
        mSpacing = CompatibilityUtil.getItemSpacing(context);
        mMargin = CompatibilityUtil.getScreenMargin(context);
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
//            final int left = child.getLeft() - params.leftMargin - mSpacing;
//            final int right = child.getRight() + params.rightMargin + mSpacing;
//            final int top = child.getBottom() + params.bottomMargin + mSpacing;
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
//            final int left = child.getRight() + params.rightMargin + mSpacing;
//            final int right = left + mDivider.getIntrinsicWidth();
//            final int top = child.getTop() - params.topMargin - mSpacing;
//            final int bottom = child.getBottom() + params.bottomMargin + mSpacing;
//            mDivider.setBounds(left, top, right, bottom);
//            mDivider.draw(c);
//        }
//    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //We can supply forced insets for each item view here in the Rect
        int position = parent.getChildAdapterPosition(view);
//        outRect.set(mSpacing / 2, mSpacing / 2, mSpacing / 2, mSpacing / 2);
//        int left = 0;
//        if (position % mSpanCount == 0) {
//            left = 0;
//        } else {
//            left = mSpacing;
//        }
//
//        int top = 0;
//        if (position < mSpanCount) {
//            top = mMargin;
//        }
//
//        int right = 0;
//        int bottom = mSpacing;
        outRect.set(mSpacing / 2, 0, mSpacing / 2, mSpacing);
    }
}