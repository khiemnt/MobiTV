package com.viettel.vpmt.mobiletv.network.dto;

/**
 * Streams object
 * Created by ThanhTD on 3/25/2016.
 */
public class Streams {
    private String message;
    private String urlStreaming;
    private int errorCode;
    private int subTypeId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrlStreaming() {
        return urlStreaming;
    }

    public void setUrlStreaming(String urlStreaming) {
        this.urlStreaming = urlStreaming;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getSubTypeId() {
        return subTypeId;
    }

    public void setSubTypeId(int subTypeId) {
        this.subTypeId = subTypeId;
    }
}
