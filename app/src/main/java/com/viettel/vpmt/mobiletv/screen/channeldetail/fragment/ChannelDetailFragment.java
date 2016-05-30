package com.viettel.vpmt.mobiletv.screen.channeldetail.fragment;

import com.google.android.exoplayer.util.Util;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.log.Logger;
import com.viettel.vpmt.mobiletv.common.Constants;
import com.viettel.vpmt.mobiletv.common.util.ImageUtils;
import com.viettel.vpmt.mobiletv.common.util.StringUtils;
import com.viettel.vpmt.mobiletv.media.PlayerFragment;
import com.viettel.vpmt.mobiletv.network.dto.ChannelDetail;
import com.viettel.vpmt.mobiletv.network.dto.DataStream;
import com.viettel.vpmt.mobiletv.screen.channeldetail.activity.ChannelDetailActivity;
import com.viettel.vpmt.mobiletv.screen.filmdetail.utils.WrapContentHeightViewPager;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;

import butterknife.Bind;

/**
 * Channel/TV detail fragment
 * Created by neo on 4/17/2016.
 */
public class ChannelDetailFragment extends PlayerFragment<ChannelDetailFragmentPresenter, ChannelDetailActivity> implements ChannelDetailFragmentView {
    @Bind(R.id.channel_detail_logo_iv)
    ImageView mLogoIv;
    @Bind(R.id.viewpager)
    WrapContentHeightViewPager mViewPager;
    @Bind(R.id.sliding_tabs)
    TabLayout mTabLayout;

    private String mStreamUrl = null;

    public static ChannelDetailFragment newInstance(String channelId, String title, String coverImageUrl) {
        ChannelDetailFragment fragment = new ChannelDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Constants.Extras.ID, channelId);
        bundle.putString(Constants.Extras.TITLE, title);
        bundle.putString(Constants.Extras.COVER_IMAGE_URL, coverImageUrl);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_channel_detail;
    }

    @Override
    public void onPrepareLayout() {
        String channelId = getArguments().getString(Constants.Extras.ID);
        getDetailWithId(channelId);
    }

    @Override
    protected void firstConfigPlayerController() {
        mPlayerController.setLikeButtonVisibility(View.GONE);
        mPlayerController.setShareButtonVisibility(View.GONE);
        validateView(true);
    }

    private void getDetailWithId(String channelId) {
        getPresenter().getChannelDetail(channelId);
    }

    @Override
    public ChannelDetailFragmentPresenter onCreatePresenter() {
        return new ChannelDetailFragmentPresenterImpl(this);
    }

    @Override
    public void loadToView(ChannelDetail channelDetail) {
        // Load logo
        ImageUtils.loadImage(getActivity(), channelDetail.getChannelContent().getLogoImage(),
                mLogoIv, false);

        // Load tabs
        ChannelFragmentPagerAdapter adapter = new ChannelFragmentPagerAdapter(channelDetail,
                getActivity().getSupportFragmentManager(), getActivity(), getPresenter());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        if (mTabLayout != null && mTabLayout.getTabAt(0) != null) {
            mTabLayout.getTabAt(0).select();
        }

        // Like
        mPlayerController.doRefreshLike(channelDetail.getChannelContent().isFavourite());

        // Play channel
        String url = channelDetail.getStreams().getUrlStreaming();
        Logger.e("URL=\n" + url);
        if (!StringUtils.isEmpty(url)) {
            mStreamUrl = url;
            String coverImageUrl = channelDetail.getChannelContent().getCoverImage();
            initPlayer(Uri.parse(url), Util.TYPE_HLS, coverImageUrl);
        } else {
            //todo request login later
            getPresenter().getChannelStream(channelDetail.getChannelContent().getId());
        }
    }

    @Override
    public void doLoadChannelStream(DataStream videoStream) {
        mStreamUrl = videoStream.getStreams().getUrlStreaming();
        initPlayer(Uri.parse(mStreamUrl), Util.TYPE_HLS);
    }

    @Override
    public void loadProgram(String programStreamUrl) {
        initPlayer(Uri.parse(programStreamUrl), Util.TYPE_HLS);
        validateView(false);
    }

    @Override
    public void playPresentProgram() {
        if (!StringUtils.isEmpty(mStreamUrl)) {
            initPlayer(Uri.parse(mStreamUrl), Util.TYPE_HLS);
            validateView(true);
        }
    }

    @Override
    public void doRefreshLike(boolean like) {
        mPlayerController.doRefreshLike(like);
    }

    private void validateView(boolean isPlayLive) {
        if (isPlayLive) {
            mPlayerController.setLiveNowVisibility(View.VISIBLE);
            mPlayerController.setProgressTimeLayoutVisibility(View.GONE);
        } else {
            mPlayerController.setLiveNowVisibility(View.GONE);
            mPlayerController.setProgressTimeLayoutVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPlayerLikeClicked() {

    }

    @Override
    public void onPlayerShareClicked() {

    }

//    @Override
//    public void doRefreshLike(boolean isLike) {
//        mFavoriteTv.setChecked(isLike);
//
//        try {
//            int likeCount = Integer.valueOf(mFavoriteTv.getText().toString());
//            likeCount = isLike ? likeCount + 1 : likeCount - 1;
//            mFavoriteTv.setText(String.valueOf(likeCount));
//        } catch (NumberFormatException ex) {
//            ex.printStackTrace();
//        }
//    }
}
