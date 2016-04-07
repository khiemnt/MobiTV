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
import com.viettel.vpmt.mobiletv.media.player.MobiPlayer;
import com.viettel.vpmt.mobiletv.media.player.PlayerController;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * An activity that plays media using {@link MobiPlayer}.
 */
public abstract class PlayerActivity extends BaseActivity {
    @Bind(R.id.shutter)
    View shutterView;
    @Bind(R.id.video_frame)
    AspectRatioFrameLayout videoFrame;
    @Bind(R.id.surface_view)
    SurfaceView surfaceView;
    @Bind(R.id.subtitles)
    SubtitleLayout subtitleLayout;
    @Bind(R.id.player_root)
    View root;

    private PlayerController mPlayerController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mPlayerController = new PlayerController(this, root, videoFrame
                , surfaceView, subtitleLayout, shutterView);
    }

    @Override
    public void onNewIntent(Intent intent) {
        mPlayerController.onNewIntent(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPlayerController.onResume(getIntent());
    }

    @Override
    public void onPause() {
        super.onPause();
        mPlayerController.onPause();
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
            Toast.makeText(getApplicationContext(), R.string.storage_permission_denied,
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
