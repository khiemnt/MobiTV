package com.viettel.vpmt.mobiletv.screen.filmdetail.fragment.part;

import com.viettel.vpmt.mobiletv.base.BasePresenterImpl;
import com.viettel.vpmt.mobiletv.network.dto.PartOfFilm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThanhTD on 3/29/2016.
 */
public class FilmPartsPresenterImpl extends BasePresenterImpl<FilmPartsView> implements FilmPartsPresenter {
    public FilmPartsPresenterImpl(FilmPartsView view) {
        super(view);
    }

    private List<PartOfFilm> mParts = new ArrayList<>();
    private int mPositionActive = 0;
    private String mFilmId;

    @Override
    public void getData() {
        FilmPartsAdapter viewPartFilmAdapter = new FilmPartsAdapter(mFilmId, mParts, mPositionActive, mView.getViewContext());
        mView.loadDataToView(viewPartFilmAdapter);
    }

    @Override
    public void setData(List<PartOfFilm> parts, String filmId) {
        mParts = parts;
        mFilmId = filmId;
    }

    @Override
    public void setPositionActive(int positionActive) {
        mPositionActive = positionActive;
    }
}
