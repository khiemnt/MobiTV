package com.viettel.vpmt.mobiletv.screen.film.fragment;

import com.viettel.vpmt.mobiletv.base.BasePresenterImpl;
import com.viettel.vpmt.mobiletv.network.dto.PartOfFilm;
import com.viettel.vpmt.mobiletv.screen.film.fragment.adapter.RecyclerViewPartFilmAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThanhTD on 3/29/2016.
 */
public class ListPartFilmPresenterImpl extends BasePresenterImpl<ListPartFilmView> implements ListPartFilmPresenter {
    public ListPartFilmPresenterImpl(ListPartFilmView view) {
        super(view);
    }

    List<PartOfFilm> parts = new ArrayList<>();
    int positionActive = 0;
    float filmId;

    @Override
    public void getData() {
        RecyclerViewPartFilmAdapter viewPartFilmAdapter = new RecyclerViewPartFilmAdapter(filmId, parts, positionActive, mView.getViewContext());
        mView.loadDataToView(viewPartFilmAdapter);
    }

    @Override
    public void setData(List<PartOfFilm> parts, Float filmId) {
        this.parts = parts;
        this.filmId = filmId;
    }

    @Override
    public void setPositionActive(int positionActive) {
        this.positionActive = positionActive;
    }
}
