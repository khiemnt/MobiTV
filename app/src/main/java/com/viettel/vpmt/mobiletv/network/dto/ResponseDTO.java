package com.viettel.vpmt.mobiletv.network.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Common response DTO
 * Created by neo on 12/15/14.
 */
public class ResponseDTO<DTO> implements Serializable {
    private int errorCode;

    @SerializedName("data")
    private DTO result;

    private String message;

    public ResponseDTO() {

    }

    public DTO getResult() {
        return result;
    }

    public void setResult(DTO result) {
        this.result = result;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

