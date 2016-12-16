package com.example.xals.fixedrec4_1.mvp.map;

import android.os.Bundle;
import android.view.View;


import com.example.xals.fixedrec4_1.business.interactor.database.IDatabaseInteractor;
import com.example.xals.fixedrec4_1.mvp.base.BasePresenter;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class TrackViewPresenter extends BasePresenter<TrackViewActivity> {

    @Inject
    IDatabaseInteractor databaseInteractor;

    private static final int CLOSE_CURRENT_TRACK = 1;
    private static final int GET_CURRENT_TRACK = 2;

    private String trackUUID;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        restartableFirst(CLOSE_CURRENT_TRACK,
                () -> databaseInteractor.closeCurrentTrack()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                TrackViewActivity::onTrackClosed,
                this::onError);

        restartableFirst(GET_CURRENT_TRACK,
                () -> databaseInteractor.getTackByUUIDWithPoints(trackUUID)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),(trackViewActivity, trackUI) -> {
                    if (trackUI.isRunning()){
                        trackViewActivity.onCurrentTrackRunningChecked(trackUI, View.VISIBLE);
                    } else {
                        trackViewActivity.onCurrentTrackRunningChecked(trackUI, View.GONE);
                    }
                },
                this::onError);
    }

    public void closeCurrentTrack() {
        start(CLOSE_CURRENT_TRACK);
    }

    public void checkIfTrackRunningToHideButton(String trackUUID) {
        this.trackUUID = trackUUID;
        start(GET_CURRENT_TRACK);
    }
}
