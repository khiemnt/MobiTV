package com.viettel.vpmt.mobiletv.screen.home.controller;

import android.os.Bundle;
import android.view.View;

import com.viettel.vpmt.mobiletv.base.BaseActivity;
import com.viettel.vpmt.mobiletv.base.log.Logger;
import com.viettel.vpmt.mobiletv.common.Constants;
import com.viettel.vpmt.mobiletv.network.ApiConstants;
import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.screen.home.HomeBoxFragment;

/**
 * See all button click listener
 * Created by neo on 3/25/2016.
 */
public class SeeAllClickListener implements View.OnClickListener {
    private BaseActivity mBaseActivity;
    private Box mBox;

    public SeeAllClickListener(BaseActivity baseActivity, Box box) {
        mBaseActivity = baseActivity;
        mBox = box;
    }

    @Override
    public void onClick(View v) {
        Logger.e("@@@", "ID=" + mBox.getId());
        Bundle args = new Bundle();
        args.putString(Constants.Extras.PATH, ApiConstants.PATH_GET_MORE_CONTENT);
        args.putString(Constants.Extras.SCOPE, ApiConstants.SCOPE_DEFAULT);
        args.putString(Constants.Extras.ID, mBox.getId());

        mBaseActivity.addFragment(new HomeBoxFragment(), args, true);
    }
}
