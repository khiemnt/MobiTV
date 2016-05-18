package com.viettel.vpmt.mobiletv.screen.channeldetail.fragment;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.network.dto.ChannelSchedule;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.screen.channeldetail.fragment.ChannelDetailFragmentPresenter;
import com.viettel.vpmt.mobiletv.screen.channeldetail.fragment.relate.ChannelRelativeListFragment;
import com.viettel.vpmt.mobiletv.screen.channeldetail.fragment.schedule.ChannelScheduleFragment;
import com.viettel.vpmt.mobiletv.screen.filmdetail.fragment.comment.CommentFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Adapter for pagers on channel detail screen
 * Created by neo on 4/17/2016.
 */
public class ChannelFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private static final int PAGE_COUNT = 3;
    private Context mContext;
    private List<Content> mContents;
    private List<ChannelSchedule> mSchedules;
    private String mChannelId;
    private ChannelDetailFragmentPresenter mChannelDetailFragmentPresenter;


    public ChannelFragmentPagerAdapter(String channelId, List<ChannelSchedule> schedules,
                                       List<Content> contents, FragmentManager fm, Context context,
                                       ChannelDetailFragmentPresenter channelDetailFragmentPresenter) {
        super(fm);
        mContext = context;
        mSchedules = schedules;
        mContents = contents;
        mChannelId = channelId;
        mChannelDetailFragmentPresenter = channelDetailFragmentPresenter;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ChannelScheduleFragment.newInstance(mSchedules, mChannelDetailFragmentPresenter);
            case 1:
                return ChannelRelativeListFragment.newInstance(mContents);
            case 2:
                return CommentFragment.newInstance();
            default:
                return ChannelScheduleFragment.newInstance(mSchedules, mChannelDetailFragmentPresenter);
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_UNCHANGED;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return mContext.getString(R.string.tab_schedule);
            case 1:
                return mContext.getString(R.string.tab_lien_quan);
            case 2:
                return mContext.getString(R.string.tab_binh_luan);
            default:
                return mContext.getString(R.string.tab_schedule);
        }
    }

    public List<Content> getContents() {
        return mContents;
    }

    public void setContents(List<Content> contents) {
        mContents = contents;
    }

    public String getChannelId() {
        return mChannelId;
    }

    public void setChannelId(String channelId) {
        mChannelId = channelId;
    }
}
