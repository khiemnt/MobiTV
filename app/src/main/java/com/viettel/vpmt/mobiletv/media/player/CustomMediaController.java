package com.viettel.vpmt.mobiletv.media.player;

import com.google.android.exoplayer.util.PlayerControl;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.log.Logger;
import com.viettel.vpmt.mobiletv.common.util.DeviceUtils;
import com.viettel.vpmt.mobiletv.common.util.ImageUtils;
import com.viettel.vpmt.mobiletv.common.util.SensorOrientationListener;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

/**
 * Custom media controller
 * Created by ThanhTD on 4/8/2016.
 */
public class CustomMediaController extends FrameLayout {

    private static final String TAG = "MediaController";

    private PlayerControl mPlayer;
    private Activity mActivity;
    private ViewGroup mAnchor;
    private View mRoot;
    private SeekBar mProgress;
    private TextView mEndTime, mCurrentTime;
    private boolean mShowing;
    private boolean mDragging;
    private static final int sDefaultTimeout = 3000;
    private static final int FADE_OUT = 1;
    private static final int SHOW_PROGRESS = 2;
    private boolean mFromXml;
    private boolean mListenersSet;
    private View.OnClickListener mNextListener, mPrevListener;
    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;
    private ImageView mPauseButton;
    private ImageView mNextButton;
    private ImageView mPrevButton;
    private ImageView mFullScreenIv;
    private LinearLayout mProgressTimeLayout;
    private TextView mLiveNowTv;
    private Handler mHandler = new MessageHandler(this);
    private List<PlayerController.VideoPart> mVideoParts;
    private SensorOrientationListener mSensorListener;
    private PlayerController.PartSelectionListener mPartSelectionListener;

    public CustomMediaController(Activity activity, AttributeSet attrs) {
        super(activity, attrs);
        mRoot = null;
        mActivity = activity;
        mFromXml = true;

        initSensor(activity);
    }

    public CustomMediaController(Activity activity) {
        this(activity, null);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        if (mRoot != null)
            initControllerView(mRoot);
    }

    public void setMediaPlayer(PlayerControl player) {
        mPlayer = player;
        updatePausePlay();
        updateFullScreen();
    }

