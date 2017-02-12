package com.example.xals.fixedrec4_1.mvp.splash.presenter;


import com.example.xals.fixedrec4_1.mvp.base.presenter.BaseViewState;

public interface SplashViewState extends BaseViewState {
    void userAuthenticated();
    void userNotAuthenticated();
}
