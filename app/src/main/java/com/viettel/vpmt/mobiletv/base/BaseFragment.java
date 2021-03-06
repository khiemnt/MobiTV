package com.viettel.vpmt.mobiletv.base;

import com.viettel.vpmt.mobiletv.common.util.CompatibilityUtil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Base Fragment
 * Created by neo on 3/22/2016.
 */
public abstract class BaseFragment<P extends BasePresenter, A extends BaseActivity> extends Fragment implements BaseView<P> {
    private P mPresenter;
    protected View mRootView;

    public BaseFragment() {
        // Presenter for this view
        mPresenter = onCreatePresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);

        // Inject views
        ButterKnife.bind(this, mRootView);

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
        if (getBaseActivity() != null && !getBaseActivity().isFinishing()) {
            getBaseActivity().onRequestError(errorCode, errorMessage);
        }
    }

    @Override
    public void onRequestSuccess() {
        getBaseActivity().onRequestSuccess();
    }

    @Override
    public void showAlertDialog(String message) {
        getBaseActivity().showAlertDialog(message);
    }

    protected void setScreenMarginForGridView() {
        // This to set margin screen with RecyclerView.
        int margin = CompatibilityUtil.getScreenMargin(getActivity());
        int spacing = CompatibilityUtil.getItemSpacing(getActivity());
        int leftRight = margin - spacing / 2;
        mRootView.setPadding(leftRight, margin, leftRight, margin);
    }
}
