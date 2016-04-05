package com.viettel.vpmt.mobiletv.playercustom;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseActivity;
import com.viettel.vpmt.mobiletv.playercustom.fragment.PlayerCustomFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by ThanhTD on 4/5/2016.
 */
public class PlayerCustomActivity extends BaseActivity<PlayerCustomPresenter> implements PlayerCustomView {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragment();
    }

    private void addFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_layout, new PlayerCustomFragment());
        transaction.commit();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_demo_player;
    }

    @Override
    public PlayerCustomPresenter onCreatePresenter() {
        return new PlayerCustomPresenterImpl(this);
    }
}
