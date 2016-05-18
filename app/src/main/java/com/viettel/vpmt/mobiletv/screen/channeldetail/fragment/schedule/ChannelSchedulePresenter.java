package com.viettel.vpmt.mobiletv.screen.channeldetail.fragment.schedule;

import com.viettel.vpmt.mobiletv.base.BasePresenter;
import com.viettel.vpmt.mobiletv.network.dto.ChannelSchedule;
import com.viettel.vpmt.mobiletv.network.dto.PartOfFilm;

import java.util.List;

/**
 * Presenter for Channel schedule
 * Created by ThanhTD on 3/29/2016.
 */
public interface ChannelSchedulePresenter extends BasePresenter {
    void getData();

    void setData(List<ChannelSchedule> schedules);
}
