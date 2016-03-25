package com.viettel.vpmt.mobiletv.screen.videodetail;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseActivity;
import com.viettel.vpmt.mobiletv.common.util.CustomTextViewExpandable;
import com.viettel.vpmt.mobiletv.screen.videodetail.fragment.CommentFragment;
import com.viettel.vpmt.mobiletv.screen.videodetail.fragment.VideoRelativeFragment;
import com.viettel.vpmt.mobiletv.screen.videodetail.fragment.adapter.SampleFragmentPagerAdapter;
import com.viewpagerindicator.TitlePageIndicator;

/**
 * Created by ThanhTD on 3/22/2016.
 */
public class VideoDetailActivity extends BaseActivity<VideoDetailPresenter> implements VideoDetailView
{
    @Bind(R.id.video_detail_thumb_up_down)
    TextView tvFavorite;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.sliding_tabs)
    TitlePageIndicator tabLayout;
    @Bind(R.id.fragment_video_detail_tvFullDes)
    TextView tvFullDes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getPresenter().initImageSlides();
        CustomTextViewExpandable.makeTextViewResizable(tvFullDes, 3, getString(R.string.view_more), false);

        SampleFragmentPagerAdapter adapter = new SampleFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        //Setting tabs from adpater
        tabLayout.setViewPager(viewPager);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.video_detail_layout;
    }

    @Override
    public VideoDetailPresenter onCreatePresenter()
    {
        return new VideoDetailPresenterImpl(this);
    }

    @Override
    public void doLoadVideo(String url)
    {
//        slideImage.setAdapter(imageSlideAdapter);
    }
}
