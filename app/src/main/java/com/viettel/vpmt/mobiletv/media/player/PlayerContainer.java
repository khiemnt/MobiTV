package com.viettel.vpmt.mobiletv.media.player;

import com.viettel.vpmt.mobiletv.base.log.Logger;
import com.viettel.vpmt.mobiletv.media.PlayerFragment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * PlayerContainer
 * Created by neo on 5/31/2016.
 */
public class PlayerContainer extends FrameLayout {

    public PlayerContainer(Context context) {
        super(context);
    }

    public PlayerContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(widthSize, (int) (widthSize / PlayerFragment.PLAYER_RATIO));
    }
}
