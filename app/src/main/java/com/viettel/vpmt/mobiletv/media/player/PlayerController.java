package com.viettel.vpmt.mobiletv.media.player;

import com.google.android.exoplayer.AspectRatioFrameLayout;
import com.google.android.exoplayer.ExoPlaybackException;
import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.MediaCodecTrackRenderer;
import com.google.android.exoplayer.MediaCodecUtil;
import com.google.android.exoplayer.MediaFormat;
import com.google.android.exoplayer.audio.AudioCapabilities;
import com.google.android.exoplayer.audio.AudioCapabilitiesReceiver;
import com.google.android.exoplayer.drm.UnsupportedDrmException;
import com.google.android.exoplayer.metadata.id3.GeobFrame;
import com.google.android.exoplayer.metadata.id3.Id3Frame;
import com.google.android.exoplayer.metadata.id3.PrivFrame;
import com.google.android.exoplayer.metadata.id3.TxxxFrame;
import com.google.android.exoplayer.text.CaptionStyleCompat;
import com.google.android.exoplayer.text.Cue;
import com.google.android.exoplayer.text.SubtitleLayout;
import com.google.android.exoplayer.util.MimeTypes;
import com.google.android.exoplayer.util.PlayerControl;
import com.google.android.exoplayer.util.Util;
import com.google.android.exoplayer.util.VerboseLogUtil;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.log.Logger;
import com.viettel.vpmt.mobiletv.common.pref.PrefManager;
import com.viettel.vpmt.mobiletv.common.util.DeviceUtils;
import com.viettel.vpmt.mobiletv.common.util.ImageUtils;
import com.viettel.vpmt.mobiletv.media.EventLogger;
import com.viettel.vpmt.mobiletv.media.SmoothStreamingTestMediaDrmCallback;
import com.viettel.vpmt.mobiletv.network.dto.PlayerSetting;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.CaptioningManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;
import java.util.Locale;

/**
 * Player Controller
 * Created by neo on 3/29/2016.
 */
public class PlayerController implements SurfaceHolder.Callback, MobiPlayer.Listener,
        MobiPlayer.CaptionListener, MobiPlayer.Id3MetadataListener, AudioCapabilitiesReceiver.Listener {
    // For use within demo app code.
//    public static final String CONTENT_ID_EXTRA = "content_id";
//    public static final String CONTENT_TYPE_EXTRA = "content_type";
//    public static final String PROVIDER_EXTRA = "provider";

    // For use when launching the demo app using adb.
//    private static final String CONTENT_EXT_EXTRA = "type";

    private static final String TAG = "PlayerActivity";
    private static final int MENU_GROUP_TRACKS = 1;
    private static final int ID_OFFSET = 2;
    private static final int RADIO_BUTTON_ID_OFFSET = 1;

    // Speed selections
    private static final int SPEED_POSITION_05 = 0;
    private static final int SPEED_POSITION_10 = 1;
    private static final int SPEED_POSITION_15 = 2;
    private static final int SPEED_POSITION_20 = 3;

    private static final float SPEED_05 = 0.5f;
    private static final float SPEED_10 = 1.0f;
    private static final float SPEED_15 = 1.5f;
    private static final float SPEED_20 = 2.0f;

    private static final CookieManager DEFAULT_COOKIE_MANAGER;

    static {
        DEFAULT_COOKIE_MANAGER = new CookieManager();
        DEFAULT_COOKIE_MANAGER.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }

    private final ImageView mPlayListTv;

    private View mRootView;
    private LinearLayout mRootControlLayout;
    private LinearLayout mActionLayout;
    private LinearLayout mMoreActionLayout;
    private ImageView mShareIv;
    private ImageView mLikeIv;

    private TextView mTitleTv;
    private ImageView mCoverIv;
    // This flag use to display cover 1 time only
    private boolean mIsCoverHasBeenShown = false;
    private String mCoverImageUrl;

    private EventLogger mEventLogger;
    private CustomMediaController mMediaController;
    private AspectRatioFrameLayout mVideoFrame;
    private SurfaceView mSurfaceView;
    private View mShutterView;
    private SubtitleLayout mSubtitleLayout;

    private MobiPlayer mPlayer;
    private boolean mPlayerNeedsPrepare;

    private long mPlayerPosition;
    private boolean mEnableBackgroundAudio;

    private Uri mContentUri;
    private int mContentType;
//    private String mContentId;
//    private String mProvider;

    private AudioCapabilitiesReceiver mAudioCapabilitiesReceiver;
    private Activity mActivity;
    private StateListener mStateListener;
    private String mTitle;
    private int mPartPosition = 0;
    private int mSpeedSelectionPosition = SPEED_POSITION_10;
    private List<VideoPart> mVideoParts;
    private PartSelectionListener mPartSelectionListener;
    private OnReportSelectionListener mOnReportSelectionListener;
    private PlayerActionListener mPlayerActionListener;

    public PlayerController(Activity activity, final ViewGroup rootView,
                            AspectRatioFrameLayout videoFrame, SurfaceView surfaceView,
                            SubtitleLayout subtitleLayout, View shutterView, String coverImageUrl) {
        mRootView = rootView;
        mActivity = activity;
        mShutterView = shutterView;
        mRootControlLayout = (LinearLayout) rootView.findViewById(R.id.player_control_root_ll);
        mActionLayout = (LinearLayout) rootView.findViewById(R.id.controls_root);
        mTitleTv = (TextView) rootView.findViewById(R.id.player_title_tv);
        TextView qualityTv = (TextView) rootView.findViewById(R.id.player_control_quantity);
        TextView speedTv = (TextView) rootView.findViewById(R.id.player_control_speed);
        TextView reportTv = (TextView) rootView.findViewById(R.id.player_control_report);
        TextView retryTv = (TextView) rootView.findViewById(R.id.control_retry);

        mCoverIv = (ImageView) rootView.findViewById(R.id.player_cover_iv);
        loadCover(activity, coverImageUrl);

        qualityTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionLayout.setVisibility(View.GONE);
                showQualityPopup();
            }
        });

        speedTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionLayout.setVisibility(View.GONE);
                showSpeedPopup();
            }
        });

        reportTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionLayout.setVisibility(View.GONE);
                showReportPopup();
            }
        });

        mActionLayout.setVisibility(View.GONE);
        retryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionLayout.setVisibility(View.GONE);
                retry();
            }
        });

        ImageView moreActionIv = (ImageView) rootView.findViewById(R.id.player_more_action_iv);
        mShareIv = (ImageView) rootView.findViewById(R.id.player_share_iv);
        mMoreActionLayout = (LinearLayout) rootView.findViewById(R.id.player_top_bar_ll);
        mLikeIv = (ImageView) rootView.findViewById(R.id.player_like_iv);
        mPlayListTv = (ImageView) rootView.findViewById(R.id.player_playlist_iv);
        ImageView backIv = (ImageView) rootView.findViewById(R.id.player_back_iv);
        mPlayListTv.setVisibility(View.GONE);
        moreActionIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaController.hide();
                mMoreActionLayout.setVisibility(View.GONE);
                mActionLayout.setVisibility(View.VISIBLE);
                mActionLayout.invalidate();
                mMoreActionLayout.invalidate();
