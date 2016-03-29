package com.viettel.vpmt.mobiletv.screen.film.fragment.adapter;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.network.dto.PartOfFilm;
import com.viettel.vpmt.mobiletv.screen.film.fragment.CommentFragment;
import com.viettel.vpmt.mobiletv.screen.film.fragment.FilmRelativeListFragment;
import com.viettel.vpmt.mobiletv.screen.film.fragment.ListPartFilmFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by ThanhTD on 3/29/2016.
 */
public class FilmPartFragmentPagerAdapter extends FragmentStatePagerAdapter {
    final int PAGE_COUNT = 3;
    private Context context;
    private List<Content> videos;
    List<PartOfFilm> parts;
    private int positionActive = 0;
    private float filmId;

    public FilmPartFragmentPagerAdapter(Float filmId, int positionActive, List<PartOfFilm> parts, List<Content> videos, FragmentManager fm, Context context) {
        super(fm);
        this.filmId = filmId;
        this.positionActive = positionActive;
        this.context = context;
        this.videos = videos;
        this.parts = parts;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ListPartFilmFragment.newInstance(parts, filmId, positionActive);
            case 1:
                return FilmRelativeListFragment.newInstance(videos);
            case 2:
                return CommentFragment.newInstance();
            default:
                return ListPartFilmFragment.newInstance(parts, filmId, positionActive);
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
                return context.getString(R.string.tab_danh_sach) + " (" + parts.size() + ")";
            case 1:
                return context.getString(R.string.tab_lien_quan);
            case 2:
                return context.getString(R.string.tab_binh_luan);
            default:
                return context.getString(R.string.tab_danh_sach) + " (" + parts.size() + ")";
        }
    }

    public List<Content> getVideos() {
        return videos;
    }

    public void setVideos(List<Content> videos) {
        this.videos = videos;
    }

    public float getFilmId() {
        return filmId;
    }

    public void setFilmId(float filmId) {
        this.filmId = filmId;
    }
}
