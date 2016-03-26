package com.viettel.vpmt.mobiletv.screen.videodetail.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseActivity;
import com.viettel.vpmt.mobiletv.screen.videodetail.fragment.VideoDetailFragment;

/**
 * Created by ThanhTD on 3/22/2016.
 */
public class VideoDetailActivity extends BaseActivity<VideoDetailPresenter> implements VideoDetailView
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addFragment();
    }

    private void addFragment()
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.video_detail_frame_layout, new VideoDetailFragment());
        transaction.commit();
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_video_detail;
    }

    @Override
    public VideoDetailPresenter onCreatePresenter()
    {
        return new VideoDetailPresenterImpl(this);
    }
}