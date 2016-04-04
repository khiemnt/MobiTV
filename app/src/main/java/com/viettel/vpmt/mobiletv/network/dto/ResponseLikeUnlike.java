package com.viettel.vpmt.mobiletv.network.dto;

/**
 * Created by ThanhTD on 4/4/2016.
 */
public class ResponseLikeUnlike {
    private String message;
    private LikeUnlike data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LikeUnlike getData() {
        return data;
    }

    public void setData(LikeUnlike data) {
        this.data = data;
    }
}
