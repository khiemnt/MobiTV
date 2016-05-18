package com.viettel.vpmt.mobiletv.network.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Video detail object
 * Created by ThanhTD on 3/25/2016.
 */
public class VideoDetail {
    @SerializedName("video_detail")
    private Content videoDetail;
    @SerializedName("streams")
    private Streams streams;
    @SerializedName("video_related")
    private ContentRelated mContentRelated;

    public Content getVideoDetail() {
        return videoDetail;
    }

    public void setVideoDetail(Content videoDetail) {
        this.videoDetail = videoDetail;
    }

    public Streams getStreams() {
        return streams;
    }

    public void setStreams(Streams streams) {
        this.streams = streams;
    }

    public ContentRelated getContentRelated() {
        return mContentRelated;
    }

    public void setContentRelated(ContentRelated contentRelated) {
        this.mContentRelated = contentRelated;
    }
}
