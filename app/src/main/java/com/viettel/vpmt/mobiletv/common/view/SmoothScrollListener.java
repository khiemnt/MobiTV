package com.viettel.vpmt.mobiletv.common.view;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

/**
 * Recycler view Smooth Scroll Listener
 * Created by neo on 4/1/2016.
 */
public class SmoothScrollListener extends RecyclerView.OnScrollListener {
    private final Context context;
    private static final Object scrollTag = new Object(); // this can be static or not, depending what u want to achieve
    final Picasso picasso;

    public SmoothScrollListener(Context context) {
        this.context = context;
        picasso = Picasso.with(context);
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            picasso.resumeTag(scrollTag);
        } else {
            picasso.pauseTag(scrollTag);
        }
    }
}
