package com.viettel.vpmt.mobiletv.screen.channeldetail.activity;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseActivity;
import com.viettel.vpmt.mobiletv.common.Constants;
import com.viettel.vpmt.mobiletv.screen.channeldetail.fragment.ChannelDetailFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

/**
 * Channel Detail activity
 * Created by neo on 4/17/2016.
 */
public class ChannelActivityDetail extends BaseActivity<ChannelDetailPresenter> implements ChannelDetailView {
    private ChannelDetailFragment mFragment;
    private String mChannelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChannelId = getIntent().getStringExtra(Constants.Extras.ID);
        addFragment(mChannelId);
    }

    /**
     * Add main fragment for activity
     */
    private void addFragment(String channelId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // extras bundle
        Bundle bundle = new Bundle();
        bundle.putString(Constants.Extras.ID, channelId);

        // Fragment to be added
        mFragment = new ChannelDetailFragment();
        mFragment.setArguments(bundle);

        transaction.add(R.id.frame_layout, mFragment);
        transaction.commit();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common_detail;
    }

    @Override
    public ChannelDetailPresenter onCreatePresenter() {
        return new ChannelDetailPresenterImpl(this);
    }

    public ChannelDetailFragment getFragment() {
        return mFragment;
    }
}
