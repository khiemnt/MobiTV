package com.viettel.vpmt.mobiletv.screen.videodetail.fragment.adapter;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.screen.film.fragment.CommentFragment;
import com.viettel.vpmt.mobiletv.screen.videodetail.fragment.ListPartVideoFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by ThanhTD on 3/29/2016.
 */
public class VideoPartFragmentPagerAdapter extends FragmentStatePagerAdapter {
    final int PAGE_COUNT = 2;
    private Context context;
    private List<Content> videos;
    private int positionActive = 0;
    private float videoId;

    public VideoPartFragmentPagerAdapter(Float videoId, int positionActive, List<Content> videos, FragmentManager fm, Context context) {
        super(fm);
        this.videoId = videoId;
        this.positionActive = positionActive;
        this.context = context;
        this.videos = videos;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ListPartVideoFragment.newInstance(videos, videoId, positionActive);
            case 1:
                return CommentFragment.newInstance();
            default:
                return ListPartVideoFragment.newInstance(videos, videoId, positionActive);
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
                return context.getString(R.string.tab_danh_sach) + " (" + videos.size() + ")";
            case 1:
                return context.getString(R.string.tab_binh_luan);
            default:
                return context.getString(R.string.tab_danh_sach) + " (" + videos.size() + ")";
        }
    }

    public List<Content> getVideos() {
        return videos;
    }

    public void setVideos(List<Content> videos) {
        this.videos = videos;
    }

    public float getVideoId() {
        return videoId;
    }

    public void setVideoId(float videoId) {
        this.videoId = videoId;
    }
}
