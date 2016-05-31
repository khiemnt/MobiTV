package com.viettel.vpmt.mobiletv.media.player;

import com.google.android.exoplayer.util.PlayerControl;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.log.Logger;
import com.viettel.vpmt.mobiletv.common.util.DeviceUtils;
import com.viettel.vpmt.mobiletv.common.util.ImageUtils;
import com.viettel.vpmt.mobiletv.common.util.SensorOrientationListener;
import com.viettel.vpmt.mobiletv.media.PlayerFragment;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Formatter;
import java.util.Locale;

/**
 * Custom media controller
 * Created by ThanhTD on 4/8/2016.
 */
public class CustomMediaController extends FrameLayout {

    private static final String TAG = "MediaController";

    private PlayerControl mPlayer;
    private Activity mActivity;
//    private LinearLayout mAnchor;
    private View mControllerLayout;
    private SeekBar mProgress;
    private TextView mEndTime, mCurrentTime;
    private boolean mShowing;
    private boolean mDragging;
    private static final int DEFAULT_TIMEOUT = 3000;
    private static final int FADE_OUT = 1;
    private static final int SHOW_PROGRESS = 2;
    private boolean mFromXml;
    private View.OnClickListener mNextListener, mPrevListener;
    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;
    private View mPlayButtons;
    private ImageView mPauseButton;
    private ImageView mNextButton;
    private ImageView mPrevButton;
    private ImageView mFullScreenIv;
    private LinearLayout mProgressTimeLayout;
    private TextView mLiveNowTv;
    private Handler mHandler = new MessageHandler(this);
    private SensorOrientationListener mSensorListener;

    private ImageView mPlayListIv;

    private LinearLayout mSettingsLayout;
    private LinearLayout mTopBarLayout;
    private ImageView mShareIv;
    private ImageView mLikeIv;

    private TextView mTitleTv;
    private PlayerController mPlayerController;
    private PlayerController.PlayerActionListener mPlayerActionListener;

    private TextView mQualityTv;
    private TextView mReportTv;
    private TextView mSpeedTv;
    private ViewGroup mAnchor;
    private LinearLayout mRoot;

    public CustomMediaController(Context context) {
        this((Activity) context);
    }

    public CustomMediaController(Context context, AttributeSet attrs) {
        this((Activity) context, attrs);
    }

    public CustomMediaController(Activity activity, AttributeSet attrs) {
        super(activity, attrs);
//        mAnchor = null;
        mActivity = activity;
        mFromXml = true;

//        initView();
        initSensor(activity);
    }

    public CustomMediaController(Activity activity) {
        this(activity, null);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
//        if (mAnchor != null)
            initControllerView(this);
    }

