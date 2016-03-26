package com.viettel.vpmt.mobiletv.screen.home;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseActivity;
import com.viettel.vpmt.mobiletv.base.BaseFragment;
import com.viettel.vpmt.mobiletv.base.log.Logger;
import com.viettel.vpmt.mobiletv.common.Constants;
import com.viettel.vpmt.mobiletv.network.ApiConstants;
import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.screen.bundle.BundleActivity;
import com.viettel.vpmt.mobiletv.screen.common.CommonHomeActivityPresenter;
import com.viettel.vpmt.mobiletv.screen.common.CommonHomeActivityView;

import butterknife.Bind;


public class HomeBoxActivity extends BaseActivity<CommonHomeActivityPresenter> implements CommonHomeActivityView {
    private static final java.lang.String TAG = HomeBoxActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupToolbar();
        setupDrawerLayout();
        // Home page
        Bundle args = new Bundle();
        args.putString(Constants.Extras.PATH, ApiConstants.PATH_HOME);
        args.putString(Constants.Extras.SCOPE, ApiConstants.SCOPE_DEFAULT);
        addFragment(new HomeBoxFragment(), args, false);
    }

    private void setupDrawerLayout() {
        // set a custom shadow that overlays the main content when the drawer opens
//        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar,
                R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                return super.onOptionsItemSelected(item);
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE! Make sure to override the method with only a single `Bundle` argument
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Setup view & type for toolbar
     */
    private void setupToolbar() {
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_home_page);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_box_home;
    }


    @Override
    public CommonHomeActivityPresenter onCreatePresenter() {
        return null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Add main fragment
     */
    public void addFragment(BaseFragment fragment, Bundle args, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (args != null) {
            fragment.setArguments(args);
        }

        transaction.add(R.id.common_content_frame, fragment);

        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }

        transaction.commit();
    }

    /**
     * Pop last fragment
     */
    public void popBackStack() {
        getSupportFragmentManager().popBackStack();
    }

    /**
     * Open bundle home screen
     */
    public void openBundleHome(Box.Type type, String id) {
        startActivity(new Intent(this, BundleActivity.class)
        .putExtra(Constants.Extras.BOX_TYPE, type)
        .putExtra(Constants.Extras.ID, id));
    }
}
