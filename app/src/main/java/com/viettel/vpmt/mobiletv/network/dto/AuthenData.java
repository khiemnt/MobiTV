package com.viettel.vpmt.mobiletv.network.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Authen Data
 * Created by neo on 3/29/2016.
 */
public class AuthenData {
    @SerializedName("accessToken")
    private String mAccessToken;
    @SerializedName("refressToken")
    private String mRefressToken;
    @SerializedName("msisdn")
    private String mMsisdn;
    @SerializedName("expiredTime")
    private long mExpiredTime;

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }

    public String getRefressToken() {
        return mRefressToken;
    }

    public void setRefressToken(String refressToken) {
        mRefressToken = refressToken;
    }

    public String getMsisdn() {
        return mMsisdn;
    }

    public void setMsisdn(String msisdn) {
        mMsisdn = msisdn;
    }

    public long getExpiredTime() {
        return mExpiredTime;
    }

    public void setExpiredTime(long expiredTime) {
        mExpiredTime = expiredTime;
    }
}
