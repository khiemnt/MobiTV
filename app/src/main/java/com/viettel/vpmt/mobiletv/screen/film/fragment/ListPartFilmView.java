package com.viettel.vpmt.mobiletv.screen.film.fragment;

import com.viettel.vpmt.mobiletv.base.BaseView;
import com.viettel.vpmt.mobiletv.screen.film.fragment.adapter.RecyclerViewPartFilmAdapter;

/**
 * Created by ThanhTD on 3/29/2016.
 */
public interface ListPartFilmView extends BaseView<ListPartFilmPresenter>{
    void loadDataToView(RecyclerViewPartFilmAdapter viewPartFilmAdapter);
}
