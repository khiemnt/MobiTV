package com.viettel.vpmt.mobiletv.common.view;

import com.viettel.vpmt.mobiletv.base.log.Logger;

import android.content.Context;
import android.support.annotation.IntegerRes;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Banner Image view
 * Created by neo on 3/25/2016.
 */
public class BannerImage extends ImageView implements View.OnTouchListener {
    // The aspect ratio to be respected by the measurer
    private static final double VIEW_ASPECT_RATIO = 2.0;

    public BannerImage(Context context) {
        super(context);
        init();
    }

    public BannerImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BannerImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        this.setOnTouchListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(widthSize, (int) (widthSize / VIEW_ASPECT_RATIO));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
