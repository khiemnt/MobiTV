package com.gemvietnam.mobitv.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.gemvietnam.mobitv.common.util.DialogUtils;
import com.gemvietnam.mobitv.network.ServiceBuilder;
import com.gemvietnam.mobitv.screen.login.LoginActivity;

import butterknife.ButterKnife;

/**
 * Base activity
 * Created by neo on 2/5/2016.
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView<T> {
    private ProgressDialog mProgressDialog;
    private T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        // Inject views
        ButterKnife.bind(this);

        // Presenter for this view
        mPresenter = onCreatePresenter();

        // Prepare layout
        onPrepareLayout();

    }

    @Override
    public T getPresenter() {
        return mPresenter;
    }

    @Override
    public void onPrepareLayout() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(android.R.style.Widget_DeviceDefault_Light_ProgressBar_Large);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);

    }

    @Override
    public void showAlertDialog(String message) {
        DialogUtils.showErrorAlert(this, message);
    }

    @Override
    public void showProgress() {
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onRequestError(int errorCode, String errorMessage) {
        if (errorCode == ServiceBuilder.TOKEN_EXPIRED) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            ActivityCompat.finishAffinity(this);
            startActivity(loginIntent);
        } else
            DialogUtils.showErrorAlert(this, errorMessage);
        hideProgress();
    }

    @Override
    public void onRequestSuccess() {
        hideProgress();
    }

    @Override
    public BaseActivity getViewContext() {
        return this;
    }

    /**
     * Return layout resource id for activity
     */
    protected abstract int getLayoutId();
}
