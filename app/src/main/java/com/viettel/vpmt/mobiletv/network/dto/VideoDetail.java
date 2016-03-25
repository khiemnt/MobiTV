package com.viettel.vpmt.mobiletv.network.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ThanhTD on 3/25/2016.
 */
public class VideoDetail
{
    @SerializedName("video_detail")
    private Content videoDetail;
    @SerializedName("streams")
    private Streams streams;
    @SerializedName("video_related")
    private VideoRelated videoRelated;

    public Content getVideoDetail()
    {
        return videoDetail;
    }

    public void setVideoDetail(Content videoDetail)
    {
        this.videoDetail = videoDetail;
    }

    public Streams getStreams()
    {
        return streams;
    }

    public void setStreams(Streams streams)
    {
        this.streams = streams;
    }

    public VideoRelated getVideoRelated()
    {
        return videoRelated;
    }

    public void setVideoRelated(VideoRelated videoRelated)
    {
        this.videoRelated = videoRelated;
    }
}
