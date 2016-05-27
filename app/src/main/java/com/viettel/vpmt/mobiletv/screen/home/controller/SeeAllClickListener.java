package com.viettel.vpmt.mobiletv.screen.home.controller;

import com.viettel.vpmt.mobiletv.base.log.Logger;
import com.viettel.vpmt.mobiletv.common.Constants;
import com.viettel.vpmt.mobiletv.network.ApiConstants;
import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.screen.bundle.BundleFragment;
import com.viettel.vpmt.mobiletv.screen.home.HomeBoxActivity;

import android.os.Bundle;
import android.view.View;

/**
 * See all button click listener
 * Created by neo on 3/25/2016.
 */
public class SeeAllClickListener implements View.OnClickListener {
    private HomeBoxActivity mBaseActivity;
    private Box mBox;

    public SeeAllClickListener(HomeBoxActivity baseActivity, Box box) {
        mBaseActivity = baseActivity;
        mBox = box;
    }

    @Override
    public void onClick(View v) {
        Bundle args = new Bundle();
        args.putString(Constants.Extras.PATH, ApiConstants.PATH_GET_MORE_CONTENT);
        args.putString(Constants.Extras.SCOPE, ApiConstants.SCOPE_DEFAULT);
        args.putString(Constants.Extras.ID, mBox.getId());
        args.putString(Constants.Extras.TITLE, mBox.getName());

//        mBaseActivity.openBundleHome(mBox.getType(), mBox.getId());
        mBaseActivity.addChildFragment(BundleFragment.newInstance(mBox.getType(), mBox.getId(), mBox.getName()));
        // TODO check animation &
    }
}
