package com.viettel.vpmt.mobiletv.screen.home;

import com.google.gson.Gson;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseActivity;
import com.viettel.vpmt.mobiletv.base.BaseFragment;
import com.viettel.vpmt.mobiletv.base.log.Logger;
import com.viettel.vpmt.mobiletv.common.Constants;
import com.viettel.vpmt.mobiletv.common.pref.PrefManager;
import com.viettel.vpmt.mobiletv.network.ApiConstants;
import com.viettel.vpmt.mobiletv.network.ServiceBuilder;
import com.viettel.vpmt.mobiletv.network.callback.BaseCallback;
import com.viettel.vpmt.mobiletv.network.dto.PlayerSetting;
import com.viettel.vpmt.mobiletv.screen.bundle.BundleFragment;
import com.viettel.vpmt.mobiletv.screen.common.CommonHomeActivityPresenter;
import com.viettel.vpmt.mobiletv.screen.common.CommonHomeActivityView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import butterknife.Bind;


public class HomeBoxActivity extends BaseActivity<CommonHomeActivityPresenter> implements
        CommonHomeActivityView, FragmentManager.OnBackStackChangedListener {
    private static final java.lang.String TAG = HomeBoxActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Bind(R.id.nav_view)
    NavigationView mNavigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupToolbar();
        setupDrawerLayout();

        handleNavMenuItem(R.id.nav_home);

        getSupportFragmentManager().addOnBackStackChangedListener(this);
        mNavigationView.setCheckedItem(R.id.nav_home);

        getSettings();
    }

    /**
     * Get Settings from server
     */
    private void getSettings() {
        if (PrefManager.getSettings(this) != null) {
            return;
        }

        String header = PrefManager.getHeader(this);
        ServiceBuilder.getService().getSettings(header)
                .enqueue(new BaseCallback<PlayerSetting>() {
                    @Override
                    public void onError(String errorCode, String errorMessage) {
                        Logger.e(TAG, "Get SETTINGS error " + errorMessage);
                    }

                    @Override
                    public void onResponse(PlayerSetting data) {
                        PrefManager.saveSettings(HomeBoxActivity.this, data);
                    }
                });
    }

    /**
     * Setup configurations of Drawer layout
     */
    private void setupDrawerLayout() {
        // set a custom shadow that overlays the main content when the drawer opens
//        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mDrawerToggle.setDrawerIndicatorEnabled(true);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                syncActionBarHomeButtonState();
                invalidateOptionsMenu();
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                return super.onOptionsItemSelected(item);
            }
        };

        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Back button click
                popBackStack();
            }
        });

        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        handleNavMenuItem(menuItem.getItemId());
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    /**
     * Handle NavigationView menu item clicked
     */
    private void handleNavMenuItem(int menuItemId) {
        String scope = null;
        String path = null;
        String title = null;

        switch (menuItemId) {
            case R.id.nav_home:
                scope = ApiConstants.SCOPE_DEFAULT;
                path = ApiConstants.PATH_HOME;
                title = getString(R.string.title_home_page);
                break;
            case R.id.nav_tv_home:
                scope = ApiConstants.SCOPE_CHANNEL;
                path = ApiConstants.PATH_HOME_CHANNEL;
                title = getString(R.string.title_tv_home);
                break;
            case R.id.nav_video_home:
                scope = ApiConstants.SCOPE_VIDEO;
                path = ApiConstants.PATH_HOME_VIDEO;
                title = getString(R.string.title_video_home);
                break;
            case R.id.nav_film_home:
                scope = ApiConstants.SCOPE_FILM;
                path = ApiConstants.PATH_HOME_FILM;
                title = getString(R.string.title_film_home);
                break;
            case R.id.nav_kids_home:
                scope = ApiConstants.SCOPE_DEFAULT;
                path = ApiConstants.PATH_HOME_KIDS;
                title = getString(R.string.title_kids_home);
                break;
            case R.id.nav_game_home:
                scope = ApiConstants.SCOPE_DEFAULT;
                path = ApiConstants.PATH_HOME_GAME;
                title = getString(R.string.title_game_home);
                break;
        }

        if (scope != null && path != null) {
            // Home page
            Bundle args = new Bundle();
            args.putString(Constants.Extras.SCOPE, scope);
            args.putString(Constants.Extras.PATH, path);
            replaceFragment(HomeBoxFragment.newInstance(scope, path, title), null, false);
        } else {
            BaseFragment fragment = new HomeFakeFragment();
            replaceFragment(fragment, null, false);
        }
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
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
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
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.isDrawerIndicatorEnabled() && mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
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
     * Replace main fragment
     */
    public void replaceFragment(BaseFragment fragment, Bundle args, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (args != null) {
            fragment.setArguments(args);
        }

        transaction.replace(R.id.common_content_frame, fragment);

        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }

        transaction.commit();
    }

    /**
     * Add child fragment
     */
    public void addChildFragment(BaseFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // TODO check animation
//        transaction.setCustomAnimations(R.animator.fragment_slide_left_enter,
//                R.animator.fragment_slide_left_exit,
//                R.animator.fragment_slide_right_enter,
//                R.animator.fragment_slide_right_exit);
        transaction.add(R.id.common_content_frame, fragment);

        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    /**
     * Pop last fragment
     */
    public void popBackStack() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onBackStackChanged() {
        syncActionBarHomeButtonState();

        FragmentManager manager = getSupportFragmentManager();
        if (manager != null) {
            for (Fragment fragment : manager.getFragments()) {
                if (fragment instanceof HomeBoxFragment || fragment instanceof BundleFragment) {
                    fragment.onResume();
                }
            }
        }
    }

    /**
     * Sync action bar home button state
     */
    private void syncActionBarHomeButtonState() {
        int backStackEntryCount =
                getSupportFragmentManager().getBackStackEntryCount();
        boolean hasChild = backStackEntryCount > 0;
        mDrawerToggle.setDrawerIndicatorEnabled(!hasChild);
        getSupportActionBar().setDisplayHomeAsUpEnabled(hasChild);
        getSupportActionBar().setHomeButtonEnabled(hasChild);
        mDrawerToggle.syncState();
        invalidateOptionsMenu();
    }

    @Override
    protected void onDestroy() {
        getSupportFragmentManager().removeOnBackStackChangedListener(this);
        super.onDestroy();
    }
}
