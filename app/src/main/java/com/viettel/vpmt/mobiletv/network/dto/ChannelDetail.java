package com.viettel.vpmt.mobiletv.network.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Detail DTO for Channel/TV screen
 * Created by neo on 5/17/2016.
 */
public class ChannelDetail {
    @SerializedName("channel_detail")
    private Content mChannelContent;
    @SerializedName("streams")
    private Streams mStreams;
    @SerializedName("schedule")
    private List<ChannelSchedule> mSchedules;
    @SerializedName("dateList")
    private List<String> mDateList;
    @SerializedName("channel_related")
    private ContentRelated mContentRelated;
    @SerializedName("currentTime")
    private String mCurrentTime;

    public Content getChannelContent() {
        return mChannelContent;
    }

    public void setChannelContent(Content channelContent) {
        mChannelContent = channelContent;
    }

    public Streams getStreams() {
        return mStreams;
    }

    public void setStreams(Streams streams) {
        mStreams = streams;
    }

    public List<String> getDateList() {
        return mDateList;
    }

    public void setDateList(List<String> dateList) {
        mDateList = dateList;
    }

    public ContentRelated getContentRelated() {
        return mContentRelated;
    }

    public void setContentRelated(ContentRelated contentRelated) {
        mContentRelated = contentRelated;
    }

    public List<ChannelSchedule> getSchedules() {
        return mSchedules;
    }

    public void setSchedules(List<ChannelSchedule> schedules) {
        mSchedules = schedules;
    }

    public String getCurrentTime() {
        return mCurrentTime;
    }

    public void setCurrentTime(String currentTime) {
        mCurrentTime = currentTime;
    }
}
