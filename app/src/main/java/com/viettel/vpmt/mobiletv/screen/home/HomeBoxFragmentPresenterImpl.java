package com.viettel.vpmt.mobiletv.screen.home;

import android.os.Bundle;

import com.viettel.vpmt.mobiletv.base.BasePresenterImpl;
import com.viettel.vpmt.mobiletv.base.log.Logger;
import com.viettel.vpmt.mobiletv.common.Constants;
import com.viettel.vpmt.mobiletv.common.util.NetworkUtils;
import com.viettel.vpmt.mobiletv.network.ServiceBuilder;
import com.viettel.vpmt.mobiletv.network.callback.BaseCallback;
import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.screen.home.adapter.HomeBoxAdapter;

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
    public void getData(Bundle args) {
        if (!NetworkUtils.checkNetwork(mView.getViewContext())) {
            return;
        }

        buildRequestAndSend(args);
    }

    /**
     * Build request from Fragment Args
     * @param args Arguments of Fragment
     */
    private void buildRequestAndSend(Bundle args) {
        // Get path of request & See all mode(Can load more, paging)
        String path = args.getString(Constants.Extras.PATH, "");
        String scope = args.getString(Constants.Extras.SCOPE, "");
        String id = args.getString(Constants.Extras.ID, "");

        // Send request
        ServiceBuilder.getService().getHomeBox(scope, path, id).enqueue(mCallback);
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
            HomeBoxAdapter homeBoxAdapter = new HomeBoxAdapter(mView.getViewContext(), data);
            mView.loadBox(homeBoxAdapter);
        }
    };
}
