package com.viettel.vpmt.mobiletv.screen.channeldetail.fragment.relate;

import com.viettel.vpmt.mobiletv.base.BasePresenter;
import com.viettel.vpmt.mobiletv.network.dto.Content;

import java.util.List;

/**
 * Presenter for channel relative
 * Created by neo on 5/17/2016.
 */
public interface ChannelRelativePresenter extends BasePresenter {
    void setData(List<Content> videos);

    void getData();
}
