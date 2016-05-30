package com.viettel.vpmt.mobiletv.screen.channeldetail.fragment;

import com.viettel.vpmt.mobiletv.base.BasePresenter;
import com.viettel.vpmt.mobiletv.network.dto.ChannelDetail;

/**
 * Presenter for Channel/TV detail fragment
 * Created by neo on 4/17/2016.
 */
public interface ChannelDetailFragmentPresenter extends BasePresenter {
    void getChannelDetail(String channelId);

    void getChannelStream(String channelId);

    void playProgram(String programId);

    void notifyChannel(String channelId);

    void postLikeChannel(boolean isLike, String channelId);

    ChannelDetail getChannelDetail();

    void playPresentProgram();
}
