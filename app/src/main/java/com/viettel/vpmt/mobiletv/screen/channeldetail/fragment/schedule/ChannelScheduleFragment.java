package com.viettel.vpmt.mobiletv.screen.channeldetail.fragment.schedule;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseFragment;
import com.viettel.vpmt.mobiletv.network.dto.ChannelSchedule;
import com.viettel.vpmt.mobiletv.screen.channeldetail.activity.ChannelDetailActivity;
import com.viettel.vpmt.mobiletv.screen.channeldetail.fragment.ChannelDetailFragmentPresenter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

import butterknife.Bind;

/**
 * List of programs in Channel schedule
 * Created by neo on 4/17/2016.
 */
public class ChannelScheduleFragment extends BaseFragment<ChannelSchedulePresenter, ChannelDetailActivity> implements ChannelScheduleView {
    @Bind(R.id.channel_schedule_rv)
    RecyclerView mRecyclerView;
    @Bind(R.id.channel_schedule_spinner)
    Spinner mDateListSpinner;
    private boolean mIsInitializingSpinner = true;
    private ChannelDetailFragmentPresenter mChannelDetailFragmentPresenter;
    private String mCurrentTime;

    public static ChannelScheduleFragment newInstance(List<String> dateList, List<ChannelSchedule> schedules,
                                                      String currentTime,
                                                      ChannelDetailFragmentPresenter channelDetailFragmentPresenter) {
        ChannelScheduleFragment fragment = new ChannelScheduleFragment();

        fragment.setSchedules(schedules);
        fragment.setDateList(dateList);
        fragment.setCurrentTime(currentTime);
        fragment.setChannelDetailFragmentPresenter(channelDetailFragmentPresenter);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_channel_schedule;
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }


    @Override
    public void onPrepareLayout() {
        getPresenter().setChannelDetailFragmentPresenter(mChannelDetailFragmentPresenter);
        getPresenter().setCurrentTime(mCurrentTime);

        // Load schedules
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        getPresenter().getData();

        // Load schedule date list
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,
                getPresenter().getDateList());
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDateListSpinner.setAdapter(spinnerArrayAdapter);
        mDateListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get channel program of date
                if (mIsInitializingSpinner) {
                    mIsInitializingSpinner = false;
                } else {
                    getPresenter().getChannelProgramSchedule(mChannelDetailFragmentPresenter.getChannelId(), position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    @Override
    public ChannelSchedulePresenterImpl onCreatePresenter() {
        return new ChannelSchedulePresenterImpl(this);
    }

    @Override
    public void loadDataToView(ChannelScheduleAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    public void setSchedules(List<ChannelSchedule> schedules) {
        getPresenter().setSchedules(schedules);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPresenter().onDestroy();
    }

    public void setDateList(List<String> dateList) {
        getPresenter().setDateList(dateList);
    }

    public void setChannelDetailFragmentPresenter(ChannelDetailFragmentPresenter channelDetailFragmentPresenter) {
        mChannelDetailFragmentPresenter = channelDetailFragmentPresenter;
    }

    public void setCurrentTime(String currentTime) {
        mCurrentTime = currentTime;
    }
}
