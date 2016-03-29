package com.viettel.vpmt.mobiletv.screen.home.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.viettel.vpmt.mobiletv.common.Constants;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.network.dto.VideoDetail;
import com.viettel.vpmt.mobiletv.screen.videodetail.activity.VideoDetailActivity;

/**
 * Content item clicked
 * Created by neo on 3/28/2016.
 */
public class ContentItemClickListener implements View.OnClickListener {
    private Context mContext;
    private Content mContent;

    public ContentItemClickListener(Context context, Content content) {
        mContext = context;
        mContent = content;
    }

    @Override
    public void onClick(View v) {
        switch (mContent.getType()) {
            case FILM:
                break;
            case VOD:
                mContext.startActivity(new Intent(mContext, VideoDetailActivity.class)
                        .putExtra(Constants.Extras.ID, mContent.getId()));
                break;
            case LIVETV:
                break;
        }
    }
}