    public void setMediaPlayer(PlayerControl player) {
        mPlayer = player;
        updatePausePlay();
//        updateFullScreen();
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

    public void initView() {
//        removeAllViews();
//        FrameLayout.LayoutParams frameParams = new FrameLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//
//        );
//        mAnchor = makeControllerView();
//        addView(mAnchor, frameParams);
//        mAnchor = (LinearLayout) this.getChildAt(0);
//        initControllerView(this);
    }

    protected LinearLayout makeControllerView() {
        LayoutInflater inflate = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout view = (LinearLayout) inflate.inflate(R.layout.media_controller, null);

        initControllerView(view);

        return view;
    }

    private void initControllerView(View rootView) {
        mPauseButton = (ImageView) rootView.findViewById(R.id.pause);
        if (mPauseButton != null) {
            mPauseButton.requestFocus();
            mPauseButton.setOnClickListener(mPauseListener);
        }

        mFullScreenIv = (ImageView) rootView.findViewById(R.id.fullscreen);
        if (mFullScreenIv != null) {
            mFullScreenIv.requestFocus();
            mFullScreenIv.setOnClickListener(mFullscreenListener);
        }

        mControllerLayout = rootView.findViewById(R.id.mediacontroller_control_layout);
        mProgress = (SeekBar) rootView.findViewById(R.id.mediacontroller_progress);
        if (mProgress != null) {
            mProgress.setOnSeekBarChangeListener(mSeekListener);
            mProgress.setMax(1000);
        }

        mEndTime = (TextView) rootView.findViewById(R.id.endTime);
        mCurrentTime = (TextView) rootView.findViewById(R.id.time_current);
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

        mPlayButtons = rootView.findViewById(R.id.mediacontroller_plays_buttons);
        mNextButton = (ImageView) rootView.findViewById(R.id.next);
        mPrevButton = (ImageView) rootView.findViewById(R.id.prev);
        mProgressTimeLayout = (LinearLayout) rootView.findViewById(R.id.mediacontroller_progress_time_layout);
        mLiveNowTv = (TextView) rootView.findViewById(R.id.mediacontroller_live_tv);
        installPrevNextListeners();

        // Top bar actions
//        mRootControlLayout = (LinearLayout) rootView.findViewById(R.id.player_control_root_ll);
        mSettingsLayout = (LinearLayout) rootView.findViewById(R.id.mediacontroller_settings_layout);
        mTitleTv = (TextView) rootView.findViewById(R.id.player_title_tv);
        mQualityTv = (TextView) rootView.findViewById(R.id.player_control_quality);
        mSpeedTv = (TextView) rootView.findViewById(R.id.player_control_speed);
        mReportTv = (TextView) rootView.findViewById(R.id.player_control_report);
        TextView retryTv = (TextView) rootView.findViewById(R.id.control_retry);

        mQualityTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mSettingsLayout.setVisibility(View.INVISIBLE);
                mPlayerController.showQualityPopup();
            }
        });

        mSpeedTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mSettingsLayout.setVisibility(View.INVISIBLE);
                mPlayerController.showSpeedPopup();
            }
        });

        mReportTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mSettingsLayout.setVisibility(View.INVISIBLE);
                mPlayerController.showReportPopup();
            }
        });

        mSettingsLayout.setVisibility(View.INVISIBLE);
        retryTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mSettingsLayout.setVisibility(View.INVISIBLE);
                mPlayerController.retry();
            }
        });

        ImageView moreActionIv = (ImageView) rootView.findViewById(R.id.player_more_action_iv);
        mShareIv = (ImageView) rootView.findViewById(R.id.player_share_iv);
        mTopBarLayout = (LinearLayout) rootView.findViewById(R.id.player_top_bar_ll);
        mLikeIv = (ImageView) rootView.findViewById(R.id.player_like_iv);
        mPlayListIv = (ImageView) rootView.findViewById(R.id.player_playlist_iv);
        ImageView backIv = (ImageView) rootView.findViewById(R.id.player_back_iv);
        mPlayListIv.setVisibility(View.INVISIBLE);
        moreActionIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideTopBar();
                hideCenterPlayback();
                hideBottomController();
                showCenterSettings();
            }
        });

        mLikeIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayerActionListener != null) {
                    mPlayerActionListener.onPlayerLikeClicked();
                }
            }
        });

        mShareIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayerActionListener != null) {
                    mPlayerActionListener.onPlayerShareClicked();
                }
            }
        });

        mPlayListIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayerController.showPlayListPopup();
            }
        });

        rootView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    toggleControlsVisibility();
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    view.performClick();
                }
                return true;
            }
        });
        rootView.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return !(keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_ESCAPE
                        || keyCode == KeyEvent.KEYCODE_MENU) && CustomMediaController.this.dispatchKeyEvent(event);
            }
        });

        backIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
    }

    public void show() {
        show(DEFAULT_TIMEOUT);
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
        if (!mShowing) {
            Logger.e(TAG, "COUNT " + ((ViewGroup) getChildAt(0)).getChildCount());
            setProgress();
            if (mPauseButton != null) {
                mPauseButton.requestFocus();
            }
            disableUnsupportedButtons();

            FrameLayout.LayoutParams tlp = new FrameLayout.LayoutParams(
                    mPlayerController.getRootView().getWidth(),
                    mPlayerController.getRootView().getHeight(),
                    Gravity.BOTTOM
            );
            mAnchor.addView(this, tlp);
            hideCenterSettings();
            showTopBar();
            showBottomController();
            showCenterPlayback();
//            FrameLayout.LayoutParams params = (LayoutParams) mAnchor.getLayoutParams();
//            if (mPlayerController != null) {
//                params.width = mPlayerController.getWidth();
//                params.height = mPlayerController.getHeight();
//            } else {
//                params.width = LayoutParams.MATCH_PARENT;
//                params.height = LayoutParams.MATCH_PARENT;
//            }
//            mAnchor.setLayoutParams(params);
//            mAnchor.invalidate();
//            mAnchor.setVisibility(View.VISIBLE);

//            this.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//            this.setVisibility(View.VISIBLE);
            mShowing = true;
            invalidate();
//            View child = this.getChildAt(0);
//            child.setVisibility(VISIBLE);
//            child.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//            child.invalidate();
        }
        updatePausePlay();

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

        try {
//            mAnchor.setVisibility(View.INVISIBLE);
            mAnchor.removeView(this);
            this.setVisibility(View.INVISIBLE);
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
        show(DEFAULT_TIMEOUT);
        return true;
    }

    @Override
    public boolean onTrackballEvent(MotionEvent ev) {
        show(DEFAULT_TIMEOUT);
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
                show(DEFAULT_TIMEOUT);
                if (mPauseButton != null) {
                    mPauseButton.requestFocus();
                }
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MEDIA_PLAY) {
            if (uniqueDown && !mPlayer.isPlaying()) {
                mPlayer.start();
                updatePausePlay();
                show(DEFAULT_TIMEOUT);
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MEDIA_STOP
                || keyCode == KeyEvent.KEYCODE_MEDIA_PAUSE) {
            if (uniqueDown && mPlayer.isPlaying()) {
                mPlayer.pause();
                updatePausePlay();
                show(DEFAULT_TIMEOUT);
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

        show(DEFAULT_TIMEOUT);
        return super.dispatchKeyEvent(event);
    }

    private View.OnClickListener mPauseListener = new View.OnClickListener() {
        public void onClick(View v) {
            doPauseResume();
            show(DEFAULT_TIMEOUT);
        }
    };

    private View.OnClickListener mFullscreenListener = new View.OnClickListener() {
        public void onClick(View v) {
            doToggleFullscreen();
            show(DEFAULT_TIMEOUT);
        }
    };

    public void updatePausePlay() {
        if ( mPauseButton == null || mPlayer == null) {
            return;
        }

        if (mPlayer.isPlaying()) {
            mPauseButton.setImageResource(android.R.drawable.ic_media_pause);
        } else {
            mPauseButton.setImageResource(android.R.drawable.ic_media_play);
        }
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
        forceToggleFullScreen();
    }

    public void forceToggleFullScreen() {
        if (DeviceUtils.isLandscape(mActivity)) {
            DeviceUtils.forceRotateScreen(mActivity, Configuration.ORIENTATION_PORTRAIT);
        } else {
            DeviceUtils.forceRotateScreen(mActivity, Configuration.ORIENTATION_LANDSCAPE);
        }

        mSensorListener.disable();
        mSensorListener.enable();
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
            show(DEFAULT_TIMEOUT);

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

//        if (mAnchor != null) {
            installPrevNextListeners();

            if (mNextButton != null && !mFromXml) {
                mNextButton.setVisibility(View.VISIBLE);
            }
            if (mPrevButton != null && !mFromXml) {
                mPrevButton.setVisibility(View.VISIBLE);
            }
//        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        Logger.e(TAG, "onConfigurationChanged");
        mProgress.setThumb(ImageUtils.getDrawable(mActivity, R.drawable.ic_player_circle));
        mQualityTv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_player_setting, 0, 0);
        mSpeedTv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_player_motion, 0, 0);
        mReportTv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_player_flag, 0, 0);

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

    public void setPlayerActionListener(PlayerController.PlayerActionListener playerActionListener) {
        mPlayerActionListener = playerActionListener;
    }

    public void setLikeButtonVisibility(int visibility) {
        mLikeIv.setVisibility(visibility);
    }

    public void setShareButtonVisibility(int visibility) {
        mShareIv.setVisibility(visibility);
    }

    public void setTitleText(String title) {
        mTitleTv.setText(title);
    }

    public void setTitleVisibility(int visibility) {
        mTitleTv.setVisibility(visibility);
    }

    public void setPlaylistVisibility(int visibility) {
        mPlayListIv.setVisibility(visibility);
    }

    public void toggleLike(boolean isLike) {
        mLikeIv.setImageResource(isLike ? R.drawable.ic_player_thumb_up_active : R.drawable.ic_player_thumb_up_nomal);
    }

    public void toggleControlsVisibility() {
        if (isShowing()) {
            hide();
        } else {
            show();
        }
    }

    public void showTopBar() {
        setTopBarVisibility(View.VISIBLE);
    }

    public void hideTopBar() {
        setTopBarVisibility(View.INVISIBLE);
    }

    public void showCenterSettings() {
        setCenterSettingsVisibility(View.VISIBLE);
    }

    public void showBottomController() {
        setBottomControllerVisibility(View.VISIBLE);
    }

    public void hideBottomController() {
        setBottomControllerVisibility(View.INVISIBLE);
    }

    public void hideCenterSettings() {
        setCenterSettingsVisibility(View.INVISIBLE);
    }

    public void showCenterPlayback() {
        setCenterPlaybackVisibility(View.VISIBLE);
    }

    public void hideCenterPlayback() {
        setCenterPlaybackVisibility(View.INVISIBLE);
    }

    public void setTopBarVisibility(int visibility) {
        mTopBarLayout.setVisibility(visibility);
    }

    public void setCenterSettingsVisibility(int visibility) {
        mSettingsLayout.setVisibility(visibility);
    }

    public void setCenterPlaybackVisibility(int visibility) {
        mPlayButtons.setVisibility(visibility);
    }

    public void setBottomControllerVisibility(int visibility) {
        mControllerLayout.setVisibility(visibility);
    }


//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
//        setMeasuredDimension(widthSize, (int) (widthSize / PlayerFragment.PLAYER_RATIO));
//        Logger.e(TAG, "WIDH " + widthSize);
//    }

    public void setPlayerController(PlayerController playerController) {
        mPlayerController = playerController;
        mAnchor = mPlayerController.getRootView();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        mRoot = makeControllerView();
        this.removeAllViews();
        this.addView(mRoot, params);
    }

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
