package com.viettel.vpmt.mobiletv.network.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Video stream object
 * Created by ThanhTD on 3/30/2016.
 */
public class VideoStream {
    private String responseCode;
    private String message;
    private String streams;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStreams() {
        return streams;
    }

    public void setStreams(String streams) {
        this.streams = streams;
    }
}
