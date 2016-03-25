package com.viettel.vpmt.mobiletv.screen.videodetail.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.VideoView;
import butterknife.Bind;
import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseActivity;
import com.viettel.vpmt.mobiletv.common.util.CustomTextViewExpandable;
import com.viettel.vpmt.mobiletv.network.dto.VideoDetail;
import com.viettel.vpmt.mobiletv.screen.videodetail.fragment.adapter.SampleFragmentPagerAdapter;
import com.viewpagerindicator.TitlePageIndicator;

/**
 * Created by ThanhTD on 3/22/2016.
 */
public class VideoDetailActivity extends BaseActivity<VideoDetailPresenter> implements VideoDetailView
{
    @Bind(R.id.video_detail_layout_video)
    VideoView videoView;
    @Bind(R.id.fragment_video_detail_tvTitle)
    TextView tvTitle;
    @Bind(R.id.fragment_video_detail_tvFullDes)
    TextView tvFullDes;
    @Bind(R.id.fragment_video_detail_tvTag)
    TextView tvTag;
    @Bind(R.id.video_detail_thumb_up_down)
    CheckBox tvFavorite;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.sliding_tabs)
    TitlePageIndicator tabLayout;

    SampleFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getPresenter().getDetailVideo();
        CustomTextViewExpandable.makeTextViewResizable(tvFullDes, 3, getString(R.string.view_more), false);
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
    public void doLoadToView(VideoDetail videoDetail)
    {
        videoView.setVideoURI(Uri.parse(videoDetail.getStreams().getUrlStreaming()));
        tvTitle.setText(videoDetail.getVideoDetail().getName());
        tvFullDes.setText(videoDetail.getVideoDetail().getDescription());
        tvTag.setText("Tag: " + videoDetail.getVideoDetail().getTag());
        tvFavorite.setChecked(videoDetail.getVideoDetail().isFavourite());
        if (videoDetail.getVideoRelated() != null)
        {
            adapter = new SampleFragmentPagerAdapter(videoDetail.getVideoRelated().getContents(), getSupportFragmentManager(), this);
            viewPager.setAdapter(adapter);
        }
        tabLayout.setViewPager(viewPager);
    }
}
