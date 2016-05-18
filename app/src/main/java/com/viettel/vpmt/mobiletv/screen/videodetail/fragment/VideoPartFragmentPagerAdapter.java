package com.viettel.vpmt.mobiletv.screen.videodetail.fragment;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.screen.filmdetail.fragment.comment.CommentFragment;
import com.viettel.vpmt.mobiletv.screen.videodetail.fragment.part.VideoPartsFragment;
import com.viettel.vpmt.mobiletv.screen.videodetail.fragment.relate.VideoRelativeListFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * ViewPager adapter for parts of video
 * Created by ThanhTD on 3/29/2016.
 */
public class VideoPartFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private static final int PAGE_COUNT = 2;
    private Context mContext;
    private List<Content> mVideos;
    private int mPositionActive = 0;
    private String mVideoId;
    private boolean mHasParts = false;

    /**
     * Pass positionActive = -1 if has no parts
     * Greater or equal 0 if has parts
     */
    public VideoPartFragmentPagerAdapter(String videoId, int positionActive, List<Content> videos, FragmentManager fm, Context context) {
        super(fm);
        mVideoId = videoId;
        mPositionActive = positionActive;
        mContext = context;
        mVideos = videos;
        mHasParts = positionActive >= 0;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (mHasParts) {
                    return VideoPartsFragment.newInstance(mVideos, mVideoId, mPositionActive);
                } else {
                    return VideoRelativeListFragment.newInstance(mVideos);
                }
            case 1:
                return CommentFragment.newInstance();
            default:
                return VideoPartsFragment.newInstance(mVideos, mVideoId, mPositionActive);
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
                if (mHasParts) {
                    return mContext.getString(R.string.tab_danh_sach) + " (" + mVideos.size() + ")";
                } else {
                    return mContext.getString(R.string.tab_lien_quan);
                }
            case 1:
                return mContext.getString(R.string.tab_binh_luan);
            default:
                return mContext.getString(R.string.tab_danh_sach) + " (" + mVideos.size() + ")";
        }
    }

    public List<Content> getVideos() {
        return mVideos;
    }

    public void setVideos(List<Content> videos) {
        mVideos = videos;
    }

    public String getVideoId() {
        return mVideoId;
    }

    public void setVideoId(String videoId) {
        mVideoId = videoId;
    }
}
