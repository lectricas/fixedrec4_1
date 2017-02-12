package com.example.xals.fixedrec4_1.mvp.auth.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.example.xals.fixedrec4_1.Fix4Application;
import com.example.xals.fixedrec4_1.business.interactor.network.NetworkInteractor;
import com.example.xals.fixedrec4_1.mvp.base.presenter.BasePresenter;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


@InjectViewState
public class LoginPresenter extends BasePresenter<LoginViewState> {

    @Inject
    NetworkInteractor network;

    public LoginPresenter() {
        Fix4Application.getApplicationComponent().inject(this);
    }

    public void login(String username, String password) {
        username = username.trim();
        password = password.trim();

        network.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(token -> {
                    getViewState().onLogin(token.getAuth_token());
                }, this::returnError);
    }
}