    private void initSensor(Context context) {
        mSensorListener = new SensorOrientationListener(
                context) {

            @Override
            public void onSimpleOrientationChanged(int orientation) {
                // Activity is handled
                Activity activity = mActivity;

                // If orientation currently handled by device
                // Ignore sensor handler
                if (DeviceUtils.isAutoRotate(activity)) {
                    mSensorListener.disable();
                    return;
                }

                // Activity locked rotation
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    Logger.e(TAG, "SENSOR say landscape");

                    // If activity is in landscape mode
                    // Disable sensor and unlock activity orientation auto
                    if (DeviceUtils.isLandscape(activity)) {
                        DeviceUtils.forceRotateScreen(activity, Configuration.ORIENTATION_LANDSCAPE);
                        DeviceUtils.forceRotateScreen(activity, Configuration.ORIENTATION_UNDEFINED);
                        mSensorListener.disable();
                    } else {
                        Logger.d("Wait for user rotate screen to unlock auto rotation");
                    }
                } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    Logger.e(TAG, "SENSOR say portrait");

                    // If activity is in portrait mode
                    // Disable sensor and unlock activity orientation auto
                    if (!DeviceUtils.isLandscape(activity)) {
                        DeviceUtils.forceRotateScreen(activity, Configuration.ORIENTATION_PORTRAIT);
                        DeviceUtils.forceRotateScreen(activity, Configuration.ORIENTATION_UNDEFINED);
                        mSensorListener.disable();
                    } else {
                        Logger.d("Wait for user rotate screen to unlock auto rotation");
                    }
                }
            }
        };
    }

    /**
     * Устанавливает якорь на ту вьюху на которую вы хотите разместить контролы
     * Это может быть например VideoView или SurfaceView
     */
    public void setAnchorView(ViewGroup view) {
        mAnchor = view;

        FrameLayout.LayoutParams frameParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        removeAllViews();
        View v = makeControllerView();
        addView(v, frameParams);
    }

    protected View makeControllerView() {
        LayoutInflater inflate = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRoot = inflate.inflate(R.layout.media_controller, null);

        initControllerView(mRoot);

        return mRoot;
    }

    private void initControllerView(View v) {
        mPauseButton = (ImageView) v.findViewById(R.id.pause);
        if (mPauseButton != null) {
            mPauseButton.requestFocus();
            mPauseButton.setOnClickListener(mPauseListener);
        }

        mFullScreenIv = (ImageView) v.findViewById(R.id.fullscreen);
        if (mFullScreenIv != null) {
            mFullScreenIv.requestFocus();
            mFullScreenIv.setOnClickListener(mFullscreenListener);
        }

        mProgress = (SeekBar) v.findViewById(R.id.mediacontroller_progress);
        if (mProgress != null) {
            mProgress.setOnSeekBarChangeListener(mSeekListener);
            mProgress.setMax(1000);
        }

        mEndTime = (TextView) v.findViewById(R.id.endTime);
        mCurrentTime = (TextView) v.findViewById(R.id.time_current);
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

        mNextButton = (ImageView) v.findViewById(R.id.next);
        mPrevButton = (ImageView) v.findViewById(R.id.prev);
        mProgressTimeLayout = (LinearLayout) v.findViewById(R.id.mediacontroller_progress_time_layout);
        mLiveNowTv = (TextView) v.findViewById(R.id.mediacontroller_live_tv);
        installPrevNextListeners();
    }

    public void show() {
        show(sDefaultTimeout);
    }

    private void disableUnsupportedButtons() {
        if (mPlayer == null) {
            return;
        }

        try {
            if (mPauseButton != null && !mPlayer.canPause()) {
                mPauseButton.setEnabled(false);
            }
        } catch (IncompatibleClassChangeError ex) {
            ex.printStackTrace();
        }
    }

    public void show(int timeout) {
        if (!mShowing && mAnchor != null) {
            setProgress();
            if (mPauseButton != null) {
                mPauseButton.requestFocus();
            }
            disableUnsupportedButtons();

            FrameLayout.LayoutParams tlp = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM
            );

            mAnchor.addView(this, tlp);
            mShowing = true;
        }
        updatePausePlay();
        updateFullScreen();

        mHandler.sendEmptyMessage(SHOW_PROGRESS);

        Message msg = mHandler.obtainMessage(FADE_OUT);
        if (timeout != 0) {
            mHandler.removeMessages(FADE_OUT);
            mHandler.sendMessageDelayed(msg, timeout);
        }
    }

    public boolean isShowing() {
        return mShowing;
    }

    public void hide() {
        if (mAnchor == null) {
            return;
        }

        try {
            mAnchor.removeView(this);
            mHandler.removeMessages(SHOW_PROGRESS);
        } catch (IllegalArgumentException ex) {
            Log.w("MediaController", "already removed");
        }
        mShowing = false;
    }

    private String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    private int setProgress() {
        if (mPlayer == null || mDragging) {
            return 0;
        }

        int position = mPlayer.getCurrentPosition();
        int duration = mPlayer.getDuration();
        if (mProgress != null) {
            if (duration > 0) {
                // use long to avoid overflow
                long pos = 1000L * position / duration;
                mProgress.setProgress((int) pos);
            }
            int percent = mPlayer.getBufferPercentage();
            mProgress.setSecondaryProgress(percent * 10);
        }

        if (mEndTime != null)
            mEndTime.setText(stringForTime(duration));
        if (mCurrentTime != null)
            mCurrentTime.setText(stringForTime(position));

        return position;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        show(sDefaultTimeout);
        return true;
    }

    @Override
    public boolean onTrackballEvent(MotionEvent ev) {
        show(sDefaultTimeout);
        return false;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (mPlayer == null) {
            return true;
        }

        int keyCode = event.getKeyCode();
        final boolean uniqueDown = event.getRepeatCount() == 0
                && event.getAction() == KeyEvent.ACTION_DOWN;
        if (keyCode == KeyEvent.KEYCODE_HEADSETHOOK
                || keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE
                || keyCode == KeyEvent.KEYCODE_SPACE) {
            if (uniqueDown) {
                doPauseResume();
                show(sDefaultTimeout);
                if (mPauseButton != null) {
                    mPauseButton.requestFocus();
                }
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MEDIA_PLAY) {
            if (uniqueDown && !mPlayer.isPlaying()) {
                mPlayer.start();
                updatePausePlay();
                show(sDefaultTimeout);
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MEDIA_STOP
                || keyCode == KeyEvent.KEYCODE_MEDIA_PAUSE) {
            if (uniqueDown && mPlayer.isPlaying()) {
                mPlayer.pause();
                updatePausePlay();
                show(sDefaultTimeout);
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN
                || keyCode == KeyEvent.KEYCODE_VOLUME_UP
                || keyCode == KeyEvent.KEYCODE_VOLUME_MUTE) {
            return super.dispatchKeyEvent(event);
        } else if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU) {
            if (uniqueDown) {
                hide();
            }
            return true;
        }

        show(sDefaultTimeout);
        return super.dispatchKeyEvent(event);
    }

    private View.OnClickListener mPauseListener = new View.OnClickListener() {
        public void onClick(View v) {
            doPauseResume();
            show(sDefaultTimeout);
        }
    };

    private View.OnClickListener mFullscreenListener = new View.OnClickListener() {
        public void onClick(View v) {
            doToggleFullscreen();
            show(sDefaultTimeout);
        }
    };

    public void updatePausePlay() {
        if (mRoot == null || mPauseButton == null || mPlayer == null) {
            return;
        }

        if (mPlayer.isPlaying()) {
            mPauseButton.setImageResource(android.R.drawable.ic_media_pause);
        } else {
            mPauseButton.setImageResource(android.R.drawable.ic_media_play);
        }
    }

    public void updateFullScreen() {
        if (mRoot == null || mFullScreenIv == null || mPlayer == null) {
            return;
        }

//        if (mPlayer.isFullScreen()) {
//            mFullScreenIv.setImageResource(R.drawable.ic_media_fullscreen_shrink);
//        } else {
//            mFullScreenIv.setImageResource(R.drawable.ic_media_fullscreen_stretch);
//        }
    }

    private void doPauseResume() {
        if (mPlayer == null) {
            return;
        }

        if (mPlayer.isPlaying()) {
            mPlayer.pause();
        } else {
            mPlayer.start();
        }
        updatePausePlay();
    }

    private void doToggleFullscreen() {
        if (mPlayer == null) {
            return;
        }
//        toggleFullScreen();
        forceToggleFullScreen();
    }

    public void forceToggleFullScreen() {
//        DeviceUtils.setRequestedOrientationSensor((Activity) mActivity, false);
        if (DeviceUtils.isLandscape(mActivity)) {
            DeviceUtils.forceRotateScreen(mActivity, Configuration.ORIENTATION_PORTRAIT);
        } else {
            DeviceUtils.forceRotateScreen(mActivity, Configuration.ORIENTATION_LANDSCAPE);
        }

        mSensorListener.disable();
        mSensorListener.enable();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                DeviceUtils.forceRotateScreen((Activity) mActivity, Configuration.ORIENTATION_UNDEFINED);
//                DeviceUtils.setRequestedOrientationSensor((Activity) mActivity, true);
            }
        }, 1000);
    }

    public void toggleFullScreen() {
        //обрабатываем нажатие на кнопку увеличения видео в весь экран
        DisplayMetrics metrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) mAnchor.getLayoutParams();
        params.width = metrics.widthPixels;
        params.height = metrics.heightPixels;
        params.leftMargin = 0;
        mAnchor.setLayoutParams(params);
    }

    private SeekBar.OnSeekBarChangeListener mSeekListener = new SeekBar.OnSeekBarChangeListener() {
        public void onStartTrackingTouch(SeekBar bar) {
            show(3600000);

            mDragging = true;
            mHandler.removeMessages(SHOW_PROGRESS);
        }

        public void onProgressChanged(SeekBar bar, int progress, boolean fromuser) {
            if (mPlayer == null) {
                return;
            }

            if (!fromuser) {
                return;
            }

            long duration = mPlayer.getDuration();
            long newposition = (duration * progress) / 1000L;
            mPlayer.seekTo((int) newposition);
            if (mCurrentTime != null)
                mCurrentTime.setText(stringForTime((int) newposition));
        }

        public void onStopTrackingTouch(SeekBar bar) {
            mDragging = false;
            setProgress();
            updatePausePlay();
            show(sDefaultTimeout);

            mHandler.sendEmptyMessage(SHOW_PROGRESS);
        }
    };

    @Override
    public void setEnabled(boolean enabled) {
        if (mPauseButton != null) {
            mPauseButton.setEnabled(enabled);
        }
        if (mNextButton != null) {
            mNextButton.setEnabled(enabled && mNextListener != null);
        }
        if (mPrevButton != null) {
            mPrevButton.setEnabled(enabled && mPrevListener != null);
        }
        if (mProgress != null) {
            mProgress.setEnabled(enabled);
        }
        disableUnsupportedButtons();
        super.setEnabled(enabled);
    }

    private View.OnClickListener mRewListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (mPlayer == null) {
                return;
            }

            int pos = mPlayer.getCurrentPosition();
            pos -= 5000; // милисекунд
            mPlayer.seekTo(pos);
            setProgress();

            show(sDefaultTimeout);
        }
    };

    private View.OnClickListener mFfwdListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (mPlayer == null) {
                return;
            }

            int pos = mPlayer.getCurrentPosition();
            pos += 15000; // милисекунд
            mPlayer.seekTo(pos);
            setProgress();

            show(sDefaultTimeout);
        }
    };

    private void installPrevNextListeners() {
        if (mNextButton != null) {
            mNextButton.setOnClickListener(mNextListener);
            mNextButton.setEnabled(mNextListener != null);
        }

        if (mPrevButton != null) {
            mPrevButton.setOnClickListener(mPrevListener);
            mPrevButton.setEnabled(mPrevListener != null);
        }
    }

    public void setPrevNextListeners(View.OnClickListener next, View.OnClickListener prev) {
        mNextListener = next;
        mPrevListener = prev;
        mListenersSet = true;

        if (mRoot != null) {
            installPrevNextListeners();

            if (mNextButton != null && !mFromXml) {
                mNextButton.setVisibility(View.VISIBLE);
            }
            if (mPrevButton != null && !mFromXml) {
                mPrevButton.setVisibility(View.VISIBLE);
            }
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        Logger.e(TAG, "onConfigurationChanged");
        mProgress.setThumb(ImageUtils.getDrawable(mActivity, R.drawable.ic_player_circle));
        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                mFullScreenIv.setImageResource(R.drawable.ic_player_fullscreen_exit);
                break;

            case Configuration.ORIENTATION_PORTRAIT:
                mFullScreenIv.setImageResource(R.drawable.ic_player_ic_fullscreen);
                break;

        }
    }

    public void setNextVisibility(int visibility) {
        if (mNextButton != null) {
            mNextButton.setVisibility(visibility);
        }
    }

    public void setNextEnabled(boolean enabled) {
        if (mNextButton != null) {
            mNextButton.setEnabled(enabled);
        }
    }

    public void setPreviousVisibility(int visibility) {
        if (mPrevButton != null) {
            mPrevButton.setVisibility(visibility);
        }
    }
    public void setPreviousEnabled(boolean enabled) {
        if (mPrevButton != null) {
            mPrevButton.setEnabled(enabled);
        }
    }

    public void setProgressTimeLayoutVisibility(int visibility) {
        mProgressTimeLayout.setVisibility(visibility);
    }

    public void setLiveNowVisibility(int visibility) {
        mLiveNowTv.setVisibility(visibility);
    }

    public void setVideoParts(List<PlayerController.VideoPart> videoParts, PlayerController.PartSelectionListener partSelectionListener) {
        mVideoParts = videoParts;
        mPartSelectionListener = partSelectionListener;
    }

    //    public interface MediaPlayerControl {
//        void start();
//
//        void pause();
//
//        int getDuration();
//
//        int getCurrentPosition();
//
//        void seekTo(int pos);
//
//        boolean isPlaying();
//
//        int getBufferPercentage();
//
//        boolean canPause();
//
//        boolean canSeekBackward();
//
//        boolean canSeekForward();
//
//        boolean isFullScreen();
//
//        void toggleFullScreen();
//    }

    private static class MessageHandler extends Handler {
        private final WeakReference<CustomMediaController> mView;

        MessageHandler(CustomMediaController view) {
            mView = new WeakReference<>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            CustomMediaController view = mView.get();
            if (view == null || view.mPlayer == null) {
                return;
            }

            int pos;
            switch (msg.what) {
                case FADE_OUT:
                    view.hide();
                    break;
                case SHOW_PROGRESS:
                    pos = view.setProgress();
                    if (!view.mDragging && view.mShowing && view.mPlayer.isPlaying()) {
                        msg = obtainMessage(SHOW_PROGRESS);
                        sendMessageDelayed(msg, 1000 - (pos % 1000));
                    }
                    break;
            }
        }
    }
}
