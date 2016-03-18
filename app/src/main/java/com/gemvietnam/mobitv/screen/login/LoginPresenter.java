package com.gemvietnam.mobitv.screen.login;

import com.gemvietnam.mobitv.base.BasePresenter;

/**
 * Login presenter
 * Created by neo on 2/5/2016.
 */
public interface LoginPresenter extends BasePresenter {
    void doLogin(String deviceId, String email, String password, boolean isAdmin);

}
