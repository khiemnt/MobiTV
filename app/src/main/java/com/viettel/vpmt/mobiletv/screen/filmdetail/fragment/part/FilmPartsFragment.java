package com.viettel.vpmt.mobiletv.screen.filmdetail.fragment.part;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseFragment;
import com.viettel.vpmt.mobiletv.network.dto.PartOfFilm;
import com.viettel.vpmt.mobiletv.screen.filmdetail.activity.FilmDetailActivity;
import com.viettel.vpmt.mobiletv.screen.common.adapter.GridDividerDecoration;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * List of parts of film framgent
 * Created by ThanhTD on 3/29/2016.
 */
public class FilmPartsFragment extends BaseFragment<FilmPartsPresenter, FilmDetailActivity> implements FilmPartsView {

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
        LinearLayout.LayoutParams params
                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        mRecyclerView.setLayoutParams(params);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);

        int spanCount = getResources().getInteger(R.integer.part_film_column);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
        GridDividerDecoration decoration = new GridDividerDecoration(getViewContext(), spanCount);
        mRecyclerView.addItemDecoration(decoration);

        mRecyclerView.setLayoutManager(layoutManager);
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
