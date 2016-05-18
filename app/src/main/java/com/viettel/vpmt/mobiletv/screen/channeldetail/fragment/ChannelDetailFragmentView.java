package com.viettel.vpmt.mobiletv.screen.channeldetail.fragment;

import com.viettel.vpmt.mobiletv.base.BaseView;
import com.viettel.vpmt.mobiletv.network.dto.ChannelDetail;
import com.viettel.vpmt.mobiletv.network.dto.DataStream;
import com.viettel.vpmt.mobiletv.network.dto.VideoStream;

/**
 * Presenter for Channel/TV detail fragment
 * Created by neo on 4/17/2016.
 */
public interface ChannelDetailFragmentView extends BaseView<ChannelDetailFragmentPresenter> {
    void loadToView(ChannelDetail channelDetail);

    void doLoadChannelStream(DataStream videoStream);

    void loadProgram(String programStreamUrl);
}
