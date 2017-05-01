package com.example.xals.fixedrec4_1.mvp.trackslist.presenter;


import android.Manifest;
import android.app.Activity;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.example.xals.fixedrec4_1.Fix4Application;
import com.example.xals.fixedrec4_1.business.interactor.database.DatabaseInteractor;
import com.example.xals.fixedrec4_1.business.interactor.network.NetworkInteractor;
import com.example.xals.fixedrec4_1.business.interactor.viewbindings.IViewInteractor;
import com.example.xals.fixedrec4_1.business.model.Transform;
import com.example.xals.fixedrec4_1.mvp.base.presenter.BasePresenter;
import com.example.xals.fixedrec4_1.repository.dto.PointDTO;
import com.example.xals.fixedrec4_1.repository.dto.Token;
import com.tbruyelle.rxpermissions.RxPermissions;


import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@InjectViewState
public class TracksPresenter extends BasePresenter<TracksViewState> {
    public TracksPresenter() {
        Fix4Application.getApplicationComponent().inject(this);
    }

    @Inject
    DatabaseInteractor database;

    @Inject
    NetworkInteractor network;

    @Inject
    IViewInteractor view;



    public void createNewTrack() {
        database.createNewTrack()
                .map(Transform::fromDto)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trackDTO -> {
                    getViewState().onNewTrackSaved(trackDTO);
                }, this::returnError);
    }

    public void getClosedTrackToUpdateUI() {
        database.getCurrentTrackNoPoints()
                .map(Transform::fromDto)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trackDTO -> {
                    getViewState().onClosedTrackLoadedUIUpdate(trackDTO);
                }, this::returnError);
    }

    public void checkTrackStateForView() {
        view.controlFabButton()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    getViewState().showFab(aBoolean);
                }, this::returnError);
    }

    public void requestPermissionAndStart(Activity activity) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions
                .requestEach(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(permission -> {
                    if (permission.granted) {
                        createNewTrack();
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        getViewState().permissionNotGranted();
                    } else {
                        getViewState().notGrantedChangeInSettings();
                    }
                }, this::returnError);
    }

    public void loadTracks() {
        Observable.defer(() -> Observable.just(preferences.getToken()))
                .flatMap(token -> {
                    if (token.equals(Token.TOKEN_EMPTY)) {
                        return database.getAllTracks();
                    } else {
                        return network.loadTracks()
                                .onErrorResumeNext(throwable -> database.getAllTracks());
                    }
                })
                .subscribe(trackDTOs -> {
                    getViewState().onGotTracks(trackDTOs);
                }, this::returnError);
    }
}
