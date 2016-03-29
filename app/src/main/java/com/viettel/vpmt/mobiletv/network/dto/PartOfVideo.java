package com.viettel.vpmt.mobiletv.network.dto;

/**
 * Created by ThanhTD on 3/29/2016.
 */
public class PartOfVideo {
    private float id;
    private String name;
    private String urlAvatar;

    public PartOfVideo(Float id, String name, String urlAvatar) {
        this.id = id;
        this.name = name;
        this.urlAvatar = urlAvatar;
    }

    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }
}
