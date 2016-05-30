package com.viettel.vpmt.mobiletv.screen.bundle;

import com.viettel.vpmt.mobiletv.base.BaseView;

import android.support.v7.widget.RecyclerView;

/**
 * Common views
 * Created by neo on 2/5/2016.
 */
public interface BundleView extends BaseView<BundlePresenter> {
    void loadBundle(RecyclerView.Adapter bundleVideoAdapter);

    void onDataEmpty();

    void stopLoadMore();
}
