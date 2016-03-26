package com.viettel.vpmt.mobiletv.network.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Content DTO
 * Created by neo on 3/22/2016.
 */
public class Content {
    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("type")
    private String mType;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("coverImage")
    private String mCoverImage;
    @SerializedName("item_id")
    private String mItemId;
    @SerializedName("avatarImage")
    private String mAvatarImage;
    @SerializedName("progress")
    private int mProgress;
    @SerializedName("shortDescription")
    private String mShortDesc;
    @SerializedName("isLive")
    private boolean mIsLive;

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

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getCoverImage() {
        return mCoverImage;
    }

    public void setCoverImage(String coverImage) {
        mCoverImage = coverImage;
    }

    public String getItemId() {
        return mItemId;
    }

    public void setItemId(String itemId) {
        mItemId = itemId;
    }

    public String getAvatarImage() {
        return mAvatarImage;
    }

    public void setAvatarImage(String avatarImage) {
        mAvatarImage = avatarImage;
    }

    public int getProgress() {
        return mProgress;
    }

    public void setProgress(int progress) {
        mProgress = progress;
    }

    public String getShortDesc() {
        return mShortDesc;
    }

    public void setShortDesc(String shortDesc) {
        mShortDesc = shortDesc;
    }

    public boolean isLive() {
        return mIsLive;
    }

    public void setIsLive(boolean isLive) {
        mIsLive = isLive;
    }

    public enum  Type {
        @SerializedName("FILM")
        FILM,
        @SerializedName("LIVETV")
        LIVETV,
        @SerializedName("VOD")
        VOD,
    }
}
