package com.viettel.vpmt.mobiletv.screen.home;

import com.viettel.vpmt.mobiletv.base.BasePresenter;

/**
 * Common Fragment Presenter
 * Created by neo on 3/22/2016.
 */
public interface HomeBoxFragmentPresenter extends BasePresenter {
    void getData(String scope, String path);
}
