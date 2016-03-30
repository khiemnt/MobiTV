package com.viettel.vpmt.mobiletv.screen.videodetail.fragment;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseFragment;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.network.dto.PartOfVideo;
import com.viettel.vpmt.mobiletv.screen.videodetail.activity.VideoDetailActivity;
import com.viettel.vpmt.mobiletv.screen.videodetail.fragment.adapter.RecyclerViewPartVideoAdapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by ThanhTD on 3/29/2016.
 */
public class ListPartVideoFragment extends BaseFragment<ListPartVideoPresenter, VideoDetailActivity> implements ListPartVideoView {

    @Bind(R.id.list_part_recyclerView)
    RecyclerView mRecyclerView;

    List<Content> parts = new ArrayList<>();
    int positionActive = 0;
    float filmId = 0;

    public static ListPartVideoFragment newInstance(List<Content> parts, float filmId, int positionActive) {
        ListPartVideoFragment listPartFilmFragment = new ListPartVideoFragment();
        listPartFilmFragment.setParts(parts);
        listPartFilmFragment.setPositionActive(positionActive);
        listPartFilmFragment.setFilmId(filmId);
        return listPartFilmFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_part_film_or_video;
    }

    @Override
    public void loadDataToView(RecyclerViewPartVideoAdapter viewPartVideoAdapter) {
        mRecyclerView.setAdapter(viewPartVideoAdapter);
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
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        getPresenter().setData(parts, filmId);
        getPresenter().setPositionActive(positionActive);
        getPresenter().getData();
    }

    @Override
    public ListPartVideoPresenter onCreatePresenter() {
        return new ListPartVideoPresenterImpl(this);
    }

    public void setParts(List<Content> parts) {
        this.parts = parts;
    }

    public void setPositionActive(int positionActive) {
        this.positionActive = positionActive;
    }

    public void setFilmId(float filmId) {
        this.filmId = filmId;
    }
}
