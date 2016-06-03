package com.viettel.vpmt.mobiletv.screen.home;

import com.viettel.vpmt.mobiletv.base.BaseView;
import com.viettel.vpmt.mobiletv.screen.common.adapter.HomeBoxAdapter;

/**
 * Common views
 * Created by neo on 2/5/2016.
 */
public interface HomeBoxFragmentView extends BaseView<HomeBoxFragmentPresenter> {
    void loadBox(HomeBoxAdapter homeBoxAdapter);

    boolean isTvHome();
}
