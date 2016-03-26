package com.viettel.vpmt.mobiletv.screen.home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseFragment;
import com.viettel.vpmt.mobiletv.screen.home.adapter.HomeBoxAdapter;

import butterknife.Bind;

/**
 * Common Fragment
 * Created by neo on 3/22/2016.
 */
public class HomeBoxFragment extends BaseFragment<HomeBoxFragmentPresenter, HomeBoxActivity> implements HomeBoxFragmentView {
    @Bind(R.id.common_progress_bar)
    ProgressBar mProgressBar;

    @Bind(R.id.common_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.common_recycler_view)
    RecyclerView mRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_box_home;
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
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        getPresenter().getData(getArguments());
    }

    @Override
    public HomeBoxFragmentPresenter onCreatePresenter() {
        return new HomeBoxFragmentPresenterImpl(this);
    }

    @Override
    public void loadBox(HomeBoxAdapter homeBoxAdapter) {
        mRecyclerView.setAdapter(homeBoxAdapter);
    }
}
