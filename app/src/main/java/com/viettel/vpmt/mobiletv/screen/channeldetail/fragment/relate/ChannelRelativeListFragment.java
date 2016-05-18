package com.viettel.vpmt.mobiletv.screen.channeldetail.fragment.relate;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseFragment;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.screen.channeldetail.activity.ChannelActivityDetail;
import com.viettel.vpmt.mobiletv.screen.home.adapter.ChannelAdapter;
import com.viettel.vpmt.mobiletv.screen.home.adapter.HorizontalItemDecoration;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Relative channels fragment
 * Created by neo on 5/17/2016.
 */
public class ChannelRelativeListFragment extends BaseFragment<ChannelRelativePresenter, ChannelActivityDetail> implements ChannelRelativeView {
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    List<Content> videos = new ArrayList<>();

    public static ChannelRelativeListFragment newInstance(List<Content> videos) {
        ChannelRelativeListFragment fragment = new ChannelRelativeListFragment();
        fragment.setVideos(videos);
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
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        int spacingInPixels = getContext().getResources().getDimensionPixelSize(R.dimen.padding_small);
        recyclerView.addItemDecoration(new HorizontalItemDecoration(spacingInPixels));
        getPresenter().setData(videos);
        getPresenter().getData();
    }

    @Override
    public ChannelRelativePresenter onCreatePresenter() {
        return new ChannelRelativePresenterImpl(this);
    }

    @Override
    public void loadRelativeChannel(ChannelAdapter channelAdapter) {
        recyclerView.setAdapter(channelAdapter);
    }

    public void setVideos(List<Content> videos) {
        this.videos = videos;
    }
}
