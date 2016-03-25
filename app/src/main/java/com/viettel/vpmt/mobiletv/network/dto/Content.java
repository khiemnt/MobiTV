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

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
    }

    public boolean isFavourite()
    {
        return isFavourite;
    }

    public void setIsFavourite(boolean isFavourite)
    {
        this.isFavourite = isFavourite;
    }

    public interface Type {
        String FILM = "FILM";
        String CHANNEL = "CHANNEL";
        String VIDEO = "VIDEO";
    }
}
