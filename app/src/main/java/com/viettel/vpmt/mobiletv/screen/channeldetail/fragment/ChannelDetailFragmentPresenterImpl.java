package com.viettel.vpmt.mobiletv.screen.channeldetail.fragment;

import com.viettel.vpmt.mobiletv.base.BasePresenterImpl;
import com.viettel.vpmt.mobiletv.common.pref.PrefManager;
import com.viettel.vpmt.mobiletv.common.util.NetworkUtils;
import com.viettel.vpmt.mobiletv.network.ServiceBuilder;
import com.viettel.vpmt.mobiletv.network.callback.BaseCallback;
import com.viettel.vpmt.mobiletv.network.dto.ChannelDetail;
import com.viettel.vpmt.mobiletv.network.dto.DataStream;

/**
 * Presenter for Channel/TV detail fragment
 * Created by neo on 4/17/2016.
 */
public class ChannelDetailFragmentPresenterImpl extends BasePresenterImpl<ChannelDetailFragmentView> implements ChannelDetailFragmentPresenter {

    private static final String TAG = ChannelDetailFragmentPresenterImpl.class.getSimpleName();

    public ChannelDetailFragmentPresenterImpl(ChannelDetailFragmentView view) {
        super(view);
    }

    @Override
    public void getChannelDetail(String channelId) {
        if (!NetworkUtils.checkNetwork(mView.getViewContext())) {
            return;
        }
        mView.showProgress();
        ServiceBuilder.getService().getDetailChannel(channelId).enqueue(new BaseCallback<ChannelDetail>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                mView.onRequestError(errorCode, errorMessage);
            }

            @Override
            public void onResponse(ChannelDetail data) {
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
}
