package com.viettel.vpmt.mobiletv.screen.film.fragment;

import com.viettel.vpmt.mobiletv.base.BasePresenter;
import com.viettel.vpmt.mobiletv.network.dto.PartOfFilm;

import java.util.List;

/**
 * Created by ThanhTD on 3/29/2016.
 */
public interface ListPartFilmPresenter extends BasePresenter{
    void getData();
    void setData(List<PartOfFilm> parts, Float filmId);
    void setPositionActive(int positionActive);
}
