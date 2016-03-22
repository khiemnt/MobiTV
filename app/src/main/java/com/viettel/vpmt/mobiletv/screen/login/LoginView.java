package com.viettel.vpmt.mobiletv.screen.login;

import com.viettel.vpmt.mobiletv.base.BaseView;

/**
 * Login views
 * Created by neo on 2/5/2016.
 */
public interface LoginView extends BaseView<LoginPresenter> {
    void onEmailError();
    void onPasswordError();
    void onLoginSuccess();
}
