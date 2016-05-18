package com.viettel.vpmt.mobiletv.screen.videodetail.activity;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseActivity;
import com.viettel.vpmt.mobiletv.common.Constants;
import com.viettel.vpmt.mobiletv.screen.videodetail.fragment.VideoDetailFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

/**
 * Video detail activity
 * Created by ThanhTD on 3/22/2016.
 */
public class VideoDetailActivity extends BaseActivity<VideoDetailPresenter> implements VideoDetailView {
    VideoDetailFragment fragment;
    String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoId = getIntent().getStringExtra(Constants.Extras.ID);
        addFragment(videoId);
    }

    private void addFragment(String videoId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.Extras.ID, videoId);
        fragment = new VideoDetailFragment();
        fragment.setArguments(bundle);
        transaction.add(R.id.frame_layout, fragment);
        transaction.commit();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common_detail;
    }

    @Override
    public VideoDetailPresenter onCreatePresenter() {
        return new VideoDetailPresenterImpl(this);
    }

    public VideoDetailFragment getFragment() {
        return fragment;
    }
}
