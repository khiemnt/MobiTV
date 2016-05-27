package com.viettel.vpmt.mobiletv.network.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Schedule data
 * Created by neo on 5/19/2016.
 */
public class ScheduleData {
    @SerializedName("schedule")
    private List<ChannelSchedule> mSchedules;

    public List<ChannelSchedule> getSchedules() {
        return mSchedules;
    }

    public void setSchedules(List<ChannelSchedule> schedules) {
        mSchedules = schedules;
    }
}
