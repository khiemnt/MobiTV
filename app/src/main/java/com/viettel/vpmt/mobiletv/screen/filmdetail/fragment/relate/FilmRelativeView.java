package com.viettel.vpmt.mobiletv.screen.filmdetail.fragment.relate;

import com.viettel.vpmt.mobiletv.base.BaseView;
import com.viettel.vpmt.mobiletv.screen.home.adapter.FilmAdapter;

/**
 * Related Film View
 * Created by ThanhTD on 3/25/2016.
 */
public interface FilmRelativeView extends BaseView<FilmRelativePresenter> {
    void loadRelativeVideo(FilmAdapter filmRelativeAdapter);
}
