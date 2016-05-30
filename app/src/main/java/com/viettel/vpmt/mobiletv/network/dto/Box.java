package com.viettel.vpmt.mobiletv.network.dto;

import com.google.gson.annotations.SerializedName;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.common.Constants;
import com.viettel.vpmt.mobiletv.common.util.DeviceUtils;
import com.viettel.vpmt.mobiletv.common.view.ChannelImage;
import com.viettel.vpmt.mobiletv.common.view.FilmImage;
import com.viettel.vpmt.mobiletv.common.view.VideoImage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;

import java.util.List;

/**
 * Box DTO
 * Created by neo on 3/22/2016.
 */
public class Box {
    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("type")
    private Type mType;
    @SerializedName("content")
    private List<Content> mContents;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Type getType() {
        return mType;
    }

    public void setType(Type type) {
        mType = type;
    }

    public List<Content> getContents() {
        return mContents;
    }

    public void setContents(List<Content> contents) {
        mContents = contents;
    }

    public enum Type {
        @SerializedName("BANNER")
        BANNER,
        @SerializedName("VOD")
        VOD,
        @SerializedName("LIVETV")
        LIVETV,
        @SerializedName("CONTINUE")
        CONTINUE,
        @SerializedName("FOCUS")
        FOCUS,
        @SerializedName("FILM")
        FILM,
        @SerializedName("TVSHOW")
        TVSHOW,
    }

    public static Point calculateItemSize(Activity activity, Box.Type boxType) {
        // Set column number by content type
        int spanCount = getSpanCount(boxType);
        double ratio = 1.0;
        switch (boxType) {
            case FILM:
                ratio = FilmImage.VIEW_ASPECT_RATIO;
                break;
            case VOD:
                ratio = VideoImage.VIEW_ASPECT_RATIO;
                break;
            case LIVETV:
                ratio = ChannelImage.VIEW_ASPECT_RATIO;
                break;
            case CONTINUE:
                ratio =VideoImage.VIEW_ASPECT_RATIO;
                break;
            case FOCUS:
                ratio = VideoImage.VIEW_ASPECT_RATIO;
                break;
            default:
                break;
        }

        // Calculate spacing
        int screenWidth = DeviceUtils.getDeviceSize(activity).x;
        int screenPadding = activity.getResources().getDimensionPixelSize(R.dimen.screen_margin);
        Point itemImageSize = new Point();
        itemImageSize.x = (screenWidth - (spanCount - 1) * Constants.ITEM_SPACING - 2 * screenPadding) / spanCount;
        itemImageSize.y = (int) (itemImageSize.x / ratio);

        return itemImageSize;
    }

    public static int getSpanCount(Box.Type boxType) {
        switch (boxType) {
            case FILM:
                return 3;
            case VOD:
                return 2;
            case LIVETV:
                return 3;
            case CONTINUE:
                return 2;
            case FOCUS:
                return 2;
            default:
                return 2;
        }
    }
}
