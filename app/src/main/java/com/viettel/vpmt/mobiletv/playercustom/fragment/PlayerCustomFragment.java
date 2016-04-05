package com.viettel.vpmt.mobiletv.playercustom.fragment;

import com.google.android.exoplayer.util.Util;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.media.PlayerFragment;
import com.viettel.vpmt.mobiletv.playercustom.PlayerCustomActivity;

import android.net.Uri;

/**
 * Created by ThanhTD on 4/5/2016.
 */
public class PlayerCustomFragment extends PlayerFragment<PlayerCustomFragmentPresenter, PlayerCustomActivity> implements PlayerCustomFragmentView{
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_custom_player;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onPrepareLayout() {
        initPlayer(Uri.parse("http://vod.mobitv.vn/cache/_FcR+DoS-MA7otD9MmG+uDAN4BKXjpXTS2CEVAlVOj5zWjk-4aXiOBMqIjT39vLxPI8jyzQTSK+4X4XKIW3dWsOaNwm0XJJvGEekGg8LGMmK=_.mp4"), Util.TYPE_HLS);
    }

    @Override
    public PlayerCustomFragmentPresenter onCreatePresenter() {
        return new PlayerCustomFragmentPresenterImpl(this);
    }
}
