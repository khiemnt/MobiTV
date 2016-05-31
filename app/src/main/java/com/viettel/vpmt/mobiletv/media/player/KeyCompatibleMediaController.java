package com.viettel.vpmt.mobiletv.media.player;

import com.google.android.exoplayer.util.PlayerControl;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

/**
 * KeyCompatibleMediaController
 * Created by neo on 5/31/2016.
 */
public class KeyCompatibleMediaController extends CustomMediaController {

    private PlayerControl mPlayerControl;

    public KeyCompatibleMediaController(Context context) {
        super(context);
    }

    public KeyCompatibleMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setMediaPlayer(PlayerControl playerControl) {
        super.setMediaPlayer(playerControl);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (mPlayerControl.canSeekForward() && keyCode == KeyEvent.KEYCODE_MEDIA_FAST_FORWARD) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                mPlayerControl.seekTo(mPlayerControl.getCurrentPosition() + 15000); // milliseconds
                show();
            }
            return true;
        } else if (mPlayerControl.canSeekBackward() && keyCode == KeyEvent.KEYCODE_MEDIA_REWIND) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                mPlayerControl.seekTo(mPlayerControl.getCurrentPosition() - 5000); // milliseconds
                show();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}