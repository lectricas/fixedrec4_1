package com.example.xals.fixedrec4_1.mvp.trackslist;


import android.os.Bundle;
import android.util.Log;

import com.example.xals.fixedrec4_1.business.dto.TrackDTO;
import com.example.xals.fixedrec4_1.business.interactor.database.IDatabaseInteractor;
import com.example.xals.fixedrec4_1.business.interactor.viewbindings.IViewInteractor;
import com.example.xals.fixedrec4_1.mvp.base.BasePresenter;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TracksPresenter extends BasePresenter<TracksFragment> {

    public static final int GET_ALL_TRACKS = 1;
    public static final int CREATE_NEW_TRACK = 2;
    public static final int GET_CURRENT_TRACK = 3;
    public static final int LISTEN_TRACK_STATE = 4;

    @Inject
    IDatabaseInteractor databaseInteractor;

    @Inject
    IViewInteractor viewInteractor;

    String currentTrackUUID;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        rxBus.toObserverable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recievd -> {
                    if(recievd instanceof TrackDTO) {
                        Log.d("TracksPresenter", ((TrackDTO) recievd).getDateCreated().toString());
                    }
                });

        restartableFirst(GET_ALL_TRACKS,
                () -> databaseInteractor.getAllTracks()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                TracksFragment::onGotTracks,
                this::onError);

        restartableFirst(CREATE_NEW_TRACK,
                () -> databaseInteractor.createNewTrack()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                TracksFragment::onNewTrackSaved,
                this::onError);

        restartableFirst(GET_CURRENT_TRACK,
                () -> databaseInteractor.getCurrentTrackForUI()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                TracksFragment::onClosedTrackLoadedUIUpdate,
                this::onError);

        restartableFirst(LISTEN_TRACK_STATE,
                () -> viewInteractor.controlFabButton()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                TracksFragment::showFab,
                this::onError);
    }

    public void getAllTracks() {
        start(GET_ALL_TRACKS);
    }

    public void createNewTrack() {
        start(CREATE_NEW_TRACK);
    }

    public void getClosedTrackToUpdateUI() {
        start(GET_CURRENT_TRACK);
    }

    public void checkTrackStateForView() {
        start(LISTEN_TRACK_STATE);
    }
}
