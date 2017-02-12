package com.example.xals.fixedrec4_1.mvp.splash;

import android.os.Bundle;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.xals.fixedrec4_1.R;
import com.example.xals.fixedrec4_1.mvp.auth.LoginActivity;
import com.example.xals.fixedrec4_1.mvp.base.BaseActivity;
import com.example.xals.fixedrec4_1.mvp.main.MainActivity;
import com.example.xals.fixedrec4_1.mvp.splash.presenter.SplashPresenter;
import com.example.xals.fixedrec4_1.mvp.splash.presenter.SplashViewState;


public class SplashActivity extends BaseActivity implements SplashViewState {

    @InjectPresenter
    SplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        presenter.checkIfUserAuthenticated();
    }

    @Override
    public void userAuthenticated() {
        MainActivity.startActivity(this);
    }

    @Override
    public void userNotAuthenticated() {
        LoginActivity.startActivity(this);
    }
}
