package com.gemvietnam.mobitv.screen.login;

import com.gemvietnam.mobitv.base.BasePresenterImpl;
import com.gemvietnam.mobitv.common.util.StringUtils;

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
        if (!StringUtils.validateEmail(email)) {
            mView.onEmailError();
            return;
        }

        if (password.length() < 3) {
            mView.onPasswordError();
            return;
        }

        mView.showProgress();
    }

}
