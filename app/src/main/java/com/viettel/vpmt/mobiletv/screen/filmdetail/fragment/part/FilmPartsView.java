package com.viettel.vpmt.mobiletv.screen.filmdetail.fragment.part;

import com.viettel.vpmt.mobiletv.base.BaseView;

/**
 * Created by ThanhTD on 3/29/2016.
 */
public interface FilmPartsView extends BaseView<FilmPartsPresenter> {
    void loadDataToView(FilmPartsAdapter viewPartFilmAdapter);
}
