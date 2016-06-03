package com.viettel.vpmt.mobiletv.screen.home;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseFragment;
import com.viettel.vpmt.mobiletv.common.Constants;
import com.viettel.vpmt.mobiletv.common.view.SmoothScrollListener;
import com.viettel.vpmt.mobiletv.network.ApiConstants;
import com.viettel.vpmt.mobiletv.screen.common.adapter.HomeBoxAdapter;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.Bind;

/**
 * Common Fragment
 * Created by neo on 3/22/2016.
 */
public class HomeBoxFragment extends BaseFragment<HomeBoxFragmentPresenter, HomeBoxActivity> implements
        HomeBoxFragmentView, SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.box_recycler_view)
    SuperRecyclerView mRecyclerView;

    private String mTitle;
    private String mScope;
    private String mPath;

    public static HomeBoxFragment newInstance(String scope, String path, String title) {
        HomeBoxFragment fragment = new HomeBoxFragment();

        Bundle args = new Bundle();
        args.putString(Constants.Extras.SCOPE, scope);
        args.putString(Constants.Extras.PATH, path);
        args.putString(Constants.Extras.TITLE, title);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_box_home;
    }

    @Override
    public void showProgress() {
        mRecyclerView.showProgress();
    }

    @Override
    public void hideProgress() {
        mRecyclerView.showProgress();
    }

    @Override
    public void onPrepareLayout() {
        mRecyclerView.getRecyclerView().setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setRefreshListener(this);
        mRecyclerView.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);

        mRecyclerView.setOnScrollListener(new SmoothScrollListener(getActivity()));
        // Set title
        mTitle = getArguments().getString(Constants.Extras.TITLE);
        setTitle();

        getData();
    }

    /**
     * Set title on ActionBar for this screen
     */
    private void setTitle() {
        ActionBar actionBar = getBaseActivity().getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(mTitle);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setTitle();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle();
    }

    /**
     * Get screen data
     */
    private void getData() {
        Bundle args = getArguments();
        mScope = args.getString(Constants.Extras.SCOPE);
        mPath = args.getString(Constants.Extras.PATH);

        getPresenter().getData(mScope, mPath);
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
    public boolean isTvHome() {
        return mScope.equalsIgnoreCase(ApiConstants.SCOPE_CHANNEL) && mPath.equalsIgnoreCase(ApiConstants.PATH_HOME_CHANNEL);
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
