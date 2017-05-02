package com.example.xals.fixedrec4_1.mvp.map.activity.presenter;

import android.util.Log;
import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.example.xals.fixedrec4_1.Fix4Application;
import com.example.xals.fixedrec4_1.business.interactor.database.DatabaseInteractor;
import com.example.xals.fixedrec4_1.business.interactor.network.NetworkInteractor;
import com.example.xals.fixedrec4_1.mvp.base.presenter.BasePresenter;
import com.example.xals.fixedrec4_1.repository.dto.Token;
import com.example.xals.fixedrec4_1.repository.dto.TrackDTO;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


@InjectViewState
public class TrackViewPresenter extends BasePresenter<TrackDisplayViewState> {

    @Inject
    DatabaseInteractor database;

    @Inject
    NetworkInteractor network;

    public TrackViewPresenter() {
        Fix4Application.getApplicationComponent().inject(this);
    }

    public void closeCurrentTrack(String trackUUID) {
        Subscription s1 = Observable.just(preferences.getToken())
                .flatMap(tokenS ->
                        database.closeCurrentTrack(trackUUID)
                                .flatMap(trackDTO -> {
                                    if (tokenS.equals(Token.TOKEN_EMPTY)) {
                                        return Observable.just(null);
                                    } else {
                                        return network.uploadTrack(Token.configure(preferences.getToken()), trackDTO);
                                    }
                                }))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    getViewState().onTrackClosed(response);
                }, this::returnError);
        unsubscribeOnDestroy(s1);
    }

    public void getTrackForDisplay(String trackUUID) {
        Subscription s1 = database.getTackByUUIDWithPoints(trackUUID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dto -> {
                    if (dto.isRunning()){
                        getViewState().onTrackForDisplayLoaded(dto, View.VISIBLE);
                    } else {
                        getViewState().onTrackForDisplayLoaded(dto, View.GONE);
                    }
                }, this::returnError);
        unsubscribeOnDestroy(s1);
    }
}
