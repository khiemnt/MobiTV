package com.viettel.vpmt.mobiletv.screen.channeldetail.fragment.schedule;

import com.viettel.vpmt.mobiletv.base.BasePresenter;
import com.viettel.vpmt.mobiletv.network.dto.ChannelSchedule;
import com.viettel.vpmt.mobiletv.network.dto.PartOfFilm;
import com.viettel.vpmt.mobiletv.screen.channeldetail.fragment.ChannelDetailFragmentPresenter;

import android.os.Handler;

import java.util.List;

/**
 * Presenter for Channel schedule
 * Created by ThanhTD on 3/29/2016.
 */
public interface ChannelSchedulePresenter extends BasePresenter {
    void getData();

    void getChannelProgramSchedule(String channelId, int datePosition);

    List<String> getDateList();

    void setSchedules(List<ChannelSchedule> schedules);

    void setDateList(List<String> dateList);

    void setChannelDetailFragmentPresenter(ChannelDetailFragmentPresenter channelDetailFragmentPresenter);

    void setCurrentTime(String currentTime);

    void onDestroy();
}
