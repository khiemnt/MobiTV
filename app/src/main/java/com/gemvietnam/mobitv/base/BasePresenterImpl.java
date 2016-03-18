package com.gemvietnam.mobitv.base;

import com.gemvietnam.mobitv.common.util.PreferenceUtils;

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
