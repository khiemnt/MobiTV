/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.viettel.vpmt.mobiletv.media;

import com.google.android.exoplayer.AspectRatioFrameLayout;
import com.google.android.exoplayer.text.SubtitleLayout;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseActivity;
import com.viettel.vpmt.mobiletv.base.BaseFragment;
import com.viettel.vpmt.mobiletv.base.BasePresenter;
import com.viettel.vpmt.mobiletv.base.log.Logger;
import com.viettel.vpmt.mobiletv.common.Constants;
import com.viettel.vpmt.mobiletv.common.pref.PrefManager;
import com.viettel.vpmt.mobiletv.media.player.MobiPlayer;
import com.viettel.vpmt.mobiletv.media.player.PlayerController;
import com.viettel.vpmt.mobiletv.network.ServiceBuilder;
import com.viettel.vpmt.mobiletv.network.callback.BaseCallback;
import com.viettel.vpmt.mobiletv.network.dto.PlayerSetting;
import com.viettel.vpmt.mobiletv.network.dto.ScheduleData;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;

/**
 * An Fragment that plays media using {@link MobiPlayer}.
 */
public abstract class PlayerFragment<P extends BasePresenter, A extends BaseActivity> extends BaseFragment<P, A>
        implements PlayerController.StateListener, PlayerController.OnReportSelectionListener, PlayerController.PlayerActionListener {
    private static final float PLAYER_RATIO = 16.0f / 9;
    @Bind(R.id.shutter)
    View mShutterView;
    @Bind(R.id.video_frame)
    AspectRatioFrameLayout mVideoFrame;
    @Bind(R.id.player_surface_view)
    SurfaceView mSurfaceView;
    @Bind(R.id.subtitles)
    SubtitleLayout mSubtitleLayout;
    @Bind(R.id.player_root)
    ViewGroup mRoot;
    @Bind(R.id.common_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.view_transparent)
    View transparent;
    @Bind(R.id.player_control_quantity)
    TextView mQualityTv;
    @Bind(R.id.player_control_report)
    TextView mReportTv;
    @Bind(R.id.player_control_speed)
    TextView mSpeedTv;

    protected PlayerController mPlayerController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        if (mPlayerController == null) {
            initPlayerController();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPlayerController == null) {
            initPlayerController();
        }
        mPlayerController.onResume();
    }

    /**
     * Initialize Player Controller
     */
    private void initPlayerController() {
        String coverUrl = getArguments().getString(Constants.Extras.COVER_IMAGE_URL, null);
        Logger.e("Cover uu " + coverUrl);
        mPlayerController = new PlayerController(getActivity(), mRoot, mVideoFrame
                , mSurfaceView, mSubtitleLayout, mShutterView, coverUrl);
        mPlayerController.setAspectRatio(PLAYER_RATIO);
        firstConfigPlayerController();

        String title = getArguments().getString(Constants.Extras.TITLE);
        setTitle(title);
        mPlayerController.setOnReportSelectionListener(this);
    }

    protected abstract void firstConfigPlayerController();

    @Override
    public void onPause() {
        super.onPause();
        mPlayerController.onPause();
    }

    protected void setTitle(String title) {
        mPlayerController.setTitle(title);
    }

    /**
     * Init Exo player
     */
//    protected void initPlayer(Uri uri, int contentType, String fileExtension) {
//        mPlayerController.init(uri, contentType, fileExtension);
//    }

    /**
     * Init Exo player
     */
    protected void initPlayer(Uri uri, int contentType, String title, String coverImageUrl) {
        mPlayerController.init(uri, contentType, null, coverImageUrl);
        mPlayerController.setTitle(title);
    }
    /**
     * Init Exo player
     */
    protected void initPlayer(Uri uri, int contentType, String coverImageUrl) {
        mPlayerController.init(uri, contentType, null, coverImageUrl);
        mPlayerController.setPlayerActionListener(this);
    }

    protected void initPlayer(Uri uri, int contentType) {
        initPlayer(uri, contentType, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayerController.onDestroy();
    }

    // Permission request listener method
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mPlayerController.preparePlayer(true);
        } else {
            Toast.makeText(getActivity().getApplicationContext(), R.string.storage_permission_denied,
                    Toast.LENGTH_LONG).show();
            getActivity().finish();
        }
    }

    @Override
    public void showProgress() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
            transparent.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
            transparent.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPreparing() {

    }

    @Override
    public void onBuffering() {

    }

    @Override
    public void onIdle() {

    }

    @Override
    public void onReady() {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mQualityTv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_player_setting,0,0);
        mSpeedTv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_player_motion,0,0);
        mReportTv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_player_flag,0,0);
        mPlayerController.onConfigurationChanged(newConfig);

        int orientation = newConfig.orientation;
        switch (orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                mPlayerController.setTitleVisibility(View.VISIBLE);
                break;

            case Configuration.ORIENTATION_PORTRAIT:
                mPlayerController.setTitleVisibility(View.INVISIBLE);
                break;

        }
    }

    @Override
    public void onReportSelected(int position) {
        PlayerSetting playerSetting = PrefManager.getSettings(getActivity());
        if (playerSetting == null) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.error_cant_report_now), Toast.LENGTH_SHORT).show();
            return;
        }

        List<PlayerSetting.ErrorType> errorTypes = playerSetting.getErrorType();
        if (errorTypes == null || errorTypes.size() == 0) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.error_cant_report_now), Toast.LENGTH_SHORT).show();
            return;
        }

        String header = PrefManager.getHeader(getActivity());
        PlayerSetting.ErrorType errorType = errorTypes.get(position);
        ServiceBuilder.getService().sendFeedback(header, String.valueOf(errorType.getId()), errorType.getContent())
        .enqueue(new BaseCallback<ScheduleData>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                Toast.makeText(getActivity(), getString(R.string.error_cant_report_now), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(ScheduleData data) {
                Toast.makeText(getActivity(), getString(R.string.send_report_successful), Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void shareContent(String contentUrl) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");

        intent.putExtra(Intent.EXTRA_TEXT, contentUrl);
        getActivity().startActivity(Intent.createChooser(intent, getString(R.string.title_share)));
    }
}
