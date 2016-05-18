package com.viettel.vpmt.mobiletv.screen.channeldetail.fragment.relate;

import com.viettel.vpmt.mobiletv.base.BaseView;
import com.viettel.vpmt.mobiletv.screen.home.adapter.ChannelAdapter;

/**
 * View for channel relative
 * Created by neo on 5/17/2016.
 */
public interface ChannelRelativeView extends BaseView<ChannelRelativePresenter> {
    void loadRelativeChannel(ChannelAdapter channelAdapter);
}
