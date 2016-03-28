package com.viettel.vpmt.mobiletv.screen.film.fragment.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.screen.film.fragment.CommentFragment;
import com.viettel.vpmt.mobiletv.screen.film.fragment.FilmRelativeListFragment;

import java.util.List;

/**
 * Created by ThanhTD on 3/22/2016.
 */
public class FilmFragmentPagerAdapter extends FragmentStatePagerAdapter
{
    final int PAGE_COUNT = 2;
    private Context context;
    private List<Content> videos;

    public FilmFragmentPagerAdapter(List<Content> videos, FragmentManager fm, Context context)
    {
        super(fm);
        this.context = context;
        this.videos = videos;
    }

    @Override
    public int getCount()
    {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                return FilmRelativeListFragment.newInstance(videos);
            case 1:
                return CommentFragment.newInstance();
            default:
                return FilmRelativeListFragment.newInstance(videos);
        }
    }

    @Override
    public int getItemPosition(Object object){
        return POSITION_UNCHANGED;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        // Generate title based on item position
        switch (position)
        {
            case 0:
                return context.getString(R.string.tab_lien_quan);
            case 1:
                return context.getString(R.string.tab_binh_luan);
            default:
                return context.getString(R.string.tab_lien_quan);
        }
    }

    public List<Content> getVideos()
    {
        return videos;
    }

    public void setVideos(List<Content> videos)
    {
        this.videos = videos;
    }
}