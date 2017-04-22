package com.example.xals.fixedrec4_1.mvp.main.presenter;


import com.arellomobile.mvp.InjectViewState;
import com.example.xals.fixedrec4_1.Fix4Application;
import com.example.xals.fixedrec4_1.business.interactor.network.NetworkInteractor;
import com.example.xals.fixedrec4_1.business.model.Transform;
import com.example.xals.fixedrec4_1.mvp.base.presenter.BasePresenter;
import com.example.xals.fixedrec4_1.repository.database.ActiveAndroidHelper;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@InjectViewState
public class MainPresenter extends BasePresenter<MainViewState> {

    @Inject
    NetworkInteractor network;

    public MainPresenter() {
        Fix4Application.getApplicationComponent().inject(this);
    }

    public void logout() {
        ActiveAndroidHelper.deleteCurrentUser()
                .map(Transform::fromDto)
                .delay(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userModel -> {
                    preferences.setToken("");
                });
    }

    public void loadMe() {
        network.me()
                .map(userDto -> {
                    ActiveAndroidHelper.saveUser(userDto);
                    return userDto;
                })
                .onErrorResumeNext(ActiveAndroidHelper.getCurrentUserIfExists())
                .map(Transform::fromDto)
                .delay(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userModel -> {
                    getViewState().onGotMe(userModel);
                });
    }
}
