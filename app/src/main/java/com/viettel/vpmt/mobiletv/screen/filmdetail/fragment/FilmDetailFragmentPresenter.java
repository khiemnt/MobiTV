package com.viettel.vpmt.mobiletv.screen.filmdetail.fragment;

import com.viettel.vpmt.mobiletv.base.BasePresenter;

/**
 * Presenter for Film detail fragment
 * Created by ThanhTD on 3/26/2016.
 */
public interface FilmDetailFragmentPresenter extends BasePresenter {
    void getDetailVideo(int position, String videoId, Integer partOfFilm, int tabIndex);
    void getFilmStream(String filmId);
    void postLikeFilm(boolean isLike, String filmId);
}
