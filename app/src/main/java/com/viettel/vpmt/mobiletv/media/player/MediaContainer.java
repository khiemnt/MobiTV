package com.viettel.vpmt.mobiletv.media.player;

import com.viettel.vpmt.mobiletv.media.PlayerFragment;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Media Container
 * Created by neo on 5/31/2016.
 */
public class MediaContainer extends LinearLayout {

    public MediaContainer(Context context) {
        super(context);
    }

    public MediaContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(widthSize, (int) (widthSize / PlayerFragment.PLAYER_RATIO));
    }
}
