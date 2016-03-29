package com.viettel.vpmt.mobiletv.screen.film.fragment;

import com.viettel.vpmt.mobiletv.base.BaseView;
import com.viettel.vpmt.mobiletv.screen.film.fragment.adapter.RecyclerViewFilmAdapter;

/**
 * Created by ThanhTD on 3/25/2016.
 */
public interface FilmRelativeView extends BaseView<FilmRelativePresenter> {
    void loadRelativeVideo(RecyclerViewFilmAdapter recyclerViewFilmAdapter);
}
