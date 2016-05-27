package com.viettel.vpmt.mobiletv.screen.bundle;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseFragment;
import com.viettel.vpmt.mobiletv.base.log.Logger;
import com.viettel.vpmt.mobiletv.common.Constants;
import com.viettel.vpmt.mobiletv.common.util.DeviceUtils;
import com.viettel.vpmt.mobiletv.common.view.SmoothScrollListener;
import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.screen.home.HomeBoxActivity;
import com.viettel.vpmt.mobiletv.screen.home.adapter.GridItemDecoration;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import butterknife.Bind;

/**
 * Bundle Fragment
 * Created by neo on 3/25/2016.
 */
public class BundleFragment extends BaseFragment<BundlePresenter, HomeBoxActivity> implements BundleView,
        SwipeRefreshLayout.OnRefreshListener, OnMoreListener {
    private static final java.lang.String TAG = BundleFragment.class.getSimpleName();

    @Bind(R.id.bundle_recycler_view)
    SuperRecyclerView mRecyclerView;

    private Box.Type mBoxType;
    private String mTitle;

    public static BundleFragment newInstance(Box.Type boxType, String id, String title) {
        BundleFragment fragment = new BundleFragment();

        Bundle args = new Bundle();
        args.putSerializable(Constants.Extras.BOX_TYPE, boxType);
        args.putSerializable(Constants.Extras.ID, id);
        args.putSerializable(Constants.Extras.TITLE, title);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bundle;
    }

    /**
     * Initialize data
     */
    private void initData() {
        String id = getArguments().getString(Constants.Extras.ID, null);

        if (id == null) {
            getBaseActivity().popBackStack();
        }

        mRecyclerView.setupMoreListener(this, 1);
        mRecyclerView.setOnScrollListener(new SmoothScrollListener(getActivity()));
        // Set title
        mTitle = getArguments().getString(Constants.Extras.TITLE);
        setTitle();

        getPresenter().getData(mBoxType, id);
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

    @Override
    public void onPrepareLayout() {
        Bundle args = getArguments();
        mBoxType = (Box.Type) args.getSerializable(Constants.Extras.BOX_TYPE);

        if (mBoxType == null) {
            mBoxType = Box.Type.VOD;
        }
        // Set column number by content type
        int spanCount;
        switch (mBoxType) {
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

        // Item decoration
        final int spacingInPixels = 20;

        // Calculate spacing
        int screenWidth = DeviceUtils.getDeviceSize(getActivity()).x;

        int itemWidth = (spanCount == 2 ?
                getResources().getDimensionPixelSize(R.dimen.item_video_width) :
                getResources().getDimensionPixelSize(R.dimen.item_channel_width));

        int recyclerViewPadding = (screenWidth - itemWidth * spanCount - (spanCount ) * spacingInPixels) / 2;

        // Grid layout manager
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.getRecyclerView().setClipToPadding(false);
        mRecyclerView.getRecyclerView().setHasFixedSize(true);

        // Item spacing
        mRecyclerView.addItemDecoration(new GridItemDecoration(spacingInPixels, spanCount, recyclerViewPadding));

        // Refresh & load more listener
        mRecyclerView.setRefreshListener(this);
        mRecyclerView.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);

        initData();
    }


    @Override
    public BundlePresenter onCreatePresenter() {
        return new BundlePresenterImpl(this);
    }

    @Override
    public void loadBox(RecyclerView.Adapter bundleVideoAdapter) {
        mRecyclerView.setAdapter(bundleVideoAdapter);
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        getPresenter().loadMore();
    }

    @Override
    public void onRefresh() {
        initData();
    }

    @Override
    public void onRequestError(String errorCode, String errorMessage) {
        super.onRequestError(errorCode, errorMessage);
        mRecyclerView.hideMoreProgress();
        mRecyclerView.hideProgress();
        Logger.e(TAG, "Error " + errorMessage);
    }

    @Override
    public void onRequestSuccess() {
        super.onRequestSuccess();
        mRecyclerView.hideMoreProgress();
        mRecyclerView.hideProgress();
    }

    @Override
    public void onDataEmpty() {
        Toast.makeText(getActivity(), getString(R.string.no_data), Toast.LENGTH_LONG).show();
    }

    @Override
    public void stopLoadMore() {
        mRecyclerView.hideMoreProgress();
        mRecyclerView.removeMoreListener();
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }
//
//    @Override
//    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
//        return enter ? AnimationUtils.loadAnimation(getActivity(), R.anim.slide_right_in) : AnimationUtils.loadAnimation(getActivity(), R.anim.slide_right_out);
//    }
}
