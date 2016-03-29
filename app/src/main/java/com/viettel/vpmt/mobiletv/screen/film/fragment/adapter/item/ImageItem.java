package com.viettel.vpmt.mobiletv.screen.film.fragment.adapter.item;

/**
 * Created by ThanhTD on 3/23/2016.
 */
public class ImageItem {
    Float videoId;
    Float partOfVideo;
    String uri;
    String des;

    public ImageItem(String videoId, String uri, String des) {
        this.videoId = Float.valueOf(videoId);
        this.uri = uri;
        this.des = des;
    }

    public ImageItem(Float videoId, Float partOfVideo, String uri, String des) {
        this.videoId = videoId;
        this.partOfVideo = partOfVideo;
        this.uri = uri;
        this.des = des;
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

    public Float getPartOfVideo() {
        return partOfVideo;
    }

    public void setPartOfVideo(Float partOfVideo) {
        this.partOfVideo = partOfVideo;
    }
}
