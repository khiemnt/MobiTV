package com.viettel.vpmt.mobiletv.screen.videodetail.fragment;

import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.VideoView;
import butterknife.Bind;
import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseFragment;
import com.viettel.vpmt.mobiletv.common.util.CustomTextViewExpandable;
import com.viettel.vpmt.mobiletv.network.dto.VideoDetail;
import com.viettel.vpmt.mobiletv.screen.videodetail.activity.VideoDetailActivity;
import com.viettel.vpmt.mobiletv.screen.videodetail.fragment.adapter.SampleFragmentPagerAdapter;
import com.viewpagerindicator.TitlePageIndicator;

/**
 * Created by ThanhTD on 3/26/2016.
 */
public class VideoDetailFragment extends BaseFragment<VideoDetailFragmentPresenter, VideoDetailActivity> implements VideoDetailFragmentView
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
    protected int getLayoutId()
    {
        return R.layout.fragment_video_detail;
    }

    @Override
    public void showProgress()
    {

    }

    @Override
    public void hideProgress()
    {

    }

    @Override
    public void onPrepareLayout()
    {
        getPresenter().getDetailVideo();
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
        tvTitle.setText(videoDetail.getVideoDetail().getName());
        tvFullDes.setText(videoDetail.getVideoDetail().getDescription());
        tvTag.setText("Tags: " + videoDetail.getVideoDetail().getTag());
        tvFavorite.setChecked(videoDetail.getVideoDetail().isFavourite());
        if (videoDetail.getVideoRelated() != null)
        {
            adapter = new SampleFragmentPagerAdapter(videoDetail.getVideoRelated().getContents(), getActivity().getSupportFragmentManager(), getActivity());
            viewPager.setAdapter(adapter);
        }
        tabLayout.setViewPager(viewPager);
    }
}