//                Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.anim);
//                animation.setDuration(500);
//                mActionLayout.setAnimation(animation);
//                mActionLayout.animate();
//                animation.start();
//                toggleFullScreen();
            }
        });

        mLikeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayerActionListener != null) {
                    mPlayerActionListener.onPlayerLikeClicked();
                }
            }
        });

        mShareIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayerActionListener != null) {
                    mPlayerActionListener.onPlayerShareClicked();
                }
            }
        });

        mPlayListTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPlayListPopup();
            }
        });

        rootView.setOnTouchListener(new View.OnTouchListener() {
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
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return !(keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_ESCAPE
                        || keyCode == KeyEvent.KEYCODE_MENU) && mMediaController.dispatchKeyEvent(event);
            }
        });

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });

        mVideoFrame = videoFrame;
        mSurfaceView = surfaceView;
        surfaceView.getHolder().addCallback(this);
        mSubtitleLayout = subtitleLayout;

        mMediaController = new KeyCompatibleMediaController(mActivity);
        mMediaController.setAnchorView(rootView);

        CookieHandler currentHandler = CookieHandler.getDefault();
        if (currentHandler != DEFAULT_COOKIE_MANAGER) {
            CookieHandler.setDefault(DEFAULT_COOKIE_MANAGER);
        }

        mAudioCapabilitiesReceiver = new AudioCapabilitiesReceiver(mActivity, this);
        mAudioCapabilitiesReceiver.register();

    }

    private void loadCover(Context activity, String coverImageUrl) {
        mIsCoverHasBeenShown = false;
        mCoverImageUrl = coverImageUrl;
        ImageUtils.loadImage(activity, coverImageUrl, mCoverIv, true);
    }

//    public void toggleFullScreen() {
//        DisplayMetrics metrics = new DisplayMetrics();
//        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) mRootView.getLayoutParams();
//        params.width = metrics.widthPixels;
//        params.height = metrics.heightPixels;
//        params.leftMargin = 0;
//        mRootView.requestFocus();
//        mRootView.setLayoutParams(params);
//    }

