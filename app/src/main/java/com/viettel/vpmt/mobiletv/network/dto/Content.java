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
    private float mDuration;
    @SerializedName("actors")
    private String mActors;
    @SerializedName("country")
    private String mCountry;
    @SerializedName("tag")
    private String mTag;
    @SerializedName("coverImage")
    private String mCoverImage;
    @SerializedName("logoImage")
    private String mLogoImage;
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
    private Integer mPlayCount;
    @SerializedName("link")
    private String mLink;

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
        return mDuration;
    }

    public void setDuration(float duration) {
        this.mDuration = duration;
    }

    public String getActors() {
        return mActors;
    }

    public void setActors(String actors) {
        this.mActors = actors;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        this.mCountry = country;
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String tag) {
        this.mTag = tag;
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
        return mPlayCount;
    }

    public void setPlayCount(Integer playCount) {
        this.mPlayCount = playCount;
    }

    public String getLogoImage() {
        return mLogoImage;
    }

    public void setLogoImage(String logoImage) {
        mLogoImage = logoImage;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
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
