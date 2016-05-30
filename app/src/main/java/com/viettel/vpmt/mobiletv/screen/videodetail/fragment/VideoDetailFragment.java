package com.viettel.vpmt.mobiletv.screen.videodetail.fragment;

import com.google.android.exoplayer.util.Util;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.common.Constants;
import com.viettel.vpmt.mobiletv.common.util.StringUtils;
import com.viettel.vpmt.mobiletv.common.view.ExpandableTextView;
import com.viettel.vpmt.mobiletv.media.PlayerFragment;
import com.viettel.vpmt.mobiletv.media.player.PlayerController;
import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.network.dto.DataStream;
import com.viettel.vpmt.mobiletv.network.dto.VideoDetail;
import com.viettel.vpmt.mobiletv.screen.videodetail.activity.VideoDetailActivity;
import com.viettel.vpmt.mobiletv.screen.videodetail.utils.WrapContentHeightViewPager;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Video detail fragment
 * Created by ThanhTD on 3/26/2016.
 */
public class VideoDetailFragment extends PlayerFragment<VideoDetailFragmentPresenter, VideoDetailActivity>
        implements VideoDetailFragmentView, PlayerController.PartSelectionListener {
    @Bind(R.id.common_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.view_transparent)
    View mTransparent;
    @Bind(R.id.fragment_video_detail_tvTitle)
    TextView mTitleTv;
    @Bind(R.id.fragment_video_detail_tvFullDes)
    ExpandableTextView mFullDesTv;
    @Bind(R.id.fragment_video_detail_tvTag)
    TextView mTagTv;
    @Bind(R.id.video_detail_thumb_up_down)
    CheckBox mFavoriteTv;
    @Bind(R.id.video_detail_number_of_view)
    CheckBox mPlayCountCb;
    @Bind(R.id.viewpager)
    WrapContentHeightViewPager mViewPager;
    @Bind(R.id.sliding_tabs)
    TabLayout mTabLayout;

    private FragmentStatePagerAdapter mAdapter;
    private String mVideoId = "";

    public static VideoDetailFragment newInstance(String videoId, String title, String coverImageUrl) {
        VideoDetailFragment fragment = new VideoDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Constants.Extras.ID, videoId);
        bundle.putString(Constants.Extras.TITLE, title);
        bundle.putString(Constants.Extras.COVER_IMAGE_URL, coverImageUrl);
        bundle.putString(Constants.Extras.PART, "");
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video_detail;
    }

    @Override
    public void showProgress() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
            mTransparent.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
            mTransparent.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPrepareLayout() {
        Bundle bundle = getArguments();
        mVideoId = bundle.getString(Constants.Extras.ID);
        getPresenter().getVideoDetail(0, mVideoId);

        // Pre-fill
        mTitleTv.setText(bundle.getString(Constants.Extras.TITLE));
    }

    @Override
    protected void firstConfigPlayerController() {
        mPlayerController.setLikeButtonVisibility(View.GONE);
        mPlayerController.setShareButtonVisibility(View.GONE);
        mPlayerController.setProgressTimeLayoutVisibility(View.VISIBLE);
        mPlayerController.setLiveNowVisibility(View.GONE);
    }

    @OnClick(R.id.video_detail_thumb_up_down)
    void doFavorite() {
        getPresenter().postLikeVideo(mFavoriteTv.isChecked(), mVideoId);
    }

    @Override
    public VideoDetailFragmentPresenter onCreatePresenter() {
        return new VideoDetailFragmentPresenterImpl(this);
    }

    @Override
    public void doLoadToView(VideoDetail videoDetail, int positionPartActive) {
        mVideoId = videoDetail.getVideoDetail().getId();

        String url = videoDetail.getStreams().getUrlStreaming();
        if (!StringUtils.isEmpty(url)) {
            initPlayer(Uri.parse(url), Util.TYPE_HLS, videoDetail.getVideoDetail().getCoverImage());
        } else {
            //todo request login later
            getPresenter().getVideoStream(mVideoId);
        }

        // Title & description
        mTitleTv.setText(videoDetail.getVideoDetail().getName());
        if (!StringUtils.isEmpty(videoDetail.getVideoDetail().getDescription())) {
            mFullDesTv.setTrim(true);
            mFullDesTv.setText(videoDetail.getVideoDetail().getDescription(), TextView.BufferType.SPANNABLE);
        } else {
            mFullDesTv.setVisibility(View.GONE);
        }

        // Tags
        if (!StringUtils.isEmpty(videoDetail.getVideoDetail().getTag()))
            mTagTv.setText(getString(R.string.tag) + videoDetail.getVideoDetail().getTag());
        else
            mTagTv.setVisibility(View.GONE);

        // Like
        mFavoriteTv.setChecked(videoDetail.getVideoDetail().isFavourite());
        mFavoriteTv.setText(videoDetail.getVideoDetail().getLikeCount() != null
                ? videoDetail.getVideoDetail().getLikeCount().toString() : "0");
        mPlayerController.doRefreshLike(videoDetail.getVideoDetail().isFavourite());

        // Play count
        mPlayCountCb.setText(videoDetail.getVideoDetail().getPlayCount() != null
                ? videoDetail.getVideoDetail().getPlayCount().toString() : "0");

        // Check if is TVShow or not
        if (videoDetail.getContentRelated() != null) {
            if (videoDetail.getVideoDetail().getType().name().equalsIgnoreCase(Box.Type.TVSHOW.name())) {
                mAdapter = new VideoPartFragmentPagerAdapter(mVideoId, positionPartActive, videoDetail.getContentRelated().getContents(),
                        getActivity().getSupportFragmentManager(), getActivity());
                List<Content> contents = videoDetail.getContentRelated().getContents();
                if (contents != null && contents.size() > 0) {
                    setPlayerParts(contents, positionPartActive);
//                    mPlayerController.setPlayListVisibility(View.VISIBLE);
                } else {
//                    mPlayerController.setPlayListVisibility(View.GONE);
                }

            } else {
                mAdapter = new VideoPartFragmentPagerAdapter(mVideoId, -1, videoDetail.getContentRelated().getContents(), getActivity().getSupportFragmentManager(), getActivity());
            }//VideoFragmentPagerAdapter
            mViewPager.setAdapter(mAdapter);
        }
        mTabLayout.setupWithViewPager(mViewPager);
        hideProgress();
    }

    private void setPlayerParts(List<Content> contents, int positionPartActive) {
        // Visible next/prev buttons
        mPlayerController.setNextVisibility(View.VISIBLE);
        mPlayerController.setPreviousVisibility(View.VISIBLE);

        // Pass data to controller
        List<PlayerController.VideoPart> videoParts = new ArrayList<>();
        for (int i = 0; i < contents.size(); i++) {
            Content content = contents.get(i);
            videoParts.add(new PlayerController.VideoPart(content.getId(), i, content.getName()));
        }

        mPlayerController.setVideoParts(videoParts, this);
        mPlayerController.setPartPosition(positionPartActive);
        mPlayerController.setPrevNextListeners(new NextClicked(contents), new PrevClicked(contents));

        // Validate views
        if (positionPartActive == 0) {
            mPlayerController.setNextEnabled(true);
            mPlayerController.setPreviousEnabled(false);
        } else if (positionPartActive == contents.size() - 1) {
            mPlayerController.setNextEnabled(false);
            mPlayerController.setPreviousEnabled(true);
        } else {
            mPlayerController.setNextEnabled(true);
            mPlayerController.setPreviousEnabled(true);
        }
    }

    @Override
    public void doLoadVideoStream(DataStream videoStream) {
        initPlayer(Uri.parse(videoStream.getStreams().getUrlStreaming()), Util.TYPE_HLS);
    }

    @Override
    public void doRefreshLike(boolean isLike) {
        mFavoriteTv.setChecked(isLike);

        try {
            int likeCount = Integer.valueOf(mFavoriteTv.getText().toString());
            likeCount = isLike ? likeCount + 1 : likeCount - 1;
            if (likeCount < 0) {
                likeCount = 0;
            }
            mFavoriteTv.setText(String.valueOf(likeCount));
            mPlayerController.doRefreshLike(isLike);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
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

    @Override
    public void onPartSelected(int position) {
        getPresenter().getVideoDetail(position,
                mPlayerController.getVideoParts().get(position).getId());
    }

    @Override
    public void onPlayerLikeClicked() {
        getPresenter().postLikeVideo(mFavoriteTv.isChecked(), mVideoId);
    }

    @Override
    public void onPlayerShareClicked() {
        shareContent(getPresenter().getVideoDetail().getVideoDetail().getLink());
    }

    private class NextClicked implements View.OnClickListener {
        private List<Content> mContentRelateds;

        public NextClicked(List<Content> contentRelateds) {
            mContentRelateds = contentRelateds;
        }

        @Override
        public void onClick(View v) {
            int position = mPlayerController.getPartPosition() + 1;
            getPresenter().getVideoDetail(position,
                    mContentRelateds.get(position).getId());
        }
    }

    private class PrevClicked implements View.OnClickListener {
        private List<Content> mContentRelateds;

        public PrevClicked(List<Content> contentRelateds) {
            mContentRelateds = contentRelateds;
        }
        @Override
        public void onClick(View v) {
            int position = mPlayerController.getPartPosition() - 1;
            getPresenter().getVideoDetail(position,
                    mContentRelateds.get(position).getId());
        }
    }
}