//    public void onNewIntent(Intent intent) {
//        releasePlayer();
//        mPlayerPosition = 0;
//        if (mActivity != null) {
//            mActivity.setIntent(intent);
//        }
//    }

    public void init(Uri uri, int contentType, String fileExtension, String coverImageUrl) {
        mContentUri = uri;
        if (coverImageUrl != null) {
            loadCover(mActivity, coverImageUrl);
        }

        releasePlayer();
        mActionLayout.setVisibility(View.GONE);
        mMoreActionLayout.setVisibility(View.GONE);
        if (contentType < 0 || contentType > 3) {
            mContentType = inferContentType(mContentUri, fileExtension);
        } else {
            mContentType = contentType;
        }

        configureSubtitleView();
        if (mPlayer == null) {
            if (!maybeRequestPermission()) {
                preparePlayer(true);
            }
        } else {
            mPlayer.setBackgrounded(false);
        }
        mPlayer.seekTo(0);
    }

    public void onResume() {
//        mContentUri = intent.getData();
//        mContentType = intent.getIntExtra(CONTENT_TYPE_EXTRA,
//                inferContentType(mContentUri, intent.getStringExtra(CONTENT_EXT_EXTRA)));
//        mContentId = intent.getStringExtra(CONTENT_ID_EXTRA);
//        mProvider = intent.getStringExtra(PROVIDER_EXTRA);
//        configureSubtitleView();

        if (mContentUri == null) {
            return;
        }

        if (mPlayer == null) {
            if (!maybeRequestPermission()) {
                preparePlayer(true);
            }
        } else {
            mPlayer.setBackgrounded(false);
        }
    }

    public void onPause() {
        if (!mEnableBackgroundAudio) {
            releasePlayer();
        } else {
            mPlayer.setBackgrounded(true);
        }
        mShutterView.setVisibility(View.VISIBLE);
    }

    public void onDestroy() {
        mAudioCapabilitiesReceiver.unregister();
        releasePlayer();
    }

    // Retry
    public void retry() {
        preparePlayer(true);
    }

    public void showPlayListPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        builder.setTitle(R.string.title_playlist);
        View view = inflater.inflate(R.layout.dialog_single_selection, null);

        builder.setView(view)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog dialog = builder.create();
        dialog.show();

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.dialog_single_selection_radio_group);

        for (int i = 0; i < mVideoParts.size(); i++) {
            RadioButton button = (RadioButton) mActivity.getLayoutInflater().inflate(R.layout.radio_button, null);
            button.setText(mVideoParts.get(i).mTitle);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            radioGroup.addView(button, params);
        }

        radioGroup.getChildCount();

        ((RadioButton) radioGroup.getChildAt(mPartPosition)).setChecked(true);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int idx = getCheckedIndex(group, checkedId);

                Logger.e(TAG, "CHECCCCK " + idx);
                if (mPartSelectionListener != null) {
                    mPartSelectionListener.onPartSelected(idx);
                    mPartPosition = idx;
                }
                dialog.dismiss();
            }
        });
    }


    public void showQualityPopup() {
        final int TRACK_TYPE = MobiPlayer.TYPE_VIDEO;
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        builder.setTitle(R.string.title_quality);
        View view = inflater.inflate(R.layout.dialog_single_selection, null);

        builder.setView(view)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog dialog = builder.create();
        dialog.show();

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.dialog_single_selection_radio_group);

        int trackCount = mPlayer.getTrackCount(TRACK_TYPE);
        if (trackCount > 0) {
            for (int i = 0; i < trackCount; i++) {
                MediaFormat format = mPlayer.getTrackFormat(TRACK_TYPE, i);
                String name = buildTrackName(format);
                RadioButton button = (RadioButton) mActivity.getLayoutInflater().inflate(R.layout.radio_button, null);
                button.setText(name);
                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                radioGroup.addView(button, params);
            }
        }

        radioGroup.getChildCount();

        int selectedTrack = mPlayer.getSelectedTrack(TRACK_TYPE);
        ((RadioButton) radioGroup.getChildAt(selectedTrack)).setChecked(true);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int idx = getCheckedIndex(group, checkedId);

                Logger.e(TAG, "CHECCCCK " + idx);
                mPlayer.setSelectedTrack(TRACK_TYPE, idx);
                dialog.dismiss();
            }
        });
    }

    public void showSpeedPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        builder.setTitle(R.string.title_speed);

        View view = inflater.inflate(R.layout.speed_dialog, null);
        builder.setView(view)
