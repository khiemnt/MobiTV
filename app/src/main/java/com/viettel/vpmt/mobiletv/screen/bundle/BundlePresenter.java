package com.viettel.vpmt.mobiletv.screen.bundle;

import com.viettel.vpmt.mobiletv.base.BasePresenter;
import com.viettel.vpmt.mobiletv.network.dto.Box;

import android.graphics.Point;

/**
 * Common Bundle Fragment Presenter
 * Created by neo on 3/22/2016.
 */
public interface BundlePresenter extends BasePresenter {
    int ITEM_LIMIT = 18;

    void getData(Box.Type boxType, String id, Point itemImageSize);

    void loadMore();
}
