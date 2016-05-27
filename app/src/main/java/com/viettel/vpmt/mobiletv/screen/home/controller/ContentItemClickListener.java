package com.viettel.vpmt.mobiletv.screen.home.controller;

import com.viettel.vpmt.mobiletv.common.Constants;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.screen.channeldetail.activity.ChannelDetailActivity;
import com.viettel.vpmt.mobiletv.screen.filmdetail.activity.FilmDetailActivity;
import com.viettel.vpmt.mobiletv.screen.videodetail.activity.VideoDetailActivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

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
                mContext.startActivity(new Intent(mContext, FilmDetailActivity.class)
                        .putExtra(Constants.Extras.ID, mContent.getId())
                        .putExtra(Constants.Extras.TITLE, mContent.getName())
                        .putExtra(Constants.Extras.COVER_IMAGE_URL, mContent.getCoverImage()));
                break;
            case VOD:
                mContext.startActivity(new Intent(mContext, VideoDetailActivity.class)
                        .putExtra(Constants.Extras.ID, mContent.getId())
                        .putExtra(Constants.Extras.TITLE, mContent.getName())
                        .putExtra(Constants.Extras.COVER_IMAGE_URL, mContent.getCoverImage()));
                break;
            case LIVETV:
                mContext.startActivity(new Intent(mContext, ChannelDetailActivity.class)
                        .putExtra(Constants.Extras.ID, mContent.getId())
                        .putExtra(Constants.Extras.TITLE, mContent.getName())
                        .putExtra(Constants.Extras.COVER_IMAGE_URL, mContent.getCoverImage()));
                break;
        }
    }
}
