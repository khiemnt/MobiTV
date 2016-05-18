package com.viettel.vpmt.mobiletv.network.dto;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Schedule of channel
 * Created by neo on 5/17/2016.
 */
public class ChannelSchedule {
    public static final String DATE_FORMAT = "yyy-MM-dd HH:mm:ss";

    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("beginTime")
    private String mBeginTime;
    @SerializedName("isReplay")
    private boolean mIsReplay;
    @SerializedName("state")
    private State mState;

    private Calendar mCalendar;
    private String mExtractedBeginTime;
    private boolean isPlaying = false;

    public enum State {
        @SerializedName("PAST")
        PAST,
        @SerializedName("PRESENT")
        PRESENT,
        @SerializedName("FUTURE")
        FUTURE,
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

    public String getBeginTime() {
        return mBeginTime;
    }

    public void setBeginTime(String beginTime) {
        mBeginTime = beginTime;
    }

    public boolean isReplay() {
        return mIsReplay;
    }

    public void setReplay(boolean replay) {
        mIsReplay = replay;
    }

    public State getState() {
        return mState;
    }

    public void setState(State state) {
        mState = state;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public Calendar getFormattedBeginTime() {
        if (mCalendar == null) {
            try {
                SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.US);
                Date date = format.parse(mBeginTime);
                mCalendar = Calendar.getInstance();
                mCalendar.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return mCalendar;
    }

    public String getExtractedBeginTime() {
        if (mExtractedBeginTime == null) {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.US);
            mExtractedBeginTime = format.format(getFormattedBeginTime().getTime());
        }
        return mExtractedBeginTime;
    }
}
