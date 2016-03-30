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
    private Type mType;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("duration")
    private float duration;
    @SerializedName("actors")
    private String actors;
    @SerializedName("country")
    private String country;
    @SerializedName("tag")
    private String tag;
    @SerializedName("coverImage")
    private String mCoverImage;
    @SerializedName("isFavourite")
    private boolean isFavourite;
    @SerializedName("item_id")
    private String mItemId;
    @SerializedName("avatarImage")
    private String mAvatarImage;
    @SerializedName("progress")
    private int mProgress;
    @SerializedName("isLive")
    private boolean mIsLive;
    @SerializedName("shortDescription")
    private String mShortDesc;
    @SerializedName("likeCount")
    private Integer likeCount;
    @SerializedName("playCount")
    private Integer playCount;

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

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    public int getProgress() {
        return mProgress;
    }

    public void setProgress(int progress) {
        mProgress = progress;
    }

    public boolean isLive() {
        return mIsLive;
    }

    public void setIsLive(boolean isLive) {
        mIsLive = isLive;
    }

    public String getShortDesc() {
        return mShortDesc;
    }

    public void setShortDesc(String shortDesc) {
        mShortDesc = shortDesc;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Integer playCount) {
        this.playCount = playCount;
    }

    public enum Type {
        @SerializedName("FILM")
        FILM,
        @SerializedName("LIVETV")
        LIVETV,
        @SerializedName("VOD")
        VOD,
        @SerializedName("TVSHOW")
        TVSHOW,
    }
}
