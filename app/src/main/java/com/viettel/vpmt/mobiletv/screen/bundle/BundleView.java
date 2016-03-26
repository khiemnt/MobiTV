package com.viettel.vpmt.mobiletv.screen.bundle;

import com.viettel.vpmt.mobiletv.base.BaseView;

/**
 * Common views
 * Created by neo on 2/5/2016.
 */
public interface BundleView extends BaseView<BundlePresenter> {
    void loadBox(BundleVideoAdapter bundleVideoAdapter);
}
