package com.viettel.vpmt.mobiletv.screen.channeldetail.fragment;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.network.dto.ChannelDetail;
import com.viettel.vpmt.mobiletv.screen.channeldetail.fragment.relate.ChannelRelativeListFragment;
import com.viettel.vpmt.mobiletv.screen.channeldetail.fragment.schedule.ChannelScheduleFragment;
import com.viettel.vpmt.mobiletv.screen.filmdetail.fragment.comment.CommentFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Adapter for pagers on channel detail screen
 * Created by neo on 4/17/2016.
 */
public class ChannelFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private static final int PAGE_COUNT = 3;
    private Context mContext;
    private ChannelDetail mChannelDetail;
    private ChannelDetailFragmentPresenter mChannelDetailFragmentPresenter;


    public ChannelFragmentPagerAdapter(ChannelDetail channelDetail, FragmentManager fm, Context context,
                                       ChannelDetailFragmentPresenter channelDetailFragmentPresenter) {
        super(fm);
        mContext = context;
        mChannelDetail = channelDetail;
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
                return ChannelScheduleFragment.newInstance(mChannelDetail.getDateList(),
                        mChannelDetail.getSchedules(), mChannelDetail.getCurrentTime(),
                        mChannelDetailFragmentPresenter);
            case 1:
                return ChannelRelativeListFragment.newInstance(mChannelDetail.getContentRelated().getContents());
            case 2:
                return CommentFragment.newInstance();
            default:
                return ChannelScheduleFragment.newInstance(mChannelDetail.getDateList(),
                        mChannelDetail.getSchedules(), mChannelDetail.getCurrentTime(),
                        mChannelDetailFragmentPresenter);
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
}

