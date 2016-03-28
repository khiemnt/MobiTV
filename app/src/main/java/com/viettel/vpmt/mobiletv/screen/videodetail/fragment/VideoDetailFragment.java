package com.viettel.vpmt.mobiletv.screen.videodetail.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import butterknife.Bind;
import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseFragment;
import com.viettel.vpmt.mobiletv.common.util.CustomTextViewExpandable;
import com.viettel.vpmt.mobiletv.network.dto.VideoDetail;
import com.viettel.vpmt.mobiletv.screen.videodetail.activity.VideoDetailActivity;
import com.viettel.vpmt.mobiletv.screen.videodetail.fragment.adapter.SampleFragmentPagerAdapter;
import com.viettel.vpmt.mobiletv.screen.videodetail.utils.WrapContentHeightViewPager;
import com.viewpagerindicator.TitlePageIndicator;

/**
 * Created by ThanhTD on 3/26/2016.
 */
public class VideoDetailFragment extends BaseFragment<VideoDetailFragmentPresenter, VideoDetailActivity> implements VideoDetailFragmentView
{
    @Bind(R.id.common_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.view_transparent)
    View transparent;
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
    WrapContentHeightViewPager viewPager;
    @Bind(R.id.sliding_tabs)
    TitlePageIndicator tabLayout;
    SampleFragmentPagerAdapter adapter;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_video_detail;
    }

    @Override
    public void showProgress()
    {
        if (mProgressBar != null)
        {
            mProgressBar.setVisibility(View.VISIBLE);
            transparent.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress()
    {
        if (mProgressBar != null)
        {
            mProgressBar.setVisibility(View.GONE);
            transparent.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPrepareLayout()
    {
        Bundle bundle = getArguments();
        float videoId = bundle.getFloat("videoId");
        getPresenter().getDetailVideo(videoId);
        CustomTextViewExpandable.makeTextViewResizable(tvFullDes, 3, getString(R.string.view_more), false);
    }

    @Override
    public VideoDetailFragmentPresenter onCreatePresenter()
    {
        return new VideoDetailFragmentPresenterImpl(this);
    }

    @Override
    public void doLoadToView(VideoDetail videoDetail)
    {
        videoView.setVideoURI(Uri.parse(videoDetail.getStreams().getUrlStreaming()));
        MediaController mediaController= new MediaController(getActivity());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse("https://ia700401.us.archive.org/19/items/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4"));
        videoView.start();
        tvTitle.setText(videoDetail.getVideoDetail().getName());
        tvFullDes.setText(videoDetail.getVideoDetail().getDescription());
        tvTag.setText("Tags: " + videoDetail.getVideoDetail().getTag());
        tvFavorite.setChecked(videoDetail.getVideoDetail().isFavourite());
        if (videoDetail.getVideoRelated() != null)
        {
            adapter = new SampleFragmentPagerAdapter(videoDetail.getVideoRelated().getContents(), getActivity().getSupportFragmentManager(), getActivity());
            viewPager.setAdapter(adapter);
        }
        adapter.notifyDataSetChanged();
        tabLayout.setViewPager(viewPager);
        hideProgress();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }
}
