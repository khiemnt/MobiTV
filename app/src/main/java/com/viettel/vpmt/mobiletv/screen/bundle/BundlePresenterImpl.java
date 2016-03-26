package com.viettel.vpmt.mobiletv.screen.bundle;

import android.support.v7.widget.RecyclerView;

import com.viettel.vpmt.mobiletv.base.BasePresenterImpl;
import com.viettel.vpmt.mobiletv.base.log.Logger;
import com.viettel.vpmt.mobiletv.common.util.NetworkUtils;
import com.viettel.vpmt.mobiletv.network.ServiceBuilder;
import com.viettel.vpmt.mobiletv.network.callback.BaseCallback;
import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.screen.home.adapter.ChannelAdapter;
import com.viettel.vpmt.mobiletv.screen.home.adapter.ContinueAdapter;
import com.viettel.vpmt.mobiletv.screen.home.adapter.FilmAdapter;
import com.viettel.vpmt.mobiletv.screen.home.adapter.FocusAdapter;
import com.viettel.vpmt.mobiletv.screen.home.adapter.VideoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Implement for common bundle presenters
 * Created by neo on 2/15/2016.
 */
public class BundlePresenterImpl extends BasePresenterImpl<BundleView> implements BundlePresenter {
    private static final String TAG = BundlePresenterImpl.class.getSimpleName();

    private RecyclerView.Adapter mAdapter;
    private Box.Type mBoxType;

    private int mCurrentSize = 0;
    private String mId;

    private List<Content> mContents = new ArrayList<>();

    public BundlePresenterImpl(BundleView view) {
        super(view);
    }

    @Override
    public void getData(Box.Type boxType, String id) {
        mBoxType = boxType;
        mId = id;

        if (!NetworkUtils.checkNetwork(mView.getViewContext())) {
            return;
        }

        mAdapter = getAdapter(mContents);
        mView.loadBox(mAdapter);
        requestGetData(mCurrentSize);
    }

    @Override
    public void loadMore() {
        requestGetData(mCurrentSize);
    }

    // Send request
    private void requestGetData(int offset) {
        ServiceBuilder.getService().getMoreContent(mId, offset, ITEM_LIMIT).enqueue(mCallback);
    }

    /**
     * Get adapter depend on type of item
     */
    private RecyclerView.Adapter getAdapter(List<Content> contents) {
        switch (mBoxType) {
            case LIVETV:
                return new ChannelAdapter(mView.getViewContext(), contents);
            case VOD:
                return new VideoAdapter(mView.getViewContext(), contents);
            case TVSHOW:
                return new VideoAdapter(mView.getViewContext(), contents);
            case CONTINUE:
                return new ContinueAdapter(mView.getViewContext(), contents);
            case FOCUS:
                return new FocusAdapter(mView.getViewContext(), contents);
            case FILM:
                return new FilmAdapter(mView.getViewContext(), contents);
            default:
                return new VideoAdapter(mView.getViewContext(), contents);
        }
    }

    // Request callback
    private BaseCallback<List<Box>> mCallback = new BaseCallback<List<Box>>() {
        @Override
        public void onError(String errorCode, String errorMessage) {
            mView.onRequestError(errorCode, errorMessage);
        }

        @Override
        public void onResponse(List<Box> data) {
            mView.onRequestSuccess();

            // If no data is returned
            if (data.size() == 0 || data.get(0) == null || data.get(0).getContents() == null) {
                mView.onDataEmpty();
                mView.stopLoadMore();
                return;
            }

            // Check can load more
            Box box = data.get(0);
            int dataSize = box.getContents().size();
            if (dataSize < ITEM_LIMIT) {
                mView.stopLoadMore();
            }

            // Pass data to view
            mContents.addAll(box.getContents());
            Logger.i(TAG, "SS==== " + mContents.size());

//            if (mCurrentSize == 0) { // Init data
//                mView.loadBox(getAdapter(mContents));
//            } else {
//                mAdapter.ad
//                mView.stopLoadMore();
//            }
            mAdapter.notifyDataSetChanged();
            mCurrentSize += dataSize;
        }
    };
}
