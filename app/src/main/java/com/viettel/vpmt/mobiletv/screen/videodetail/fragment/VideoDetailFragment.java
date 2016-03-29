package com.viettel.vpmt.mobiletv.screen.videodetail.fragment;

import com.google.android.exoplayer.util.Util;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.log.Logger;
import com.viettel.vpmt.mobiletv.common.util.CustomTextViewExpandable;
import com.viettel.vpmt.mobiletv.common.util.StringUtils;
import com.viettel.vpmt.mobiletv.media.PlayerFragment;
import com.viettel.vpmt.mobiletv.network.dto.VideoDetail;
import com.viettel.vpmt.mobiletv.screen.videodetail.activity.VideoDetailActivity;
import com.viettel.vpmt.mobiletv.screen.videodetail.fragment.adapter.VideoFragmentPagerAdapter;
import com.viettel.vpmt.mobiletv.screen.videodetail.utils.WrapContentHeightViewPager;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.Bind;

/**
 * Created by ThanhTD on 3/26/2016.
 */
public class VideoDetailFragment extends PlayerFragment<VideoDetailFragmentPresenter, VideoDetailActivity> implements VideoDetailFragmentView {
    @Bind(R.id.common_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.view_transparent)
    View transparent;
    //    @Bind(R.id.video_detail_layout_video)
//    VideoView videoView;
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
    TabLayout tabLayout;
    VideoFragmentPagerAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video_detail;
    }

    @Override
    public void showProgress() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
            transparent.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
            transparent.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPrepareLayout() {
        Bundle bundle = getArguments();
        float videoId = bundle.getFloat("videoId");
        float partOfVideo = bundle.getFloat("part");
        getPresenter().getDetailVideo(0, videoId, partOfVideo);
        CustomTextViewExpandable.makeTextViewResizable(tvFullDes, 3, getString(R.string.view_more), false);
    }

    @Override
    public VideoDetailFragmentPresenter onCreatePresenter() {
        return new VideoDetailFragmentPresenterImpl(this);
    }

    @Override
    public void doLoadToView(VideoDetail videoDetail) {
        String url = videoDetail.getStreams().getUrlStreaming();
        if (!StringUtils.isEmpty(url)) {
            initPlayer(Uri.parse(url), Util.TYPE_OTHER);
        } else {
            Logger.e("Cannot get url streaming...");
        }


        tvTitle.setText(videoDetail.getVideoDetail().getName());
        tvFullDes.setText(videoDetail.getVideoDetail().getDescription());
        if (videoDetail.getVideoDetail().getTag() != null)
            tvTag.setText(getString(R.string.tag) + videoDetail.getVideoDetail().getTag());
        else
            tvTag.setVisibility(View.GONE);
        tvFavorite.setChecked(videoDetail.getVideoDetail().isFavourite());

        //// TODO: 3/29/2016 check init option adapter for view video or tvShow
        if (videoDetail.getVideoRelated() != null) {
            adapter = new VideoFragmentPagerAdapter(videoDetail.getVideoRelated().getContents(), getActivity().getSupportFragmentManager(), getActivity());
            viewPager.setAdapter(adapter);
        }
        tabLayout.setupWithViewPager(viewPager);
        hideProgress();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
