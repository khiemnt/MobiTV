package com.viettel.vpmt.mobiletv.screen.channeldetail.fragment.schedule;

import com.viettel.vpmt.mobiletv.base.BasePresenterImpl;
import com.viettel.vpmt.mobiletv.base.log.Logger;
import com.viettel.vpmt.mobiletv.common.pref.PrefManager;
import com.viettel.vpmt.mobiletv.common.util.NetworkUtils;
import com.viettel.vpmt.mobiletv.network.ServiceBuilder;
import com.viettel.vpmt.mobiletv.network.callback.BaseCallback;
import com.viettel.vpmt.mobiletv.network.dto.ChannelSchedule;
import com.viettel.vpmt.mobiletv.network.dto.ScheduleData;
import com.viettel.vpmt.mobiletv.screen.channeldetail.fragment.ChannelDetailFragmentPresenter;

import android.os.Handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

/**
 * Presenter for Channel schedule
 * Created by ThanhTD on 3/29/2016.
 */
public class ChannelSchedulePresenterImpl extends BasePresenterImpl<ChannelScheduleView>
        implements ChannelSchedulePresenter, ChannelScheduleAdapter.OnItemAction {
    private final Timer mTimer = new Timer();
    private List<ChannelSchedule> mSchedules = new ArrayList<>();
    private ChannelScheduleAdapter mAdapter;
    private ChannelDetailFragmentPresenter mChannelDetailFragmentPresenter;
    private List<String> mDateList = new ArrayList<>();
    private Calendar mCurrentTime;
    private boolean mIsAutoNext = true;
    private boolean mIsOnCurrentSchedules = true;

    public ChannelSchedulePresenterImpl(ChannelScheduleView view) {
        super(view);
    }

    @Override
    public void getData() {
        mAdapter = new ChannelScheduleAdapter(mView.getViewContext(), mSchedules, this);
        mView.loadDataToView(mAdapter);
        startTimer();
    }

    @Override
    public void getChannelProgramSchedule(String channelId, final int datePosition) {
        if (!NetworkUtils.checkNetwork(mView.getViewContext())) {
            return;
        }

        String header = PrefManager.getHeader(mView.getViewContext());

        ServiceBuilder.getService().getChannelProgram(header,
                channelId,
                mDateList.get(datePosition))
                .enqueue(new BaseCallback<ScheduleData>() {
                    @Override
                    public void onError(String errorCode, String errorMessage) {
                        mView.onRequestError(errorCode, errorMessage);
                    }

                    @Override
                    public void onResponse(ScheduleData data) {
                        mAdapter.setSchedules(data.getSchedules());
                        mAdapter.notifyDataSetChanged();

                        // If current date isn't selected date
                        try {
                            Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(mDateList.get(datePosition));
                            Calendar selectedCalendar = Calendar.getInstance();
                            selectedCalendar.setTime(date);

                            if (selectedCalendar.get(Calendar.YEAR) == mCurrentTime.get(Calendar.YEAR)
                                    && selectedCalendar.get(Calendar.MONTH) == mCurrentTime.get(Calendar.MONTH)
                                    && selectedCalendar.get(Calendar.DAY_OF_MONTH) == mCurrentTime.get(Calendar.DAY_OF_MONTH)) {
                                mIsOnCurrentSchedules = true;
                                startTimer();
                            } else {
                                mIsOnCurrentSchedules = false;
                                Logger.d("Pause move next program timer");
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * Make a Timer to move current playing program
     */
    private void startTimer() {
        if (!mIsAutoNext || !mIsOnCurrentSchedules) {
            return;
        }

        final int currentIdx = mAdapter.getPresentPosition();
        if (currentIdx < 0 || currentIdx >= mSchedules.size() - 1) {
            return;
        }

        final ChannelSchedule nextSchedule = mSchedules.get(currentIdx + 1);

        long delay = nextSchedule.getFormattedBeginTime().getTime().getTime()
                - mCurrentTime.getTimeInMillis();
        Logger.d("delay " + delay);

        // Move next program
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mSchedules == null || mCurrentTime == null) {
                    return;
                }
                if (mView == null || mView.getViewContext() == null) {
                    return;
                }

                if (!mIsAutoNext || !mIsOnCurrentSchedules) {
                    return;
                }

                // Notify to view
                mView.getViewContext().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.moveNextProgram();
                    }
                });

                // Start new Timer schedule
                startTimer();
            }
        }, delay);

    }

    @Override
    public List<String> getDateList() {
        return mDateList;
    }

    @Override
    public void setSchedules(List<ChannelSchedule> schedules) {
        mSchedules = schedules;
    }

    @Override
    public void setDateList(List<String> dateList) {
        mDateList = dateList;
    }

    @Override
    public void setChannelDetailFragmentPresenter(ChannelDetailFragmentPresenter channelDetailFragmentPresenter) {
        mChannelDetailFragmentPresenter = channelDetailFragmentPresenter;
    }

    @Override
    public void setCurrentTime(String currentTime) {
        mCurrentTime = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(ChannelSchedule.DATE_FORMAT, Locale.US);
        try {
            Date date = format.parse(currentTime);
            mCurrentTime.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        mTimer.cancel();
    }

    public Handler mHandler = new Handler();

    @Override
    public void playProgram(String programId) {
        mChannelDetailFragmentPresenter.playProgram(programId);
        mIsAutoNext = false;
    }

    @Override
    public void playPresentProgram() {
        mChannelDetailFragmentPresenter.playPresentProgram();
    }
}
