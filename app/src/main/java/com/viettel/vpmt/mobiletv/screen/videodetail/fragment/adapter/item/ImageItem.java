package com.viettel.vpmt.mobiletv.screen.videodetail.fragment.adapter.item;

/**
 * Created by ThanhTD on 3/23/2016.
 */
public class ImageItem {
    Float videoId;
    String uri;
    String des;
    String name;

    public ImageItem(String videoId, String uri, String des, String name) {
        this.videoId = Float.valueOf(videoId);
        this.uri = uri;
        this.des = des;
        this.name = name;
    }

    public Float getVideoId() {
        return videoId;
    }

    public void setVideoId(Float videoId) {
        this.videoId = videoId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
