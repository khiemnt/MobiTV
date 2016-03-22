package com.viettel.vpmt.mobiletv.screen.login;

import com.viettel.vpmt.mobiletv.base.BasePresenter;

/**
 * Login presenter
 * Created by neo on 2/5/2016.
 */
public interface LoginPresenter extends BasePresenter {
    void doLogin(String deviceId, String email, String password, boolean isAdmin);

}
