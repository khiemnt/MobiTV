package com.viettel.vpmt.mobiletv.screen.bundle;

import android.os.Bundle;

import com.viettel.vpmt.mobiletv.base.BasePresenter;
import com.viettel.vpmt.mobiletv.network.dto.Box;

/**
 * Common Bundle Fragment Presenter
 * Created by neo on 3/22/2016.
 */
public interface BundlePresenter extends BasePresenter {
    void getData(Box.Type boxType, Bundle args);
}
