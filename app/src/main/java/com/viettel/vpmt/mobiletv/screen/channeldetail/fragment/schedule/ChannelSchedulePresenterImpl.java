package com.viettel.vpmt.mobiletv.screen.channeldetail.fragment.schedule;

import com.viettel.vpmt.mobiletv.base.BasePresenterImpl;
import com.viettel.vpmt.mobiletv.network.dto.ChannelSchedule;
import com.viettel.vpmt.mobiletv.screen.channeldetail.fragment.ChannelDetailFragmentPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Presenter for Channel schedule
 * Created by ThanhTD on 3/29/2016.
 */
public class ChannelSchedulePresenterImpl extends BasePresenterImpl<ChannelScheduleView> implements ChannelSchedulePresenter {
    private final Timer mTimer = new Timer();
    private List<ChannelSchedule> mSchedules = new ArrayList<>();
    private ChannelScheduleAdapter mAdapter;
    private ChannelDetailFragmentPresenter mChannelDetailFragmentPresenter;

    public ChannelSchedulePresenterImpl(ChannelScheduleView view, ChannelDetailFragmentPresenter channelDetailFragmentPresenter) {
        super(view);
        mChannelDetailFragmentPresenter = channelDetailFragmentPresenter;
    }

    @Override
    public void getData() {
        mAdapter
                = new ChannelScheduleAdapter(mView.getViewContext(), mSchedules, mChannelDetailFragmentPresenter);
        mView.loadDataToView(mAdapter);
        startTimer();
    }

    @Override
    public void setData(List<ChannelSchedule> schedules) {
        mSchedules = schedules;
    }

    /**
     * Make a Timer to move current playing program
     */
    private void startTimer() {
        final int currentIdx = getPresentScheduleIndex();
        if (currentIdx < 0) {
            return;
        }

        if (currentIdx < mSchedules.size() - 1) {
            ChannelSchedule nextSchedule = mSchedules.get(currentIdx + 1);

            try {
                // Schedule to next program
                mTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // Mark next program as PRESENT
                        mSchedules.get(currentIdx).setState(ChannelSchedule.State.PAST);
                        mSchedules.get(currentIdx + 1).setState(ChannelSchedule.State.PRESENT);

                        mView.getViewContext().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                            }
                        });

                        // Start new Timer schedule
                        startTimer();
                    }
                }, nextSchedule.getFormattedBeginTime().getTime());
            } catch (IllegalArgumentException|IllegalStateException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Get index in list of current playing program
     */
    private int getPresentScheduleIndex() {
        int present = -1;
        for (int i = 0; i < mSchedules.size(); i++) {
            ChannelSchedule schedule = mSchedules.get(i);
            if (schedule.getState() == ChannelSchedule.State.PRESENT) {
                present = i;
            }
        }

        return present;
    }
}
