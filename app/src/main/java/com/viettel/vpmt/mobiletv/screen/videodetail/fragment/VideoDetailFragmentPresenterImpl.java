package com.viettel.vpmt.mobiletv.screen.videodetail.fragment;

import com.viettel.vpmt.mobiletv.base.BasePresenterImpl;
import com.viettel.vpmt.mobiletv.common.pref.PrefManager;
import com.viettel.vpmt.mobiletv.common.util.NetworkUtils;
import com.viettel.vpmt.mobiletv.network.ServiceBuilder;
import com.viettel.vpmt.mobiletv.network.callback.BaseCallback;
import com.viettel.vpmt.mobiletv.network.dto.DataStream;
import com.viettel.vpmt.mobiletv.network.dto.ResponseLikeUnlike;
import com.viettel.vpmt.mobiletv.network.dto.VideoDetail;

/**
 * Presenter for video detail fragment
 * Created by ThanhTD on 3/26/2016.
 */
public class VideoDetailFragmentPresenterImpl extends BasePresenterImpl<VideoDetailFragmentView> implements VideoDetailFragmentPresenter {
    private VideoDetail mVideoDetail;

    public VideoDetailFragmentPresenterImpl(VideoDetailFragmentView view) {
        super(view);
    }

    @Override
    public void getVideoDetail(final int position, final String videoId) {
        if (!NetworkUtils.checkNetwork(mView.getViewContext())) {
            return;
        }
        mView.showProgress();
        String header = PrefManager.getHeader(mView.getViewContext());
        ServiceBuilder.getService().getDetailVideo(header, videoId).enqueue(new BaseCallback<VideoDetail>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                mView.onRequestError(errorCode, errorMessage);
            }

            @Override
            public void onResponse(VideoDetail data) {
                mView.doLoadToView(data, position);
                mVideoDetail = data;
            }
        });
    }

    @Override
    public void getVideoStream(String videoId) {
        if (!NetworkUtils.checkNetwork(mView.getViewContext())) {
            return;
        }
        String header = PrefManager.getHeader(mView.getViewContext());
        ServiceBuilder.getService().getVideoStream(header, videoId).enqueue(new BaseCallback<DataStream>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                mView.onRequestError(errorCode, errorMessage);
            }

            @Override
            public void onResponse(DataStream data) {
                mView.doLoadVideoStream(data);
            }
        });
    }

    @Override
    public void postLikeVideo(boolean isLike, String videoId) {
        if (!NetworkUtils.checkNetwork(mView.getViewContext())) {
            return;
        }
        String header = PrefManager.getHeader(mView.getViewContext());
        ServiceBuilder.getService().postLikeVideo(header, videoId).enqueue(new BaseCallback<ResponseLikeUnlike>() {
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
    public VideoDetail getVideoDetail() {
        return mVideoDetail;
    }
}