//                .setNegativeButton(R.string.send, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                })
                .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog dialog = builder.create();
        dialog.show();

        ((RadioButton) ((RadioGroup) view).getChildAt(mSpeedSelectionPosition)).setChecked(true);
        ((RadioGroup) view).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int idx = getCheckedIndex(group, checkedId);
                mSpeedSelectionPosition = idx;
                changeSpeed(mSpeedSelectionPosition);
                dialog.dismiss();
            }
        });
    }

    private void changeSpeed(int speedSelectionPosition) {
        switch (speedSelectionPosition) {
            case SPEED_POSITION_05:
                mPlayer.setPlaybackSpeed(SPEED_05);
                break;
            case SPEED_POSITION_10:
                mPlayer.setPlaybackSpeed(SPEED_10);
                break;
            case SPEED_POSITION_15:
                mPlayer.setPlaybackSpeed(SPEED_15);
                break;
            case SPEED_POSITION_20:
                mPlayer.setPlaybackSpeed(SPEED_20);
                break;
        }
    }

    private int getCheckedIndex(RadioGroup group, int checkedId) {
        int radioButtonID = group.getCheckedRadioButtonId();
        View radioButton = group.findViewById(radioButtonID);
        return group.indexOfChild(radioButton);
    }

    public void showReportPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        builder.setTitle(R.string.title_report);
        View view = inflater.inflate(R.layout.dialog_single_selection, null);

        builder.setView(view)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog dialog = builder.create();
        dialog.show();

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.dialog_single_selection_radio_group);

        // Get reports
        PlayerSetting playerSetting = PrefManager.getSettings(mActivity);
        if (playerSetting == null) {
            Toast.makeText(mActivity, mActivity.getString(R.string.error_cant_report_now), Toast.LENGTH_SHORT).show();
            return;
        }

        List<PlayerSetting.ErrorType> errorTypes = playerSetting.getErrorType();
        if (errorTypes == null || errorTypes.size() == 0) {
            Toast.makeText(mActivity, mActivity.getString(R.string.error_cant_report_now), Toast.LENGTH_SHORT).show();
            return;
        }

        for (int i = 0; i < errorTypes.size(); i++) {
            String name = errorTypes.get(i).getContent();
            RadioButton button = (RadioButton) mActivity.getLayoutInflater().inflate(R.layout.radio_button, null);
            button.setText(name);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            radioGroup.addView(button, params);
        }

        radioGroup.getChildCount();

