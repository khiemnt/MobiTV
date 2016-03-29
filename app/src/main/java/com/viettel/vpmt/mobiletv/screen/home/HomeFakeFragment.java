package com.viettel.vpmt.mobiletv.screen.home;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseFragment;
import com.viettel.vpmt.mobiletv.common.Constants;
import com.viettel.vpmt.mobiletv.screen.home.adapter.HomeBoxAdapter;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.Bind;

/**
 * Common Fragment
 * Created by neo on 3/22/2016.
 */
public class HomeFakeFragment extends BaseFragment<HomeBoxFragmentPresenter, HomeBoxActivity> implements
        HomeBoxFragmentView, SwipeRefreshLayout.OnRefreshListener {
//    @Bind(R.id.common_progress_bar)
//    ProgressBar mProgressBar;
//
//    @Bind(R.id.common_swipe_refresh)
//    SwipeRefreshLayout mSwipeRefreshLayout;

    //    @Bind(R.id.common_recycler_view)
//    RecyclerView mRecyclerView;
    @Bind(R.id.box_recycler_view)
    SuperRecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_box_home;
    }

    @Override
    public void showProgress() {
//        if (mProgressBar != null) {
//            mProgressBar.setVisibility(View.VISIBLE);
//        }
//
//        if (mSwipeRefreshLayout != null) {
//            mSwipeRefreshLayout.setVisibility(View.GONE);
//        }
    }

    @Override
    public void hideProgress() {
//        if (mProgressBar != null) {
//            mProgressBar.setVisibility(View.GONE);
//        }
//
//        if (mSwipeRefreshLayout != null) {
//            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void onPrepareLayout() {
        mRecyclerView.getRecyclerView().setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setRefreshListener(this);
        mRecyclerView.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);

//        getData();
    }

    /**
     * Get screen data
     */
    private void getData() {
        Bundle args = getArguments();
        String scope = args.getString(Constants.Extras.SCOPE);
        String path = args.getString(Constants.Extras.PATH);
        getPresenter().getData(scope, path);
    }

    @Override
    public HomeBoxFragmentPresenter onCreatePresenter() {
        return new HomeBoxFragmentPresenterImpl(this);
    }

    @Override
    public void loadBox(HomeBoxAdapter homeBoxAdapter) {
        mRecyclerView.setAdapter(homeBoxAdapter);
    }

    @Override
    public void onRequestSuccess() {
        hideProgress();
    }

    @Override
    public void onRequestError(String errorCode, String errorMessage) {
        hideProgress();

        getBaseActivity().onRequestError(errorCode, errorMessage);
    }

    @Override
    public void onRefresh() {
        getData();
    }
}
