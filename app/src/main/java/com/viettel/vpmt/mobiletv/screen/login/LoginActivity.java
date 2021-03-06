package com.viettel.vpmt.mobiletv.screen.login;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseActivity;

import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Login Activity
 * Created by neo on 2/5/2016.
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView {
    @Bind(R.id.login_email_et)
    EditText mEmailEt;

    @Bind(R.id.login_password_et)
    EditText mPasswordEt;

    @Bind(R.id.login_error_tv)
    TextView mErrorTv;


    @Override
    public LoginPresenter onCreatePresenter() {
        return new LoginPresenterImpl(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.login_login_bt)
    public void doLogin() {
//        getPresenter().doLogin(DeviceUtils.getDeviceId(this), mEmailEt.getText().toString(),
//                mPasswordEt.getText().toString(), true);
        finish();
    }

    @Override
    public void onEmailError() {
        mErrorTv.setText(R.string.msg_email_error);
    }

    @Override
    public void onPasswordError() {
        mErrorTv.setText(R.string.msg_password_error);
    }

    @Override
    public void onLoginSuccess() {

    }

    @OnClick(R.id.sign_up_btn)
    void goTosignUp() {
    }
}
