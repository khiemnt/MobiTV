package com.viettel.vpmt.mobiletv.screen.home;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseActivity;
import com.viettel.vpmt.mobiletv.base.BaseFragment;
import com.viettel.vpmt.mobiletv.common.Constants;
import com.viettel.vpmt.mobiletv.network.ApiConstants;

import butterknife.Bind;


public class HomeBoxActivity extends BaseActivity<HomeBoxFragmentPresenter> implements HomeBoxActivityView {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupToolbar();

        // Home page
        Bundle args = new Bundle();
        args.putString(Constants.Extras.PATH, ApiConstants.PATH_HOME);
        args.putString(Constants.Extras.SCOPE, ApiConstants.SCOPE_DEFAULT);
        addFragment(new HomeBoxFragment(), args, false);
    }

    /**
     * Setup view & type for toolbar
     */
    private void setupToolbar() {
        setSupportActionBar(mToolbar);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_box_home;
    }


    @Override
    public HomeBoxFragmentPresenter onCreatePresenter() {
        return null;
    }
}
