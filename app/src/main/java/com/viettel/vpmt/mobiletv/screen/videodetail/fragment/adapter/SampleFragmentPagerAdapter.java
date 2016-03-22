package com.viettel.vpmt.mobiletv.screen.videodetail.fragment.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.screen.videodetail.fragment.PageFragment;

/**
 * Created by ThanhTD on 3/22/2016.
 */
public class SampleFragmentPagerAdapter extends FragmentPagerAdapter
{
    final int PAGE_COUNT = 2;
    private Context context;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context)
    {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position)
    {
        return PageFragment.newInstance(position + 1);
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
}