package com.viettel.vpmt.mobiletv.screen.channeldetail.fragment.schedule;

import com.viettel.vpmt.mobiletv.base.BaseView;

/**
 * View for Channel schedule
 * Created by neo on 5/17/2016.
 */
public interface ChannelScheduleView extends BaseView<ChannelSchedulePresenter> {
    void loadDataToView(ChannelScheduleAdapter viewPartFilmAdapter);
}
