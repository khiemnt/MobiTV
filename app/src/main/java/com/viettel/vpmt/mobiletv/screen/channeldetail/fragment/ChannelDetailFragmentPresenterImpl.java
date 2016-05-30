package com.viettel.vpmt.mobiletv.screen.channeldetail.fragment;

import com.viettel.vpmt.mobiletv.base.BasePresenterImpl;
import com.viettel.vpmt.mobiletv.base.log.Logger;
import com.viettel.vpmt.mobiletv.common.pref.PrefManager;
import com.viettel.vpmt.mobiletv.common.util.NetworkUtils;
import com.viettel.vpmt.mobiletv.network.ServiceBuilder;
import com.viettel.vpmt.mobiletv.network.callback.BaseCallback;
import com.viettel.vpmt.mobiletv.network.dto.ChannelDetail;
import com.viettel.vpmt.mobiletv.network.dto.DataStream;
import com.viettel.vpmt.mobiletv.network.dto.ResponseLikeUnlike;

/**
 * Presenter for Channel/TV detail fragment
 * Created by neo on 4/17/2016.
 */
public class ChannelDetailFragmentPresenterImpl extends BasePresenterImpl<ChannelDetailFragmentView> implements ChannelDetailFragmentPresenter {
    private static final String TAG = ChannelDetailFragmentPresenterImpl.class.getSimpleName();

    private ChannelDetail mChannelDetail;

    public ChannelDetailFragmentPresenterImpl(ChannelDetailFragmentView view) {
        super(view);
    }

    @Override
    public void getChannelDetail(String channelId) {
        if (!NetworkUtils.checkNetwork(mView.getViewContext())) {
            return;
        }
        mView.showProgress();
        String header = PrefManager.getHeader(mView.getViewContext());
        ServiceBuilder.getService().getDetailChannel(header, channelId).enqueue(new BaseCallback<ChannelDetail>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                mView.onRequestError(errorCode, errorMessage);
            }

            @Override
            public void onResponse(ChannelDetail data) {
                mChannelDetail = data;
                mView.loadToView(data);
                mView.hideProgress();
            }
        });
    }

    @Override
    public void getChannelStream(String channelId) {
        if (!NetworkUtils.checkNetwork(mView.getViewContext())) {
            return;
        }
        String header = PrefManager.getHeader(mView.getViewContext());
        ServiceBuilder.getService().getChannelStream(header, channelId).enqueue(new BaseCallback<DataStream>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                mView.onRequestError(errorCode, errorMessage);
            }

            @Override
            public void onResponse(DataStream data) {
                mView.doLoadChannelStream(data);
            }
        });
    }

    @Override
    public void playProgram(String programId) {
        if (!NetworkUtils.checkNetwork(mView.getViewContext())) {
            return;
        }
        String header = PrefManager.getHeader(mView.getViewContext());
        ServiceBuilder.getService().getProgramStream(header, programId).enqueue(new BaseCallback<DataStream>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                mView.onRequestError(errorCode, errorMessage);
            }

            @Override
            public void onResponse(DataStream data) {
                mView.loadProgram(data.getStreams().getUrlStreaming());
            }
        });
    }

    @Override
    public void notifyChannel(String channelId) {
        if (!NetworkUtils.checkNetwork(mView.getViewContext())) {
            return;
        }
        mView.showProgress();
        String header = PrefManager.getHeader(mView.getViewContext());
        ServiceBuilder.getService().notifyChannel(header, channelId).enqueue(new BaseCallback<String>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                mView.hideProgress();
                mView.onRequestError(errorCode, errorMessage);
            }

            @Override
            public void onResponse(String data) {
                //
                Logger.e("@@@", "datadatadata " + data);
                mView.hideProgress();
            }
        });
    }

    @Override
    public void postLikeChannel(boolean isLike, String channelId) {
        if (!NetworkUtils.checkNetwork(mView.getViewContext())) {
            return;
        }
        String header = PrefManager.getHeader(mView.getViewContext());
        ServiceBuilder.getService().postLikeChannel(header, channelId).enqueue(new BaseCallback<ResponseLikeUnlike>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                mView.onRequestError(errorCode, errorMessage);
            }

            @Override
            public void onResponse(ResponseLikeUnlike data) {
                mView.doRefreshLike(data.isLike());
            }
        });
    }

    @Override
    public ChannelDetail getChannelDetail() {
        return mChannelDetail;
    }

    @Override
    public void playPresentProgram() {
        mView.playPresentProgram();
    }
}
