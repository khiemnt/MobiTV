package com.viettel.vpmt.mobiletv.screen.filmdetail.fragment.part;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseFragment;
import com.viettel.vpmt.mobiletv.network.dto.PartOfFilm;
import com.viettel.vpmt.mobiletv.screen.filmdetail.activity.FilmActivityDetail;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * List of parts of film framgent
 * Created by ThanhTD on 3/29/2016.
 */
public class FilmPartsFragment extends BaseFragment<FilmPartsPresenter, FilmActivityDetail> implements FilmPartsView {

    @Bind(R.id.list_recyclerView)
    RecyclerView mRecyclerView;

    private List<PartOfFilm> parts = new ArrayList<>();
    private int positionActive = 0;
    private String mFilmId = "";

    public static FilmPartsFragment newInstance(List<PartOfFilm> parts, String filmId, int positionActive) {
        FilmPartsFragment filmPartsFragment = new FilmPartsFragment();
        filmPartsFragment.setParts(parts);
        filmPartsFragment.setPositionActive(positionActive);
        filmPartsFragment.setFilmId(filmId);
        return filmPartsFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_recycler_view;
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
        getPresenter().setData(parts, mFilmId);
        getPresenter().setPositionActive(positionActive);
        getPresenter().getData();
    }


    @Override
    public FilmPartsPresenter onCreatePresenter() {
        return new FilmPartsPresenterImpl(this);
    }

    @Override
    public void loadDataToView(FilmPartsAdapter viewPartFilmAdapter) {
        mRecyclerView.setAdapter(viewPartFilmAdapter);
    }

    public void setParts(List<PartOfFilm> parts) {
        this.parts = parts;
    }

    public void setPositionActive(int positionActive) {
        this.positionActive = positionActive;
    }

    public void setFilmId(String filmId) {
        this.mFilmId = filmId;
    }
}
