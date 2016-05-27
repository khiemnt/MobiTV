package com.viettel.vpmt.mobiletv.screen.channeldetail.fragment;

import com.viettel.vpmt.mobiletv.base.BasePresenter;

/**
 * Presenter for Channel/TV detail fragment
 * Created by neo on 4/17/2016.
 */
public interface ChannelDetailFragmentPresenter extends BasePresenter {
    void getChannelDetail(String channelId);

    void getChannelStream(String channelId);

    void playProgram(String programId);

    void notifyChannel(String channelId);

    String getChannelId();

    void playPresentProgram();
}
