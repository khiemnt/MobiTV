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
import com.google.android.exoplayer.util.Util;
import com.google.android.exoplayer.util.VerboseLogUtil;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.media.EventLogger;
import com.viettel.vpmt.mobiletv.media.SmoothStreamingTestMediaDrmCallback;
import com.viettel.vpmt.mobiletv.media.WidevineTestMediaDrmCallback;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.accessibility.CaptioningManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
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
    public static final String CONTENT_ID_EXTRA = "content_id";
    public static final String CONTENT_TYPE_EXTRA = "content_type";
    public static final String PROVIDER_EXTRA = "provider";

    // For use when launching the demo app using adb.
    private static final String CONTENT_EXT_EXTRA = "type";

    private static final String TAG = "PlayerActivity";
    private static final int MENU_GROUP_TRACKS = 1;
    private static final int ID_OFFSET = 2;

    private static final CookieManager defaultCookieManager;

    static {
        defaultCookieManager = new CookieManager();
        defaultCookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }

    private View rootView;
    private RelativeLayout rlRootControl;
    private LinearLayout llAction;
    private TextView tvMoreAction;
    private ImageView ivQuality;
    private ImageView ivSpeed;
    private ImageView ivReport;
    private ImageView ivRetry;

    private EventLogger eventLogger;
    private MediaController mediaController;
    private AspectRatioFrameLayout videoFrame;
    private SurfaceView surfaceView;
    private View shutterView;
    private SubtitleLayout subtitleLayout;

    private MobiPlayer player;
    private boolean playerNeedsPrepare;

    private long playerPosition;
    private boolean enableBackgroundAudio;

    private Uri contentUri;
    private int contentType;
    private String contentId;
    private String provider;

    private AudioCapabilitiesReceiver audioCapabilitiesReceiver;
    private Activity mActivity;

    public PlayerController(Activity activity, final View rootView,
                            AspectRatioFrameLayout videoFrame, SurfaceView surfaceView,
                            SubtitleLayout subtitleLayout, View shutterView) {
        this.rootView = rootView;
        mActivity = activity;
        this.shutterView = shutterView;
        this.rlRootControl = (RelativeLayout) rootView.findViewById(R.id.rlRootControl);
        this.llAction = (LinearLayout) rootView.findViewById(R.id.controls_root);
        this.ivQuality = (ImageView) rootView.findViewById(R.id.control_quantity);
        this.ivSpeed = (ImageView) rootView.findViewById(R.id.control_speed);
        this.ivReport = (ImageView) rootView.findViewById(R.id.control_report);
        this.ivRetry = (ImageView) rootView.findViewById(R.id.control_retry);
        ivQuality.setOnClickListener(qualityListener);
        ivSpeed.setOnClickListener(speedListener);
        ivReport.setOnClickListener(reportListener);
        llAction.setVisibility(View.GONE);
        ivRetry.setOnClickListener(retryListener);
        tvMoreAction = (TextView) rootView.findViewById(R.id.item_more_action);
        tvMoreAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaController.hide();
                tvMoreAction.setVisibility(View.GONE);
                llAction.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.anim);
                animation.setDuration(500);
                llAction.setAnimation(animation);
                llAction.animate();
                animation.start();
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
                if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_ESCAPE
                        || keyCode == KeyEvent.KEYCODE_MENU) {
                    return false;
                }
                return mediaController.dispatchKeyEvent(event);
            }
        });

        this.videoFrame = videoFrame;
        this.surfaceView = surfaceView;
        surfaceView.getHolder().addCallback(this);
        this.subtitleLayout = subtitleLayout;

        mediaController = new KeyCompatibleMediaController(mActivity);
        mediaController.setAnchorView(rootView);

        CookieHandler currentHandler = CookieHandler.getDefault();
        if (currentHandler != defaultCookieManager) {
            CookieHandler.setDefault(defaultCookieManager);
        }

        audioCapabilitiesReceiver = new AudioCapabilitiesReceiver(mActivity, this);
        audioCapabilitiesReceiver.register();
    }

    private View.OnClickListener qualityListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showQualityPopup(v);
        }
    };

    private View.OnClickListener speedListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //// TODO: 4/6/2016
        }
    };

    private View.OnClickListener reportListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showAudioPopup(v);
        }
    };

    private View.OnClickListener retryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            retry();
        }
    };

    public void onNewIntent(Intent intent) {
        releasePlayer();
        playerPosition = 0;
        if (mActivity != null) {
            mActivity.setIntent(intent);
        }
    }

    public void init(Uri uri, int contentType, String fileExtension) {
        contentUri = uri;
        releasePlayer();
        llAction.setVisibility(View.GONE);
        tvMoreAction.setVisibility(View.GONE);
        if (contentType < 0 || contentType > 3) {
            this.contentType = inferContentType(contentUri, fileExtension);
        } else {
            this.contentType = contentType;
        }

        configureSubtitleView();
        if (player == null) {
            if (!maybeRequestPermission()) {
                preparePlayer(true);
            }
        } else {
            player.setBackgrounded(false);
        }
    }

    public void onResume(Intent intent) {
//        contentUri = intent.getData();
//        contentType = intent.getIntExtra(CONTENT_TYPE_EXTRA,
//                inferContentType(contentUri, intent.getStringExtra(CONTENT_EXT_EXTRA)));
//        contentId = intent.getStringExtra(CONTENT_ID_EXTRA);
//        provider = intent.getStringExtra(PROVIDER_EXTRA);
//        configureSubtitleView();
//        if (player == null) {
//            if (!maybeRequestPermission()) {
//                preparePlayer(true);
//            }
//        } else {
//            player.setBackgrounded(false);
//        }
    }

    public void onPause() {
        if (!enableBackgroundAudio) {
            releasePlayer();
        } else {
            player.setBackgrounded(true);
        }
        shutterView.setVisibility(View.VISIBLE);
    }

    public void onDestroy() {
        audioCapabilitiesReceiver.unregister();
        releasePlayer();
    }

    // Retry
    public void retry() {
        preparePlayer(true);
    }

    public void showQualityPopup(View v) {
        PopupMenu popup = new PopupMenu(mActivity, v);
        configurePopupWithTracks(popup, null, MobiPlayer.TYPE_VIDEO);
        popup.show();
    }

    // AudioCapabilitiesReceiver.Listener methods

    @Override
    public void onAudioCapabilitiesChanged(AudioCapabilities audioCapabilities) {
        if (player == null) {
            return;
        }
        boolean backgrounded = player.getBackgrounded();
        boolean playWhenReady = player.getPlayWhenReady();
        releasePlayer();
        preparePlayer(playWhenReady);
        player.setBackgrounded(backgrounded);
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
        if (requiresPermission(contentUri)) {
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
        switch (contentType) {
            case Util.TYPE_SS:
                return new SmoothStreamingRendererBuilder(mActivity, userAgent, contentUri.toString(),
                        new SmoothStreamingTestMediaDrmCallback());
            case Util.TYPE_DASH:
                return new DashRendererBuilder(mActivity, userAgent, contentUri.toString(),
                        new WidevineTestMediaDrmCallback(contentId, provider));
            case Util.TYPE_HLS:
                return new HlsRendererBuilder(mActivity, userAgent, contentUri.toString());
            case Util.TYPE_OTHER:
                return new ExtractorRendererBuilder(mActivity, userAgent, contentUri);
            default:
                throw new IllegalStateException("Unsupported type: " + contentType);
        }
    }

    public void preparePlayer(boolean playWhenReady) {
        if (player == null) {
            player = new MobiPlayer(getRendererBuilder());
            player.addListener(this);
            player.setCaptionListener(this);
            player.setMetadataListener(this);
            player.seekTo(playerPosition);
            playerNeedsPrepare = true;
            mediaController.setMediaPlayer(player.getPlayerControl());
            mediaController.setEnabled(true);
            eventLogger = new EventLogger();
            eventLogger.startSession();
            player.addListener(eventLogger);
            player.setInfoListener(eventLogger);
            player.setInternalErrorListener(eventLogger);
        }
        if (playerNeedsPrepare) {
            player.prepare();
            playerNeedsPrepare = false;
            updateButtonVisibilities();
        }
        player.setSurface(surfaceView.getHolder().getSurface());
        player.setPlayWhenReady(playWhenReady);
    }

    private void releasePlayer() {
        if (player != null) {
            playerPosition = player.getCurrentPosition();
            player.release();
            player = null;
            eventLogger.endSession();
            eventLogger = null;
        }
    }

    // MobiPlayer.Listener implementation

    @Override
    public void onStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == ExoPlayer.STATE_ENDED) {
            showControls();
        }
        String text = "playWhenReady=" + playWhenReady + ", playbackState=";
        switch (playbackState) {
            case ExoPlayer.STATE_BUFFERING:
                text += "buffering";
                break;
            case ExoPlayer.STATE_ENDED:
                text += "ended";
                break;
            case ExoPlayer.STATE_IDLE:
                text += "idle";
                break;
            case ExoPlayer.STATE_PREPARING:
                text += "preparing";
                break;
            case ExoPlayer.STATE_READY:
                text += "ready";
                break;
            default:
                text += "unknown";
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
        playerNeedsPrepare = true;
        updateButtonVisibilities();
        showControls();
    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees,
                                   float pixelWidthAspectRatio) {
        shutterView.setVisibility(View.GONE);
        videoFrame.setAspectRatio(
                height == 0 ? 1 : (width * pixelWidthAspectRatio) / height);
    }

    // User controls

    private void updateButtonVisibilities() {
//        retryButton.setVisibility(playerNeedsPrepare ? View.VISIBLE : View.GONE);
//        videoButton.setVisibility(haveTracks(MobiPlayer.TYPE_VIDEO) ? View.VISIBLE : View.GONE);
//        audioButton.setVisibility(haveTracks(MobiPlayer.TYPE_AUDIO) ? View.VISIBLE : View.GONE);
//        textButton.setVisibility(haveTracks(MobiPlayer.TYPE_TEXT) ? View.VISIBLE : View.GONE);
    }

    private boolean haveTracks(int type) {
        return player != null && player.getTrackCount(type) > 0;
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
        backgroundAudioItem.setChecked(enableBackgroundAudio);
        PopupMenu.OnMenuItemClickListener clickListener = new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item == backgroundAudioItem) {
                    enableBackgroundAudio = !item.isChecked();
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
        if (player == null) {
            return;
        }
        int trackCount = player.getTrackCount(trackType);
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
                    buildTrackName(player.getTrackFormat(trackType, i)));
        }
        menu.setGroupCheckable(MENU_GROUP_TRACKS, true, true);
        menu.findItem(player.getSelectedTrack(trackType) + ID_OFFSET).setChecked(true);
    }

    private static String buildTrackName(MediaFormat format) {
        if (format.adaptive) {
            return "auto";
        }
        String trackName;
        if (MimeTypes.isVideo(format.mimeType)) {
            trackName = joinWithSeparator(joinWithSeparator(buildResolutionString(format),
                    buildBitrateString(format)), buildTrackIdString(format));
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
                ? "" : format.width + "x" + format.height;
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
        if (player == null || item.getGroupId() != MENU_GROUP_TRACKS) {
            return false;
        }
        player.setSelectedTrack(type, item.getItemId() - ID_OFFSET);
        return true;
    }

    private void toggleControlsVisibility() {
        if (mediaController.isShowing()) {
            mediaController.hide();
            tvMoreAction.setVisibility(View.GONE);
        } else {
            showControls();
        }
    }

    private void showControls() {
        if (mediaController != null) {
            try {
                rlRootControl.setVerticalGravity(View.VISIBLE);
                tvMoreAction.setVisibility(View.VISIBLE);
                llAction.setVisibility(View.GONE);
                mediaController.show(0);
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        }
    }

    // MobiPlayer.CaptionListener implementation

    @Override
    public void onCues(List<Cue> cues) {
        subtitleLayout.setCues(cues);
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
        if (player != null) {
            player.setSurface(holder.getSurface());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Do nothing.
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (player != null) {
            player.blockingClearSurface();
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
        subtitleLayout.setStyle(style);
        subtitleLayout.setFractionalTextSize(SubtitleLayout.DEFAULT_TEXT_SIZE_FRACTION * fontScale);
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

    private static final class KeyCompatibleMediaController extends MediaController {

        private MediaPlayerControl playerControl;

        public KeyCompatibleMediaController(Context context) {
            super(context);
        }

        @Override
        public void setMediaPlayer(MediaPlayerControl playerControl) {
            super.setMediaPlayer(playerControl);
            this.playerControl = playerControl;
        }

        @Override
        public boolean dispatchKeyEvent(KeyEvent event) {
            int keyCode = event.getKeyCode();
            if (playerControl.canSeekForward() && keyCode == KeyEvent.KEYCODE_MEDIA_FAST_FORWARD) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    playerControl.seekTo(playerControl.getCurrentPosition() + 15000); // milliseconds
                    show();
                }
                return true;
            } else if (playerControl.canSeekBackward() && keyCode == KeyEvent.KEYCODE_MEDIA_REWIND) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    playerControl.seekTo(playerControl.getCurrentPosition() - 5000); // milliseconds
                    show();
                }
                return true;
            }
            return super.dispatchKeyEvent(event);
        }
    }
}
