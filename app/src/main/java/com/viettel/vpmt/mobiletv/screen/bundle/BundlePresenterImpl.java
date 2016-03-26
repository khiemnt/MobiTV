package com.viettel.vpmt.mobiletv.screen.bundle;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.viettel.vpmt.mobiletv.base.BasePresenterImpl;
import com.viettel.vpmt.mobiletv.base.log.Logger;
import com.viettel.vpmt.mobiletv.common.Constants;
import com.viettel.vpmt.mobiletv.common.util.NetworkUtils;
import com.viettel.vpmt.mobiletv.network.ApiConstants;
import com.viettel.vpmt.mobiletv.network.ServiceBuilder;
import com.viettel.vpmt.mobiletv.network.callback.BaseCallback;
import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.screen.home.adapter.HomeBoxAdapter;

import java.util.List;

/**
 * Implement for common bundle presenters
 * Created by neo on 2/15/2016.
 */
public class BundlePresenterImpl extends BasePresenterImpl<BundleView> implements BundlePresenter {
    private static final String TAG = BundlePresenterImpl.class.getSimpleName();

    private RecyclerView.Adapter mAdapter;
    private Box.Type mType;

    public BundlePresenterImpl(BundleView view) {
        super(view);
    }

    @Override
    public void getData(Box.Type boxType, Bundle args) {
        mType = boxType;

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
        boolean isSeeAll = args.getBoolean(Constants.Extras.IS_SEE_ALL, false);
        int limit = isSeeAll ? ApiConstants.PAGE_COUNT : 0;

        // Send request
//        ServiceBuilder.getService().getHomeBox(scope, path, id, 0 , limit).enqueue(mCallback);
    }

    private BaseCallback<List<Content>> mCallback = new BaseCallback<List<Content>>() {
        @Override
        public void onError(String errorCode, String errorMessage) {
            mView.onRequestError(errorCode, errorMessage);
        }

        @Override
        public void onResponse(List<Content> data) {
            mView.onRequestSuccess();
            Logger.i(TAG, "SS==== " + data.size());
            BundleVideoAdapter homeBoxAdapter = new BundleVideoAdapter(mView.getViewContext(), data, mType);
            mView.loadBox(homeBoxAdapter);
        }
    };
}
