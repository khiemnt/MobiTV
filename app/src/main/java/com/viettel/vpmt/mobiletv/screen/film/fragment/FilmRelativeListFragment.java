package com.viettel.vpmt.mobiletv.screen.film.fragment;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseFragment;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.screen.film.activity.FilmActivityDetail;
import com.viettel.vpmt.mobiletv.screen.film.fragment.adapter.RecyclerViewFilmAdapter;
import com.viettel.vpmt.mobiletv.screen.home.adapter.HorizontalItemDecoration;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by ThanhTD on 3/22/2016.
 */
public class FilmRelativeListFragment extends BaseFragment<FilmRelativePresenter, FilmActivityDetail> implements FilmRelativeView {
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    List<Content> videos = new ArrayList<>();

    public static FilmRelativeListFragment newInstance(List<Content> videos) {
        FilmRelativeListFragment videoRelativeFragment = new FilmRelativeListFragment();
        videoRelativeFragment.setVideos(videos);
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
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        int spacingInPixels = getContext().getResources().getDimensionPixelSize(R.dimen.padding_small);
        recyclerView.addItemDecoration(new HorizontalItemDecoration(spacingInPixels));
        getPresenter().setData(videos);
        getPresenter().getData();
    }

    @Override
    public FilmRelativePresenter onCreatePresenter() {
        return new FilmRelativePresenterImpl(this);
    }

    @Override
    public void loadRelativeVideo(RecyclerViewFilmAdapter recyclerViewFilmAdapter) {
        recyclerView.setAdapter(recyclerViewFilmAdapter);
    }

    public void setVideos(List<Content> videos) {
        this.videos = videos;
    }
}
