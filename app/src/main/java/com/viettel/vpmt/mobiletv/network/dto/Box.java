package com.viettel.vpmt.mobiletv.network.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Box DTO
 * Created by neo on 3/22/2016.
 */
public class Box {
    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("type")
    private Type mType;
    @SerializedName("content")
    private List<Content> mContents;

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

    public Type getType() {
        return mType;
    }

    public void setType(Type type) {
        mType = type;
    }

    public List<Content> getContents() {
        return mContents;
    }

    public void setContents(List<Content> contents) {
        mContents = contents;
    }

    public enum Type {
        @SerializedName("BANNER")
        BANNER,
        @SerializedName("VIDEO")
        VIDEO,
        @SerializedName("CHANNEL")
        CHANNEL,
        @SerializedName("CONTINUE")
        CONTINUE,
        @SerializedName("FOCUS")
        FOCUS,
        @SerializedName("FILM")
        FILM,
        @SerializedName("TVSHOW")
        TVSHOW,
    }
}
