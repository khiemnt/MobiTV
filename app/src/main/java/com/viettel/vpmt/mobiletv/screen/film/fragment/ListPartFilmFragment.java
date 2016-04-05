package com.viettel.vpmt.mobiletv.screen.film.fragment;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseFragment;
import com.viettel.vpmt.mobiletv.network.dto.PartOfFilm;
import com.viettel.vpmt.mobiletv.screen.film.activity.FilmActivityDetail;
import com.viettel.vpmt.mobiletv.screen.film.fragment.adapter.RecyclerViewPartFilmAdapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by ThanhTD on 3/29/2016.
 */
public class ListPartFilmFragment extends BaseFragment<ListPartFilmPresenter, FilmActivityDetail> implements ListPartFilmView {

    @Bind(R.id.list_part_recyclerView)
    RecyclerView mRecyclerView;

    List<PartOfFilm> parts = new ArrayList<>();
    int positionActive = 0;
    float filmId = 0;

    public static ListPartFilmFragment newInstance(List<PartOfFilm> parts, float filmId, int positionActive) {
        ListPartFilmFragment listPartFilmFragment = new ListPartFilmFragment();
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
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onPrepareLayout() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.part_film_column));
        mRecyclerView.setLayoutManager(linearLayoutManager);
        getPresenter().setData(parts, filmId);
        getPresenter().setPositionActive(positionActive);
        getPresenter().getData();
    }


    @Override
    public ListPartFilmPresenter onCreatePresenter() {
        return new ListPartFilmPresenterImpl(this);
    }

    @Override
    public void loadDataToView(RecyclerViewPartFilmAdapter viewPartFilmAdapter) {
        mRecyclerView.setAdapter(viewPartFilmAdapter);
    }

    public void setParts(List<PartOfFilm> parts) {
        this.parts = parts;
    }

    public void setPositionActive(int positionActive) {
        this.positionActive = positionActive;
    }

    public void setFilmId(float filmId) {
        this.filmId = filmId;
    }
}
