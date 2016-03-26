package com.viettel.vpmt.mobiletv.screen.bundle;

import android.support.v7.widget.RecyclerView;

import com.viettel.vpmt.mobiletv.base.BaseView;

/**
 * Common views
 * Created by neo on 2/5/2016.
 */
public interface BundleView extends BaseView<BundlePresenter> {
    void loadBox(RecyclerView.Adapter bundleVideoAdapter);
    void onDataEmpty();
    void stopLoadMore();
}
