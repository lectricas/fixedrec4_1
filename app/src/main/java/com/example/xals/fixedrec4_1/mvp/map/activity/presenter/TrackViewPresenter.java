package com.example.xals.fixedrec4_1.mvp.map.activity.presenter;

import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.example.xals.fixedrec4_1.Fix4Application;
import com.example.xals.fixedrec4_1.business.interactor.database.DatabaseInteractor;
import com.example.xals.fixedrec4_1.mvp.base.presenter.BasePresenter;
import com.example.xals.fixedrec4_1.repository.dto.Token;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


@InjectViewState
public class TrackViewPresenter extends BasePresenter<TrackDisplayViewState> {
    public TrackViewPresenter() {
        Fix4Application.getApplicationComponent().inject(this);
    }

    @Inject
    DatabaseInteractor databaseInteractor;

    public void closeCurrentTrack() {
        databaseInteractor.closeCurrentTrack()
                .flatMap(trackDTO -> databaseInteractor.getTackByUUIDWithPoints(trackDTO.getUuid()))
                .flatMap(trackDTO -> api.uploadTrack(Token.configure(preferences.getToken()), trackDTO))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trackDTO -> {
                    getViewState().onTrackClosed(trackDTO);
                }, this::returnError);
    }

    public void getTrackForDisplay(String trackUUID) {
        databaseInteractor.getTackByUUIDWithPoints(trackUUID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dto -> {
                    if (dto.isRunning()){
                        getViewState().onTrackForDisplayLoaded(dto, View.VISIBLE);
                    } else {
                        getViewState().onTrackForDisplayLoaded(dto, View.GONE);
                    }
                });
    }
}
