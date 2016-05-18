package com.viettel.vpmt.mobiletv.network.dto;

/**
 * Video part object
 * Created by ThanhTD on 3/29/2016.
 */
public class PartOfVideo {
    private String mId;
    private String mName;
    private String mUrlAvatar;

    public PartOfVideo(String id, String name, String urlAvatar) {
        mId = id;
        mName = name;
        mUrlAvatar = urlAvatar;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUrlAvatar() {
        return mUrlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        mUrlAvatar = urlAvatar;
    }
}
