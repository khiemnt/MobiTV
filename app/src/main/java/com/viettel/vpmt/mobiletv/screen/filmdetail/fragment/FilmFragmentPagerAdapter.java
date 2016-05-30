package com.viettel.vpmt.mobiletv.screen.filmdetail.fragment;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.network.dto.PartOfFilm;
import com.viettel.vpmt.mobiletv.screen.filmdetail.fragment.comment.CommentFragment;
import com.viettel.vpmt.mobiletv.screen.filmdetail.fragment.relate.FilmRelativeListFragment;
import com.viettel.vpmt.mobiletv.screen.filmdetail.fragment.part.FilmPartsFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * ViewPager adapter for mParts of film
 * Created by ThanhTD on 3/29/2016.
 */
public class FilmFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private int mPageCount = 2;
    private Context mContext;
    private List<Content> mVideos;
    private List<PartOfFilm> mParts;
    private int mPositionActive = 0;
    private String mFilmId;
    private boolean mHasParts;

    public FilmFragmentPagerAdapter(String filmId, int positionActive, List<PartOfFilm> parts, List<Content> videos, FragmentManager fm, Context context) {
        super(fm);
        mFilmId = filmId;
        mPositionActive = positionActive;
        mContext = context;
        mVideos = videos;
        mParts = parts;
        mPageCount = parts == null ? 2 : 3;
        mHasParts = parts != null;
    }

    @Override
    public int getCount() {
        return mPageCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (mHasParts) {
                    return FilmPartsFragment.newInstance(mParts, mFilmId, mPositionActive);
                } else {
                    return FilmRelativeListFragment.newInstance(mVideos);
                }
            case 1:
                if (mHasParts) {
                    return FilmRelativeListFragment.newInstance(mVideos);
                } else {
                    return CommentFragment.newInstance();
                }
            case 2:
                return CommentFragment.newInstance();
            default:
                return FilmPartsFragment.newInstance(mParts, mFilmId, mPositionActive);
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
                    return mContext.getString(R.string.tab_danh_sach) + " (" + mParts.size() + ")";
                } else {
                    return mContext.getString(R.string.tab_lien_quan);
                }
            case 1:
                if (mHasParts) {
                    return mContext.getString(R.string.tab_lien_quan);
                } else {
                    return mContext.getString(R.string.tab_binh_luan);
                }
            case 2:
                return mContext.getString(R.string.tab_binh_luan);
            default:
                return mContext.getString(R.string.tab_danh_sach) + " (" + mParts.size() + ")";
        }
    }

    public List<Content> getVideos() {
        return mVideos;
    }

    public void setVideos(List<Content> videos) {
        mVideos = videos;
    }
}
