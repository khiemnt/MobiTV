package com.viettel.vpmt.mobiletv.screen.videodetail;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import butterknife.Bind;
import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseActivity;
import com.viettel.vpmt.mobiletv.common.util.CustomTextViewExpandable;
import com.viettel.vpmt.mobiletv.screen.videodetail.fragment.adapter.SampleFragmentPagerAdapter;

/**
 * Created by ThanhTD on 3/22/2016.
 */
public class VideoDetailActivity extends BaseActivity<VideoDetailPresenter> implements VideoDetailView
{
    @Bind(R.id.fragment_video_detail_tvFavorite)
    TextView tvFavorite;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.sliding_tabs)
    TabLayout tabLayout;
    @Bind(R.id.fragment_video_detail_tvFullDes)
    TextView tvFullDes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(), this));
        tabLayout.setupWithViewPager(viewPager);
        CustomTextViewExpandable.makeTextViewResizable(tvFullDes, 3, getString(R.string.view_more), false);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_video_detail;
    }

    @Override
    public VideoDetailPresenter onCreatePresenter()
    {
        return new VideoDetailPresenterImpl(this);
    }

}
