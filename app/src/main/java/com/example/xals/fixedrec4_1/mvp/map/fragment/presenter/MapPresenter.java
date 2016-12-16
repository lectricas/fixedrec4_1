package com.example.xals.fixedrec4_1.mvp.map.fragment.presenter;

import android.os.Bundle;
import android.util.Log;

import com.example.xals.fixedrec4_1.business.dto.PointDTO;
import com.example.xals.fixedrec4_1.mvp.base.BasePresenter;
import com.example.xals.fixedrec4_1.mvp.map.fragment.GoogleMapFragment;
import com.example.xals.fixedrec4_1.util.AppPreferences;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MapPresenter extends BasePresenter<GoogleMapFragment> {


    public static final int LOAD_TRACK_BY_UUD = 0;
    @Inject
    AppPreferences preferences;


    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        deliverReplay().call(rxBus.toObserverable())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recieved -> {
                    recieved.split((googleMapFragment, o) -> {
                        if (o instanceof PointDTO) {
                            Log.d("MapPresenter", "PointRecieved");
                            googleMapFragment.pointReceived(((PointDTO) o));
                        }
                    },(googleMapFragment1, throwable) -> {

                    });
                });
//
//        restartableFirst(LOAD_TRACK_BY_UUD,
//                () -> loadPriceList.call(null)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread()),
//                MainActivity::onTestLoaded,
//                this::onError);
    }

    public void testLoad() {
        start(LOAD_TRACK_BY_UUD);
    }

    public void loadTrackByUUID(String uuid) {

    }
}
