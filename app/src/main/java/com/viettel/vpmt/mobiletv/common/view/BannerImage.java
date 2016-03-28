package com.viettel.vpmt.mobiletv.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.log.Logger;

/**
 * Banner Image view
 * Created by neo on 3/25/2016.
 */
public class BannerImage extends ImageView implements View.OnTouchListener {
    private int mAspectRatioWidth;
    private int mAspectRatioHeight;

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

    // The aspect ratio to be respected by the measurer
    private static final double VIEW_ASPECT_RATIO = 2.0;

    private ViewAspectRatioMeasurer varm = new ViewAspectRatioMeasurer(VIEW_ASPECT_RATIO);

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        varm.measure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(varm.getMeasuredWidth(), varm.getMeasuredHeight());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Logger.e("BannerImage", "Touch");
        return true;
    }
}
