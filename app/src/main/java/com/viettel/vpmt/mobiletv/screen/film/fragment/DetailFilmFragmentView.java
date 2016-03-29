package com.viettel.vpmt.mobiletv.screen.film.fragment;

import com.viettel.vpmt.mobiletv.base.BaseView;
import com.viettel.vpmt.mobiletv.network.dto.FilmDetail;

/**
 * Created by ThanhTD on 3/26/2016.
 */
public interface DetailFilmFragmentView extends BaseView<FilmDetailFragmentPresenter> {
    void doLoadToView(FilmDetail filmDetail, int positionPartActive);
}
