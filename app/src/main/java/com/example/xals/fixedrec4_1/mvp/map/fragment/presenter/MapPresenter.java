package com.example.xals.fixedrec4_1.mvp.map.fragment.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.example.xals.fixedrec4_1.repository.dto.PointDTO;
import com.example.xals.fixedrec4_1.mvp.base.presenter.BasePresenter;
import com.example.xals.fixedrec4_1.util.AppPreferences;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@InjectViewState
public class MapPresenter extends BasePresenter<MapViewState> {


    public static final int LOAD_TRACK_BY_UUD = 0;
    @Inject
    AppPreferences preferences;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        rxBus.toObserverable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recieved -> {
                    if (recieved instanceof PointDTO) {
                        Log.d("MapPresenter", "PointRecieved");
                        getViewState().pointReceived(((PointDTO) recieved));
                    }
                }, this::returnError);
    }
}
