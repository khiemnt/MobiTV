package com.viettel.vpmt.mobiletv.screen.videodetail.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import butterknife.Bind;
import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseFragment;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.screen.videodetail.activity.VideoDetailActivity;
import com.viettel.vpmt.mobiletv.screen.videodetail.fragment.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThanhTD on 3/22/2016.
 */
public class VideoRelativeListFragment extends BaseFragment<VideoRelativePresenter, VideoDetailActivity> implements VideoRelativeView
{
    @Bind(R.id.common_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    List<Content> videos = new ArrayList<>();

    public static VideoRelativeListFragment newInstance(List<Content> videos)
    {
        VideoRelativeListFragment videoRelativeFragment = new VideoRelativeListFragment();
        videoRelativeFragment.setVideos(videos);
        return videoRelativeFragment;
    }


    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_video_relative;
    }

    @Override
    public void showProgress()
    {
        if (mProgressBar != null)
        {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress()
    {
        if (mProgressBar != null)
        {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPrepareLayout()
    {
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        getPresenter().setData(videos);
        getPresenter().getData();
    }

    @Override
    public VideoRelativePresenter onCreatePresenter()
    {
        return new VideoRelativePresenterImpl(this);
    }

    @Override
    public void loadRelativeVideo(RecyclerViewAdapter recyclerViewAdapter)
    {
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
        recyclerView.invalidate();
    }

    public void setVideos(List<Content> videos)
    {
        this.videos = videos;
    }
}
