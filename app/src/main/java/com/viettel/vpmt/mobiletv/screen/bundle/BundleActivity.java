package com.viettel.vpmt.mobiletv.screen.bundle;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseActivity;
import com.viettel.vpmt.mobiletv.base.BaseFragment;
import com.viettel.vpmt.mobiletv.common.Constants;
import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.screen.common.CommonHomeActivityPresenter;
import com.viettel.vpmt.mobiletv.screen.common.CommonHomeActivityView;

import butterknife.Bind;


public class BundleActivity extends BaseActivity<CommonHomeActivityPresenter> implements CommonHomeActivityView {
    private static final String TAG = BundleActivity.class.getSimpleName();
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupToolbar();

        // Extra data
        Box.Type boxType = (Box.Type) getIntent().getSerializableExtra(Constants.Extras.BOX_TYPE);
        String id = getIntent().getStringExtra(Constants.Extras.ID);

        // Home Bundle
        addMainFragment(BundleFragment.newInstance(boxType, id));
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
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Add main fragment
     */
    public void addMainFragment(BaseFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.common_content_frame, fragment);
        transaction.commit();
    }
}
