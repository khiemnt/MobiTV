package com.viettel.vpmt.mobiletv.screen.bundle;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseFragment;
import com.viettel.vpmt.mobiletv.common.Constants;
import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.screen.home.HomeBoxActivity;

import butterknife.Bind;

/**
 * Bundle Fragment
 * Created by neo on 3/25/2016.
 */
public class BundleFragment extends BaseFragment<BundlePresenter, HomeBoxActivity> implements BundleView {
    @Bind(R.id.common_progress_bar)
    ProgressBar mProgressBar;

    @Bind(R.id.common_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.common_recycler_view)
    RecyclerView mRecyclerView;

    private GridLayoutManager mLayoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bundle;
    }

    @Override
    public void showProgress() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void hideProgress() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }

        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPrepareLayout() {
        Bundle args = getArguments();
        Box.Type boxType = (Box.Type) args.getSerializable(Constants.Extras.BOX_TYPE);

        if (boxType == null) {
            boxType = Box.Type.VOD;
        }
        // Set column number by content type
        int spanCount = 2;
        switch (boxType) {
            case FILM:
                spanCount = 3;
                break;
            case VOD:
                spanCount = 2;
                break;
            case LIVETV:
                spanCount = 3;
                break;
            case CONTINUE:
                spanCount = 2;
                break;
            case FOCUS:
                spanCount = 2;
                break;
            default:
                spanCount = 2;
                break;
        }

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        mRecyclerView.setLayoutManager(mLayoutManager);

        getPresenter().getData(boxType, getArguments());
    }

    @Override
    public BundlePresenter onCreatePresenter() {
        return new BundlePresenterImpl(this);
    }

    @Override
    public void loadBox(BundleVideoAdapter bundleVideoAdapter) {
        mRecyclerView.setAdapter(bundleVideoAdapter);
    }
}
