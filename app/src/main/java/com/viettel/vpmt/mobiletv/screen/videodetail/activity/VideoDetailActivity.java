package com.viettel.vpmt.mobiletv.screen.videodetail.activity;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseActivity;
import com.viettel.vpmt.mobiletv.screen.videodetail.fragment.VideoDetailFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by ThanhTD on 3/22/2016.
 */
public class VideoDetailActivity extends BaseActivity<VideoDetailPresenter> implements VideoDetailView {
    VideoDetailFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragment();
    }

    private void addFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putFloat("videoId", 776504);
        fragment = new VideoDetailFragment();
        fragment.setArguments(bundle);
        transaction.add(R.id.frame_layout, fragment);
        transaction.commit();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_or_film_detail;
    }

    @Override
    public VideoDetailPresenter onCreatePresenter() {
        return new VideoDetailPresenterImpl(this);
    }

    public VideoDetailFragment getFragment() {
        return fragment;
    }
}
