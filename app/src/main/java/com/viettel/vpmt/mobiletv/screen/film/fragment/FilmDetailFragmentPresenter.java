package com.viettel.vpmt.mobiletv.screen.film.fragment;

import com.viettel.vpmt.mobiletv.base.BasePresenter;

/**
 * Created by ThanhTD on 3/26/2016.
 */
public interface FilmDetailFragmentPresenter extends BasePresenter {
    void getDetailVideo(int position, float videoId, Integer partOfFilm, int tabIndex);
    void getVideoStream(float videoId);
    void postLikeFilm(boolean isLike, float filmId);
}
