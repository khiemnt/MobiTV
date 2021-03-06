package com.viettel.vpmt.mobiletv.network.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ThanhTD on 3/28/2016.
 */
public class ContentRelated {
    private String id;
    private String name;
    private Box.Type type;
    private int count;
    @SerializedName("content")
    private List<Content> contents;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Box.Type getType() {
        return type;
    }

    public void setType(Box.Type type) {
        this.type = type;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
