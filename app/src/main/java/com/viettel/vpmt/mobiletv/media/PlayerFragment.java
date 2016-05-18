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
import com.viettel.vpmt.mobiletv.media.player.MobiPlayer;
import com.viettel.vpmt.mobiletv.media.player.PlayerController;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * An activity that plays media using {@link MobiPlayer}.
 */
public abstract class PlayerFragment<P extends BasePresenter, A extends BaseActivity> extends BaseFragment<P, A> {
    @Bind(R.id.shutter)
    View mShutterView;
    @Bind(R.id.video_frame)
    AspectRatioFrameLayout mVideoFrame;
    @Bind(R.id.surface_view)
    SurfaceView mSurfaceView;
    @Bind(R.id.subtitles)
    SubtitleLayout mSubtitleLayout;
    @Bind(R.id.player_root)
    ViewGroup mRoot;
    @Bind(R.id.common_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.view_transparent)
    View transparent;

    protected PlayerController mPlayerController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        if (mPlayerController == null) {
            mPlayerController = new PlayerController(getActivity(), mRoot, mVideoFrame
                    , mSurfaceView, mSubtitleLayout, mShutterView);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPlayerController == null) {
            mPlayerController = new PlayerController(getActivity(), mRoot, mVideoFrame
                    , mSurfaceView, mSubtitleLayout, mShutterView);
        }
        mPlayerController.onResume(getActivity().getIntent());
    }

    @Override
    public void onPause() {
        super.onPause();
        mPlayerController.onPause();
    }

    /**
     * Init Exo player
     */
    protected void initPlayer(Uri uri, int contentType, String fileExtension) {
        mPlayerController.init(uri, contentType, fileExtension);
    }

    /**
     * Init Exo player
     */
    protected void initPlayer(Uri uri, int contentType) {
        mPlayerController.init(uri, contentType, null);
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
}
