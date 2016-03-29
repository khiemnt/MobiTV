package com.viettel.vpmt.mobiletv.screen.film.fragment;

import com.viettel.vpmt.mobiletv.base.BasePresenter;
import com.viettel.vpmt.mobiletv.network.dto.Content;

import java.util.List;

/**
 * Created by ThanhTD on 3/25/2016.
 */
public interface FilmRelativePresenter extends BasePresenter {
    void setData(List<Content> videos);

    void getData();
}
