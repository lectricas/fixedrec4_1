package com.example.xals.fixedrec4_1.mvp.auth.presenter;


import com.example.xals.fixedrec4_1.mvp.base.presenter.BaseViewState;

public interface LoginViewState extends BaseViewState {
    void onLogin(String authResult);
}
