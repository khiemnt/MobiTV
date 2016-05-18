package com.viettel.vpmt.mobiletv.screen.filmdetail.fragment.item;

/**
 * Film item object
 * Created by ThanhTD on 3/23/2016.
 */
public class ImageItem {
    String mVideoId;
    Float mPartOfVideo;
    String mUri;
    String mDes;
    String mName;

    public ImageItem(String videoId, String uri, String des, String name) {
        mVideoId = videoId;
        mUri = uri;
        mDes = des;
        mName = name;
    }

    public ImageItem(String videoId, Float partOfVideo, String uri, String des) {
        mVideoId = videoId;
        mPartOfVideo = partOfVideo;
        mUri = uri;
        mDes = des;
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

    public Float getPartOfVideo() {
        return mPartOfVideo;
    }

    public void setPartOfVideo(Float partOfVideo) {
        mPartOfVideo = partOfVideo;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
