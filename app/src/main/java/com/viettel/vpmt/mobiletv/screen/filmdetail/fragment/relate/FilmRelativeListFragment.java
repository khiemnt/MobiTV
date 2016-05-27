package com.viettel.vpmt.mobiletv.screen.filmdetail.fragment.relate;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseFragment;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.screen.filmdetail.activity.FilmDetailActivity;
import com.viettel.vpmt.mobiletv.screen.home.adapter.FilmAdapter;
import com.viettel.vpmt.mobiletv.screen.home.adapter.HorizontalItemDecoration;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Related Film fragment
 * Created by ThanhTD on 3/22/2016.
 */
public class FilmRelativeListFragment extends BaseFragment<FilmRelativePresenter, FilmDetailActivity> implements FilmRelativeView {
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private List<Content> mContents = new ArrayList<>();

    public static FilmRelativeListFragment newInstance(List<Content> contents) {
        FilmRelativeListFragment videoRelativeFragment = new FilmRelativeListFragment();
        videoRelativeFragment.setContents(contents);
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
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        int spacingInPixels = getContext().getResources().getDimensionPixelSize(R.dimen.padding_small);
        mRecyclerView.addItemDecoration(new HorizontalItemDecoration(spacingInPixels));
        getPresenter().setData(mContents);
        getPresenter().getData();
    }

    @Override
    public FilmRelativePresenter onCreatePresenter() {
        return new FilmRelativePresenterImpl(this);
    }

    @Override
    public void loadRelativeVideo(FilmAdapter filmRelativeAdapter) {
        mRecyclerView.setAdapter(filmRelativeAdapter);
    }

    public void setContents(List<Content> contents) {
        mContents = contents;
    }
}