//        ((RadioButton) radioGroup.getChildAt(selectedTrack)).setChecked(true);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int idx = getCheckedIndex(group, checkedId);

                Logger.e(TAG, "CHECCCCK " + idx);
                if (mOnReportSelectionListener != null) {
                    mOnReportSelectionListener.onReportSelected(idx);
                }
                dialog.dismiss();
            }
        });
    }


    // AudioCapabilitiesReceiver.Listener methods

    @Override
    public void onAudioCapabilitiesChanged(AudioCapabilities audioCapabilities) {
        if (mPlayer == null) {
            return;
        }
        boolean backgrounded = mPlayer.getBackgrounded();
        boolean playWhenReady = mPlayer.getPlayWhenReady();
        releasePlayer();
        preparePlayer(playWhenReady);
        mPlayer.setBackgrounded(backgrounded);
    }

    // Permission request listener method

    public void onRequestPermissionSuccess() {
        preparePlayer(true);
    }

    // Permission management methods

    /**
     * Checks whether it is necessary to ask for permission to read storage. If necessary, it also
     * requests permission.
     *
     * @return true if a permission request is made. False if it is not necessary.
     */
    @TargetApi(23)
    private boolean maybeRequestPermission() {
        if (requiresPermission(mContentUri)) {
            mActivity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            return true;
        } else {
            return false;
        }
    }

    @TargetApi(23)
    private boolean requiresPermission(Uri uri) {
        return Util.SDK_INT >= 23
                && Util.isLocalFileUri(uri)
                && mActivity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED;
    }

    // Internal methods

    private MobiPlayer.RendererBuilder getRendererBuilder() {
        String userAgent = Util.getUserAgent(mActivity, "ExoPlayerDemo");
        switch (mContentType) {
            case Util.TYPE_SS:
                return new SmoothStreamingRendererBuilder(mActivity, userAgent, mContentUri.toString(),
                        new SmoothStreamingTestMediaDrmCallback());
//            case Util.TYPE_DASH:
//                return new DashRendererBuilder(mActivity, userAgent, mContentUri.toString(),
//                        new WidevineTestMediaDrmCallback(mContentId, mProvider));
            case Util.TYPE_HLS:
                return new HlsRendererBuilder(mActivity, userAgent, mContentUri.toString());
            case Util.TYPE_OTHER:
                return new ExtractorRendererBuilder(mActivity, userAgent, mContentUri);
            default:
                throw new IllegalStateException("Unsupported type: " + mContentType);
        }
    }

    public void preparePlayer(boolean playWhenReady) {
        if (mPlayer == null) {
            mPlayer = new MobiPlayer(getRendererBuilder());
            mPlayer.addListener(this);
            mPlayer.setCaptionListener(this);
            mPlayer.setMetadataListener(this);
            mPlayer.seekTo(mPlayerPosition);
            mPlayerNeedsPrepare = true;
            mMediaController.setMediaPlayer(mPlayer.getPlayerControl());
            mMediaController.setEnabled(true);
            mEventLogger = new EventLogger();
            mEventLogger.startSession();
            mPlayer.addListener(mEventLogger);
            mPlayer.setInfoListener(mEventLogger);
            mPlayer.setInternalErrorListener(mEventLogger);
        }
        if (mPlayerNeedsPrepare) {
            mPlayer.prepare();
            mPlayerNeedsPrepare = false;
            updateButtonVisibilities();
        }
        mPlayer.setSurface(mSurfaceView.getHolder().getSurface());
        mPlayer.setPlayWhenReady(playWhenReady);
    }

    /**
     * Release this instance
     */
    private void releasePlayer() {
        if (mPlayer != null) {
            mPlayerPosition = mPlayer.getCurrentPosition();
            mPlayer.release();
            mPlayer = null;
            mEventLogger.endSession();
            mEventLogger = null;
        }
    }

    /**
     * Set stateListener for controller
     *
     * @param stateListener {@link StateListener}
     */
    public void setStateListener(StateListener stateListener) {
        mStateListener = stateListener;
    }

    // MobiPlayer.Listener implementation

    @Override
    public void onStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == ExoPlayer.STATE_ENDED) {
            showControls();
        }
        Logger.i(TAG, "State changed: " + playbackState);
        switch (playbackState) {
            case ExoPlayer.STATE_BUFFERING:
                if (mStateListener != null) {
                    mStateListener.onBuffering();
                }
                break;
            case ExoPlayer.STATE_ENDED:
                break;
            case ExoPlayer.STATE_IDLE:
                if (mStateListener != null) {
                    mStateListener.onIdle();
                }
                break;
            case ExoPlayer.STATE_PREPARING:
                if (mStateListener != null) {
                    mStateListener.onPreparing();
                }

                if (!mIsCoverHasBeenShown) {
                    mCoverIv.setVisibility(View.VISIBLE);
                }
                break;
            case ExoPlayer.STATE_READY:
                if (mStateListener != null) {
                    mStateListener.onReady();
                }

                if (!mIsCoverHasBeenShown) {
                    // Disappear cover
                    Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.cover_disappear);
                    mCoverIv.startAnimation(animation);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            mCoverIv.setVisibility(View.GONE);
                            mIsCoverHasBeenShown = true;
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
                break;
            default:
                break;
        }

        updateButtonVisibilities();
    }

    @Override
    public void onError(Exception e) {
        String errorString = null;
        if (e instanceof UnsupportedDrmException) {
            // Special case DRM failures.
            UnsupportedDrmException unsupportedDrmException = (UnsupportedDrmException) e;
            errorString = mActivity.getString(Util.SDK_INT < 18 ? R.string.error_drm_not_supported
                    : unsupportedDrmException.reason == UnsupportedDrmException.REASON_UNSUPPORTED_SCHEME
                    ? R.string.error_drm_unsupported_scheme : R.string.error_drm_unknown);
        } else if (e instanceof ExoPlaybackException
                && e.getCause() instanceof MediaCodecTrackRenderer.DecoderInitializationException) {
            // Special case for decoder initialization failures.
            MediaCodecTrackRenderer.DecoderInitializationException decoderInitializationException =
                    (MediaCodecTrackRenderer.DecoderInitializationException) e.getCause();
            if (decoderInitializationException.decoderName == null) {
                if (decoderInitializationException.getCause() instanceof MediaCodecUtil.DecoderQueryException) {
                    errorString = mActivity.getString(R.string.error_querying_decoders);
                } else if (decoderInitializationException.secureDecoderRequired) {
                    errorString = mActivity.getString(R.string.error_no_secure_decoder,
                            decoderInitializationException.mimeType);
                } else {
                    errorString = mActivity.getString(R.string.error_no_decoder,
                            decoderInitializationException.mimeType);
                }
            } else {
                errorString = mActivity.getString(R.string.error_instantiating_decoder,
                        decoderInitializationException.decoderName);
            }
        }
        if (errorString != null) {
            Toast.makeText(mActivity.getApplicationContext(), errorString, Toast.LENGTH_LONG).show();
        }
        mPlayerNeedsPrepare = true;
        updateButtonVisibilities();
        showControls();
    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees,
                                   float pixelWidthAspectRatio) {
        mShutterView.setVisibility(View.GONE);
        mVideoFrame.setAspectRatio(
                height == 0 ? 1 : (width * pixelWidthAspectRatio) / height);
    }

    // User controls

    private void updateButtonVisibilities() {
//        retryButton.setVisibility(mPlayerNeedsPrepare ? View.VISIBLE : View.GONE);
//        videoButton.setVisibility(haveTracks(MobiPlayer.TYPE_VIDEO) ? View.VISIBLE : View.GONE);
//        audioButton.setVisibility(haveTracks(MobiPlayer.TYPE_AUDIO) ? View.VISIBLE : View.GONE);
//        textButton.setVisibility(haveTracks(MobiPlayer.TYPE_TEXT) ? View.VISIBLE : View.GONE);
    }

    private boolean haveTracks(int type) {
        return mPlayer != null && mPlayer.getTrackCount(type) > 0;
    }

    public void showVideoPopup(View v) {
        PopupMenu popup = new PopupMenu(mActivity, v);
        configurePopupWithTracks(popup, null, MobiPlayer.TYPE_VIDEO);
        popup.show();
    }

    public void showAudioPopup(View v) {
        PopupMenu popup = new PopupMenu(mActivity, v);
        Menu menu = popup.getMenu();
        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, R.string.enable_background_audio);
        final MenuItem backgroundAudioItem = menu.findItem(0);
        backgroundAudioItem.setCheckable(true);
        backgroundAudioItem.setChecked(mEnableBackgroundAudio);
        PopupMenu.OnMenuItemClickListener clickListener = new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item == backgroundAudioItem) {
                    mEnableBackgroundAudio = !item.isChecked();
                    return true;
                }
                return false;
            }
        };
        configurePopupWithTracks(popup, clickListener, MobiPlayer.TYPE_AUDIO);
        popup.show();
    }

    public void showTextPopup(View v) {
        PopupMenu popup = new PopupMenu(mActivity, v);
        configurePopupWithTracks(popup, null, MobiPlayer.TYPE_TEXT);
        popup.show();
    }

    public void showVerboseLogPopup(View v) {
        PopupMenu popup = new PopupMenu(mActivity, v);
        Menu menu = popup.getMenu();
        menu.add(Menu.NONE, 0, Menu.NONE, R.string.logging_normal);
        menu.add(Menu.NONE, 1, Menu.NONE, R.string.logging_verbose);
        menu.setGroupCheckable(Menu.NONE, true, true);
        menu.findItem((VerboseLogUtil.areAllTagsEnabled()) ? 1 : 0).setChecked(true);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == 0) {
                    VerboseLogUtil.setEnableAllTags(false);
                } else {
                    VerboseLogUtil.setEnableAllTags(true);
                }
                return true;
            }
        });
        popup.show();
    }

    private void configurePopupWithTracks(PopupMenu popup,
                                          final PopupMenu.OnMenuItemClickListener customActionClickListener,
                                          final int trackType) {
        if (mPlayer == null) {
            return;
        }
        int trackCount = mPlayer.getTrackCount(trackType);
        if (trackCount == 0) {
            return;
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return (customActionClickListener != null
                        && customActionClickListener.onMenuItemClick(item))
                        || onTrackItemClick(item, trackType);
            }
        });
        Menu menu = popup.getMenu();
        // ID_OFFSET ensures we avoid clashing with Menu.NONE (which equals 0).
        menu.add(MENU_GROUP_TRACKS, MobiPlayer.TRACK_DISABLED + ID_OFFSET, Menu.NONE, R.string.off);
        for (int i = 0; i < trackCount; i++) {
            menu.add(MENU_GROUP_TRACKS, i + ID_OFFSET, Menu.NONE,
                    buildTrackName(mPlayer.getTrackFormat(trackType, i)));
        }
        menu.setGroupCheckable(MENU_GROUP_TRACKS, true, true);
        menu.findItem(mPlayer.getSelectedTrack(trackType) + ID_OFFSET).setChecked(true);
    }

