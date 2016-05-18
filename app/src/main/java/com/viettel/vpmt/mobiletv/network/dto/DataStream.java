package com.viettel.vpmt.mobiletv.network.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Stream data object
 * Created by neo on 5/18/2016.
 */
public class DataStream {
    @SerializedName("streams")
    private Streams mStreams;

    public Streams getStreams() {
        return mStreams;
    }

    public void setStreams(Streams streams) {
        mStreams = streams;
    }
}
