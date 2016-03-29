package com.viettel.vpmt.mobiletv.screen.login;

import com.viettel.vpmt.mobiletv.base.BasePresenterImpl;

/**
 * Implement for Login presenter
 * Created by neo on 2/15/2016.
 */
public class LoginPresenterImpl extends BasePresenterImpl<LoginView> implements LoginPresenter {

    public LoginPresenterImpl(LoginView view) {
        super(view);
    }

    @Override
    public void doLogin(String deviceId, String email, String password, boolean isAdmin) {
        mView.showProgress();
    }

}
