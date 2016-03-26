package com.viettel.vpmt.mobiletv.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Square Image view
 * Created by neo on 3/25/2016.
 */
public class SquareImage extends ImageView {
    private int mAspectRatioWidth;
    private int mAspectRatioHeight;

    public SquareImage(Context context) {
        super(context);
    }

    public SquareImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    // The aspect ratio to be respected by the measurer
    private static final double VIEW_ASPECT_RATIO = 1.0;

    private ViewAspectRatioMeasurer varm = new ViewAspectRatioMeasurer(VIEW_ASPECT_RATIO);

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        varm.measure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(varm.getMeasuredWidth(), varm.getMeasuredHeight());
    }
}
