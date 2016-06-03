package com.viettel.vpmt.mobiletv.screen.home;

import com.viettel.vpmt.mobiletv.base.BasePresenterImpl;
import com.viettel.vpmt.mobiletv.base.log.Logger;
import com.viettel.vpmt.mobiletv.common.util.NetworkUtils;
import com.viettel.vpmt.mobiletv.network.ServiceBuilder;
import com.viettel.vpmt.mobiletv.network.callback.BaseCallback;
import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.screen.common.adapter.HomeBoxAdapter;

import java.util.List;

/**
 * Implement for common presenters
 * Created by neo on 2/15/2016.
 */
public class HomeBoxFragmentPresenterImpl extends BasePresenterImpl<HomeBoxFragmentView> implements HomeBoxFragmentPresenter {
    private static final String TAG = HomeBoxFragmentPresenterImpl.class.getSimpleName();

    public HomeBoxFragmentPresenterImpl(HomeBoxFragmentView view) {
        super(view);
    }

    @Override
    public void getData(String scope, String path) {
        if (!NetworkUtils.checkNetwork(mView.getViewContext())) {
            return;
        }

        buildRequestAndSend(scope, path);
    }

    /**
     * Build request from Fragment Args
     */
    private void buildRequestAndSend(String scope, String path) {
        // Send request
        ServiceBuilder.getService().getHomeBox(scope, path).enqueue(mCallback);
    }

    private BaseCallback<List<Box>> mCallback = new BaseCallback<List<Box>>() {
        @Override
        public void onError(String errorCode, String errorMessage) {
            mView.onRequestError(errorCode, errorMessage);
        }

        @Override
        public void onResponse(List<Box> data) {
            mView.onRequestSuccess();
            Logger.i(TAG, "SS==== " + data.size());
            HomeBoxAdapter homeBoxAdapter
                    = new HomeBoxAdapter(mView.getViewContext(), data, HomeBoxAdapter.GroupViewType.HORIZONTAL_SCROLL, true, mView.isTvHome());
            mView.loadBox(homeBoxAdapter);
        }
    };
}
