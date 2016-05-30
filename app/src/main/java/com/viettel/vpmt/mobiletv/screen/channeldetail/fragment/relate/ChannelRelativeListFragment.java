package com.viettel.vpmt.mobiletv.screen.channeldetail.fragment.relate;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseFragment;
import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.screen.channeldetail.activity.ChannelDetailActivity;
import com.viettel.vpmt.mobiletv.screen.home.adapter.ChannelAdapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Relative channels fragment
 * Created by neo on 5/17/2016.
 */
public class ChannelRelativeListFragment extends BaseFragment<ChannelRelativePresenter, ChannelDetailActivity> implements ChannelRelativeView {
    @Bind(R.id.relate_recycler_view)
    RecyclerView mRecyclerView;
    List<Content> mContents = new ArrayList<>();

    public static ChannelRelativeListFragment newInstance(List<Content> videos) {
        ChannelRelativeListFragment fragment = new ChannelRelativeListFragment();
        fragment.setContents(videos);
        return fragment;
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
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), Box.getSpanCount(Box.Type.LIVETV));
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setClipToPadding(false);
        mRecyclerView.setHasFixedSize(true);

        // Item spacing
        getPresenter().setData(mContents);
        getPresenter().getData();
    }

    @Override
    public ChannelRelativePresenter onCreatePresenter() {
        return new ChannelRelativePresenterImpl(this);
    }

    @Override
    public void loadRelativeChannel(ChannelAdapter channelAdapter) {
        mRecyclerView.setAdapter(channelAdapter);
    }

    public void setContents(List<Content> contents) {
        this.mContents = contents;
    }
}
