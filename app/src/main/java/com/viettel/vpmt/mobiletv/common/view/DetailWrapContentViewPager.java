package com.viettel.vpmt.mobiletv.common.view;

import com.viettel.vpmt.mobiletv.common.util.CompatibilityUtil;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Wrap Content Height ViewPager
 * Created by ThanhTD on 3/25/2016.
 */
public class DetailWrapContentViewPager extends ViewPager {
    public DetailWrapContentViewPager(Context context) {
        super(context);
    }

    public DetailWrapContentViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) height = h;
        }

        if (height == 0) {
            // Make size not zero
            if (CompatibilityUtil.isTablet(getContext())) {
                height = 500;
            } else {
                height = 250;
            }
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
