package com.gemvietnam.mobitv.screen.login;

import com.gemvietnam.mobitv.base.BaseView;

/**
 * Login views
 * Created by neo on 2/5/2016.
 */
public interface LoginView extends BaseView<LoginPresenter> {
    void onEmailError();
    void onPasswordError();
    void onLoginSuccess();
}
