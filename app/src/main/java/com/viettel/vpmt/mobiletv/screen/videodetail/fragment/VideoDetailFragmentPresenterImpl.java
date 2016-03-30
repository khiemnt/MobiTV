package com.viettel.vpmt.mobiletv.screen.videodetail.fragment;

import com.viettel.vpmt.mobiletv.base.BasePresenterImpl;
import com.viettel.vpmt.mobiletv.common.pref.PrefManager;
import com.viettel.vpmt.mobiletv.common.util.NetworkUtils;
import com.viettel.vpmt.mobiletv.network.ServiceBuilder;
import com.viettel.vpmt.mobiletv.network.callback.BaseCallback;
import com.viettel.vpmt.mobiletv.network.dto.VideoDetail;
import com.viettel.vpmt.mobiletv.network.dto.VideoStream;

/**
 * Created by ThanhTD on 3/26/2016.
 */
public class VideoDetailFragmentPresenterImpl extends BasePresenterImpl<VideoDetailFragmentView> implements VideoDetailFragmentPresenter {
    public VideoDetailFragmentPresenterImpl(VideoDetailFragmentView view) {
        super(view);
    }

    @Override
    public void getDetailVideo(final int position, float videoId, Float partOfVideo) {
        if (!NetworkUtils.checkNetwork(mView.getViewContext())) {
            return;
        }
        mView.showProgress();
        String header = "Bearer " + PrefManager.getAccessToken(mView.getViewContext());
        ServiceBuilder.getService().getDetailVideo(header, videoId).enqueue(new BaseCallback<VideoDetail>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                mView.onRequestError(errorCode, errorMessage);
            }

            @Override
            public void onResponse(VideoDetail data) {
                mView.doLoadToView(data, position);
            }
        });
    }

    @Override
    public void getVideoStream(float videoId) {
        if (!NetworkUtils.checkNetwork(mView.getViewContext())) {
            return;
        }
        String header = "Bearer " + PrefManager.getAccessToken(mView.getViewContext());
        ServiceBuilder.getService().getVideoStream(header, videoId).enqueue(new BaseCallback<VideoStream>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                mView.onRequestError(errorCode, errorMessage);
            }

            @Override
            public void onResponse(VideoStream data) {
                mView.doLoadVideoStream(data);
            }
        });
    }


}
