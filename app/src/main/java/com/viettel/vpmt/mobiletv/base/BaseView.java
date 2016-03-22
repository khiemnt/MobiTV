package com.viettel.vpmt.mobiletv.base;

/**
 * Base View
 * Created by neo on 2/5/2016.
 */
public interface BaseView<P extends BasePresenter> {
    void showProgress();

    void hideProgress();

    void onPrepareLayout();

    void showAlertDialog(String message);

    P getPresenter();

    P onCreatePresenter();

    void onRequestError(int errorCode, String errorMessage);

    void onRequestSuccess();

    BaseActivity getViewContext();
}
