package com.viettel.vpmt.mobiletv.base;

import com.viettel.vpmt.mobiletv.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Base Fragment
 * Created by neo on 3/22/2016.
 */
public abstract class BaseListFragment<P extends BasePresenter, A extends BaseActivity> extends Fragment implements BaseView<P> {
    @Bind(R.id.common_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.common_swipe_refresh)
    SwipeRefreshLayout mSwipeLayout;
    private P mPresenter;
    protected View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);

        // Inject views
        ButterKnife.bind(this, mRootView);

        // Presenter for this view
        mPresenter = onCreatePresenter();

        // Prepare layout
        onPrepareLayout();

        return mRootView;
    }

    /**
     * Return layout resource id for activity
     */
    protected abstract int getLayoutId();


    @Override
    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public BaseActivity getViewContext() {
        return getBaseActivity();
    }

    protected A getBaseActivity() {
        return (A) getActivity();
    }

    @Override
    public void onRequestError(String errorCode, String errorMessage) {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }

        if (mSwipeLayout != null) {
            mSwipeLayout.setVisibility(View.VISIBLE);
        }

        getBaseActivity().onRequestError(errorCode, errorMessage);
    }

    @Override
    public void onRequestSuccess() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }

        if (mSwipeLayout != null) {
            mSwipeLayout.setVisibility(View.VISIBLE);
        }
        getBaseActivity().onRequestSuccess();
    }

    @Override
    public void showAlertDialog(String message) {
        getBaseActivity().showAlertDialog(message);
    }
}
