package com.viettel.vpmt.mobiletv.screen.channeldetail.fragment;

import com.google.android.exoplayer.util.Util;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.log.Logger;
import com.viettel.vpmt.mobiletv.common.util.ImageUtils;
import com.viettel.vpmt.mobiletv.media.PlayerFragment;
import com.viettel.vpmt.mobiletv.network.dto.ChannelDetail;
import com.viettel.vpmt.mobiletv.network.dto.DataStream;
import com.viettel.vpmt.mobiletv.screen.channeldetail.activity.ChannelActivityDetail;
import com.viettel.vpmt.mobiletv.screen.filmdetail.utils.WrapContentHeightViewPager;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.widget.ImageView;

import butterknife.Bind;

/**
 * Channel/TV detail fragment
 * Created by neo on 4/17/2016.
 */
public class ChannelDetailFragment extends PlayerFragment<ChannelDetailFragmentPresenter, ChannelActivityDetail> implements ChannelDetailFragmentView {
    @Bind(R.id.channel_detail_logo_iv)
    ImageView mLogoIv;
    @Bind(R.id.viewpager)
    WrapContentHeightViewPager mViewPager;
    @Bind(R.id.sliding_tabs)
    TabLayout mTabLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_channel_detail;
    }

    @Override
    public void onPrepareLayout() {
//        String channelId = getArguments().getString(Constants.Extras.ID);
        // TODO hard code
        String channelId = "141";
        getDetailWithId(channelId);
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
        ChannelFragmentPagerAdapter adapter;

        adapter = new ChannelFragmentPagerAdapter(channelDetail.getChannelContent().getId(),
                channelDetail.getSchedules(), channelDetail.getContentRelated().getContents(),
                getActivity().getSupportFragmentManager(), getActivity(), getPresenter());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        if (mTabLayout != null && mTabLayout.getTabAt(0) != null) {
            mTabLayout.getTabAt(0).select();
        }

        // Play channel
        String url = channelDetail.getStreams().getUrlStreaming();
        Logger.e("URL=\n" + url);
//        if (!StringUtils.isEmpty(url)) {
//            initPlayer(Uri.parse(url), Util.TYPE_HLS);
//        } else {
            //todo request login later
            getPresenter().getChannelStream(channelDetail.getChannelContent().getId());
//        }
    }

    @Override
    public void doLoadChannelStream(DataStream videoStream) {
        initPlayer(Uri.parse(videoStream.getStreams().getUrlStreaming()), Util.TYPE_HLS);
    }

    @Override
    public void loadProgram(String programStreamUrl) {
        initPlayer(Uri.parse(programStreamUrl), Util.TYPE_HLS);
    }
}
