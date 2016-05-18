package com.viettel.vpmt.mobiletv.screen.videodetail.fragment.item;

/**
 * Video image object
 * Created by ThanhTD on 3/23/2016.
 */
public class ImageItem {
    private String mVideoId;
    private String mUri;
    private String mDes;
    private String mName;

    public ImageItem(String videoId, String uri, String des, String name) {
        mVideoId = videoId;
        mUri = uri;
        mDes = des;
        mName = name;
    }

    public String getVideoId() {
        return mVideoId;
    }

    public void setVideoId(String videoId) {
        mVideoId = videoId;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }

    public String getDes() {
        return mDes;
    }

    public void setDes(String des) {
        mDes = des;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
