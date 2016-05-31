package com.viettel.vpmt.mobiletv.screen.search;

import com.viettel.vpmt.mobiletv.base.log.Logger;
import com.viettel.vpmt.mobiletv.common.pref.PrefManager;
import com.viettel.vpmt.mobiletv.common.util.NetworkUtils;
import com.viettel.vpmt.mobiletv.network.ServiceBuilder;
import com.viettel.vpmt.mobiletv.network.callback.BaseCallback;
import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.network.dto.ResponseLikeUnlike;
import com.viettel.vpmt.mobiletv.screen.home.adapter.HomeBoxAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Implement for search presenter
 * Created by neo on 5/30/2016.
 */
public class SearchPresenterImpl implements SearchPresenter {
    private static final String TAG = SearchPresenterImpl.class.getSimpleName();
    private SearchView mView;

    public SearchPresenterImpl(SearchView view) {
        mView = view;
    }

    @Override
    public void search(String keyword) {
        if (!NetworkUtils.checkNetwork(mView.getViewContext())) {
            return;
        }

        String header = PrefManager.getHeader(mView.getViewContext());
        ServiceBuilder.getService().search(header, keyword)
                .enqueue(new BaseCallback<List<Box>>() {
                    @Override
                    public void onError(String errorCode, String errorMessage) {
                        mView.onRequestError(errorCode, errorMessage);
                    }

                    @Override
                    public void onResponse(List<Box> data) {
                        HomeBoxAdapter homeBoxAdapter = new HomeBoxAdapter(mView.getViewContext(), data, HomeBoxAdapter.GroupViewType.GRID);
                        mView.loadBox(homeBoxAdapter);
                    }
                });
    }

    @Override
    public void getSuggestion(String keyword) {
        String header = PrefManager.getHeader(mView.getViewContext());
        ServiceBuilder.getService().searchSuggestion(header, keyword)
                .enqueue(new BaseCallback<List<Box>>() {
                    @Override
                    public void onError(String errorCode, String errorMessage) {
                        Logger.e(TAG, "Cannot get suggestion");
                    }

                    @Override
                    public void onResponse(List<Box> data) {
                        SuggestionAdapter adapter = new SuggestionAdapter(mView.getViewContext(), data);
                        mView.loadSuggestion(adapter);
                    }
                });
    }

}
