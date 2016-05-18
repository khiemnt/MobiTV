package com.viettel.vpmt.mobiletv.screen.channeldetail.fragment.schedule;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseFragment;
import com.viettel.vpmt.mobiletv.network.dto.ChannelSchedule;
import com.viettel.vpmt.mobiletv.screen.channeldetail.activity.ChannelActivityDetail;
import com.viettel.vpmt.mobiletv.screen.channeldetail.fragment.ChannelDetailFragmentPresenter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * List of programs in Channel schedule
 * Created by neo on 4/17/2016.
 */
public class ChannelScheduleFragment extends BaseFragment<ChannelSchedulePresenter, ChannelActivityDetail> implements ChannelScheduleView {
    @Bind(R.id.channel_schedule_rv)
    RecyclerView mRecyclerView;

    private List<ChannelSchedule> mSchedules = new ArrayList<>();
    private ChannelDetailFragmentPresenter mChannelDetailFragmentPresenter;


    public static ChannelScheduleFragment newInstance(List<ChannelSchedule> schedules,
                                                      ChannelDetailFragmentPresenter channelDetailFragmentPresenter) {
        ChannelScheduleFragment fragment = new ChannelScheduleFragment();

        // Set playing program
        for (ChannelSchedule schedule : schedules) {
            if (schedule.getState() == ChannelSchedule.State.PRESENT) {
                schedule.setPlaying(true);
                break;
            }
        }

        fragment.setSchedules(schedules);
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
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        getPresenter().setData(mSchedules);
        getPresenter().getData();
    }


    @Override
    public ChannelSchedulePresenterImpl onCreatePresenter() {
        return new ChannelSchedulePresenterImpl(this, mChannelDetailFragmentPresenter);
    }

    @Override
    public void loadDataToView(ChannelScheduleAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    public void setSchedules(List<ChannelSchedule> schedules) {
        mSchedules = schedules;
    }

    public void setChannelDetailFragmentPresenter(ChannelDetailFragmentPresenter channelDetailFragmentPresenter) {
        mChannelDetailFragmentPresenter = channelDetailFragmentPresenter;
    }
}
