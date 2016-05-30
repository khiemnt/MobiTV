package com.viettel.vpmt.mobiletv.screen.videodetail.fragment.relate;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseFragment;
import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.screen.home.adapter.GridDividerDecoration;
import com.viettel.vpmt.mobiletv.screen.home.adapter.VideoAdapter;
import com.viettel.vpmt.mobiletv.screen.videodetail.activity.VideoDetailActivity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by ThanhTD on 3/22/2016.
 */
public class VideoRelativeListFragment extends BaseFragment<VideoRelativePresenter, VideoDetailActivity> implements VideoRelativeView {
    @Bind(R.id.relate_recycler_view)
    RecyclerView mRecyclerView;
    List<Content> mContents = new ArrayList<>();

    public static VideoRelativeListFragment newInstance(List<Content> videos) {
        VideoRelativeListFragment videoRelativeFragment = new VideoRelativeListFragment();
        videoRelativeFragment.setContents(videos);
        return videoRelativeFragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video_relative;
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void onPrepareLayout() {
        // Grid layout manager
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), Box.getSpanCount(Box.Type.VOD));
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setClipToPadding(false);
        mRecyclerView.setHasFixedSize(true);

        // Item spacing
        mRecyclerView.addItemDecoration(new GridDividerDecoration(getActivity()));
        getPresenter().setData(mContents);
        getPresenter().getData();
    }

    @Override
    public VideoRelativePresenter onCreatePresenter() {
        return new VideoRelativePresenterImpl(this);
    }

    @Override
    public void loadRelativeVideo(VideoAdapter videoRelativeAdapter) {
        mRecyclerView.setAdapter(videoRelativeAdapter);
    }

    public void setContents(List<Content> contents) {
        this.mContents = contents;
    }
}
