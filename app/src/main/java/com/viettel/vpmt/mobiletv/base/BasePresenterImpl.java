package com.viettel.vpmt.mobiletv.base;

/**
 * Base implements for presenters
 * Created by neo on 14/03/2016.
 */
public class BasePresenterImpl<V extends BaseView> implements BasePresenter {
    protected V mView;

    public BasePresenterImpl(V view) {
        mView = view;

    }
}
