package com.example.xals.fixedrec4_1.mvp.splash.presenter;


import com.arellomobile.mvp.InjectViewState;
import com.example.xals.fixedrec4_1.Fix4Application;
import com.example.xals.fixedrec4_1.mvp.base.presenter.BasePresenter;
import com.example.xals.fixedrec4_1.repository.dto.Token;

import java.util.concurrent.TimeUnit;

import rx.Observable;

@InjectViewState
public class SplashPresenter extends BasePresenter<SplashViewState> {
    public SplashPresenter() {
        Fix4Application.getApplicationComponent().inject(this);
    }

    public void checkIfUserAuthenticated() {
        Observable.defer(() -> Observable.just(preferences.getToken()))
                .delay(1, TimeUnit.SECONDS)
                .subscribe(token -> {
                    if (token.equals(Token.TOKEN_EMPTY)) {
                        getViewState().userNotAuthenticated();
                    } else {
                        getViewState().userAuthenticated();
                    }
                });
    }
}