//    private void configureVideoPopupWithTracks(final int trackType) {
//        if (mPlayer == null) {
//            return;
//        }
//        int trackCount = mPlayer.getTrackCount(trackType);
//        if (trackCount == 0) {
//            return;
//        }
//        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                return (customActionClickListener != null
//                        && customActionClickListener.onMenuItemClick(item))
//                        || onTrackItemClick(item, trackType);
//            }
//        });
//        Menu menu = popup.getMenu();
//        // ID_OFFSET ensures we avoid clashing with Menu.NONE (which equals 0).
//        menu.add(MENU_GROUP_TRACKS, MobiPlayer.TRACK_DISABLED + ID_OFFSET, Menu.NONE, R.string.off);
//        for (int i = 0; i < trackCount; i++) {
//            menu.add(MENU_GROUP_TRACKS, i + ID_OFFSET, Menu.NONE,
//                    buildTrackName(mPlayer.getTrackFormat(trackType, i)));
//        }
//        menu.setGroupCheckable(MENU_GROUP_TRACKS, true, true);
//        menu.findItem(mPlayer.getSelectedTrack(trackType) + ID_OFFSET).setChecked(true);
//    }

    private String buildTrackName(MediaFormat format) {
        if (format.adaptive) {
            return mActivity.getString(R.string.value_quality_auto);
        }
        String trackName;
        if (MimeTypes.isVideo(format.mimeType)) {
//            trackName = joinWithSeparator(joinWithSeparator(buildResolutionString(format),
//                    buildBitrateString(format)), buildTrackIdString(format));
            trackName = buildResolutionString(format);
        } else if (MimeTypes.isAudio(format.mimeType)) {
            trackName = joinWithSeparator(joinWithSeparator(joinWithSeparator(buildLanguageString(format),
                    buildAudioPropertyString(format)), buildBitrateString(format)),
                    buildTrackIdString(format));
        } else {
            trackName = joinWithSeparator(joinWithSeparator(buildLanguageString(format),
                    buildBitrateString(format)), buildTrackIdString(format));
        }
        return trackName.length() == 0 ? "unknown" : trackName;
    }

    private static String buildResolutionString(MediaFormat format) {
        return format.width == MediaFormat.NO_VALUE || format.height == MediaFormat.NO_VALUE
                ? "" : format.height + "p";
    }

    private static String buildAudioPropertyString(MediaFormat format) {
        return format.channelCount == MediaFormat.NO_VALUE || format.sampleRate == MediaFormat.NO_VALUE
                ? "" : format.channelCount + "ch, " + format.sampleRate + "Hz";
    }

    private static String buildLanguageString(MediaFormat format) {
        return TextUtils.isEmpty(format.language) || "und".equals(format.language) ? ""
                : format.language;
    }

    private static String buildBitrateString(MediaFormat format) {
        return format.bitrate == MediaFormat.NO_VALUE ? ""
                : String.format(Locale.US, "%.2fMbit", format.bitrate / 1000000f);
    }

    private static String joinWithSeparator(String first, String second) {
        return first.length() == 0 ? second : (second.length() == 0 ? first : first + ", " + second);
    }

    private static String buildTrackIdString(MediaFormat format) {
        return format.trackId == null ? "" : " (" + format.trackId + ")";
    }

    private boolean onTrackItemClick(MenuItem item, int type) {
        if (mPlayer == null || item.getGroupId() != MENU_GROUP_TRACKS) {
            return false;
        }
        mPlayer.setSelectedTrack(type, item.getItemId() - ID_OFFSET);
        return true;
    }

    private void toggleControlsVisibility() {
        if (mMediaController.isShowing()) {
            hideControls();
        } else {
            showControls();
        }
    }

    private void hideControls() {
        mMediaController.hide();
        mMoreActionLayout.setVisibility(View.GONE);
    }

    private void showControls() {
        if (mMediaController != null) {
            try {
                mRootControlLayout.setVerticalGravity(View.VISIBLE);
                mMoreActionLayout.setVisibility(View.VISIBLE);
                mActionLayout.setVisibility(View.GONE);
                mMediaController.show(0);
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        }
    }

    // MobiPlayer.CaptionListener implementation

    @Override
    public void onCues(List<Cue> cues) {
        mSubtitleLayout.setCues(cues);
    }

    // MobiPlayer.MetadataListener implementation

    @Override
    public void onId3Metadata(List<Id3Frame> id3Frames) {
        for (Id3Frame id3Frame : id3Frames) {
            if (id3Frame instanceof TxxxFrame) {
                TxxxFrame txxxFrame = (TxxxFrame) id3Frame;
                Log.i(TAG, String.format("ID3 TimedMetadata %s: description=%s, value=%s", txxxFrame.id,
                        txxxFrame.description, txxxFrame.value));
            } else if (id3Frame instanceof PrivFrame) {
                PrivFrame privFrame = (PrivFrame) id3Frame;
                Log.i(TAG, String.format("ID3 TimedMetadata %s: owner=%s", privFrame.id, privFrame.owner));
            } else if (id3Frame instanceof GeobFrame) {
                GeobFrame geobFrame = (GeobFrame) id3Frame;
                Log.i(TAG, String.format("ID3 TimedMetadata %s: mimeType=%s, filename=%s, description=%s",
                        geobFrame.id, geobFrame.mimeType, geobFrame.filename, geobFrame.description));
            } else {
                Log.i(TAG, String.format("ID3 TimedMetadata %s", id3Frame.id));
            }
        }
    }

    // SurfaceHolder.Callback implementation

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mPlayer != null) {
            mPlayer.setSurface(holder.getSurface());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Do nothing.
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mPlayer != null) {
            mPlayer.blockingClearSurface();
        }
    }

    private void configureSubtitleView() {
        CaptionStyleCompat style;
        float fontScale;
        if (Util.SDK_INT >= 19) {
            style = getUserCaptionStyleV19();
            fontScale = getUserCaptionFontScaleV19();
        } else {
            style = CaptionStyleCompat.DEFAULT;
            fontScale = 1.0f;
        }
        mSubtitleLayout.setStyle(style);
        mSubtitleLayout.setFractionalTextSize(SubtitleLayout.DEFAULT_TEXT_SIZE_FRACTION * fontScale);
    }

    @TargetApi(19)
    private float getUserCaptionFontScaleV19() {
        CaptioningManager captioningManager =
                (CaptioningManager) mActivity.getSystemService(Context.CAPTIONING_SERVICE);
        return captioningManager.getFontScale();
    }

    @TargetApi(19)
    private CaptionStyleCompat getUserCaptionStyleV19() {
        CaptioningManager captioningManager =
                (CaptioningManager) mActivity.getSystemService(Context.CAPTIONING_SERVICE);
        return CaptionStyleCompat.createFromCaptionStyle(captioningManager.getUserStyle());
    }

    /**
     * Makes a best guess to infer the type from a media {@link Uri} and an optional overriding
     * file
     * extension.
     *
     * @param uri           The {@link Uri} of the media.
     * @param fileExtension An overriding file extension.
     * @return The inferred type.
     */
    private static int inferContentType(Uri uri, String fileExtension) {
        String lastPathSegment = !TextUtils.isEmpty(fileExtension) ? "." + fileExtension
                : uri.getLastPathSegment();
        return Util.inferContentType(lastPathSegment);
    }

    public void setLikeButtonVisibility(int visibility) {
        mLikeIv.setVisibility(visibility);
    }

    public void setShareButtonVisibility(int visibility) {
        mShareIv.setVisibility(visibility);
    }

    public void setTitle(String title) {
        mTitle = title;
        mTitleTv.setText(title);

        if (DeviceUtils.isLandscape(mActivity)) {
            setTitleVisibility(View.VISIBLE);
        } else {
            setTitleVisibility(View.INVISIBLE);
        }
    }

    public void setTitleVisibility(int visibility) {
        mTitleTv.setText(mTitle);
        mTitleTv.setVisibility(visibility);
    }

    public void setAspectRatio(float playerRatio) {
        mVideoFrame.setAspectRatio(playerRatio);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        mMediaController.onConfigurationChanged(newConfig);
        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                setPlayListVisibility(View.VISIBLE);
                mLikeIv.setVisibility(View.VISIBLE);
                mShareIv.setVisibility(View.VISIBLE);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                setPlayListVisibility(View.GONE);
                mLikeIv.setVisibility(View.GONE);
                mShareIv.setVisibility(View.GONE);
                break;
        }
    }

    public void setNextVisibility(int visibility) {
        mMediaController.setNextVisibility(visibility);
    }

    public void setPreviousVisibility(int visibility) {
        mMediaController.setPreviousVisibility(visibility);
    }

    public void setPrevNextListeners(View.OnClickListener next, View.OnClickListener prev) {
        mMediaController.setPrevNextListeners(next, prev);
    }

    public void setNextEnabled(boolean enabled) {
        mMediaController.setNextEnabled(enabled);
    }

    public void setPreviousEnabled(boolean enabled) {
        mMediaController.setPreviousEnabled(enabled);
    }

    public void setProgressTimeLayoutVisibility(int visibility) {
        mMediaController.setProgressTimeLayoutVisibility(visibility);
    }

    public void setLiveNowVisibility(int visibility) {
        mMediaController.setLiveNowVisibility(visibility);
    }

    public void setPlayListVisibility(int visibility) {
        if (mVideoParts != null && mVideoParts.size() > 0) {
            mPlayListTv.setVisibility(visibility);
        } else {
            mPlayListTv.setVisibility(View.GONE);
        }
    }

    public void setVideoParts(List<VideoPart> videoParts, PartSelectionListener partSelectionListener) {
        mVideoParts = videoParts;
        mPartSelectionListener = partSelectionListener;
        mMediaController.setVideoParts(videoParts, partSelectionListener);
    }

    public void setPartPosition(int partPosition) {
        mPartPosition = partPosition;
    }

    public int getPartPosition() {
        return mPartPosition;
    }

    public List<VideoPart> getVideoParts() {
        return mVideoParts;
    }

    public void setOnReportSelectionListener(OnReportSelectionListener onReportSelectionListener) {
        mOnReportSelectionListener = onReportSelectionListener;
    }

    public void setPlayerActionListener(PlayerActionListener playerActionListener) {
        mPlayerActionListener = playerActionListener;
    }

    public void doRefreshLike(boolean isLike) {
        mLikeIv.setImageResource(isLike ? R.drawable.ic_player_thumb_up_active : R.drawable.ic_player_thumb_up_nomal);
    }

    private static final class KeyCompatibleMediaController extends CustomMediaController {

        private PlayerControl mPlayerControl;

        public KeyCompatibleMediaController(Context context) {
            super((Activity) context);
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

    /**
     * Player controller listener
     */
    public interface StateListener {
        void onPreparing();

        void onBuffering();

        void onIdle();

        void onReady();
    }

    public interface PlayerActionListener {
        void onPlayerLikeClicked();

        void onPlayerShareClicked();
    }

    public static class VideoPart {
        int mPosition;
        String mTitle;
        String mId;

        public VideoPart(String id, int position, String title) {
            mPosition = position;
            mTitle = title;
            mId = id;
        }

        public String getId() {
            return mId;
        }
    }

    public interface PartSelectionListener {
        void onPartSelected(int position);
    }

    public interface OnReportSelectionListener {
        void onReportSelected(int position);
    }
}
