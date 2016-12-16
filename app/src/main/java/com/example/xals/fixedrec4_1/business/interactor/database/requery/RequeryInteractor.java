package com.example.xals.fixedrec4_1.business.interactor.database.requery;


import com.example.xals.fixedrec4_1.business.dto.PointDTO;
import com.example.xals.fixedrec4_1.business.dto.TrackDTO;
import com.example.xals.fixedrec4_1.business.interactor.database.IDatabaseInteractor;
import com.example.xals.fixedrec4_1.util.AppPreferences;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class RequeryInteractor implements IDatabaseInteractor {

    AppPreferences preferences;



    @Inject
    public RequeryInteractor() {
        super();
//        this.preferences = preferences;
    }

    @Override
    public Observable<TrackDTO> getTackByUUIDWithPoints(String uuid) {
        return null;
    }

    @Override
    public Observable<List<TrackDTO>> getAllTracks() {
        return null;
    }

    @Override
    public Observable<TrackDTO> createNewTrack() {



//        TrackR dto = TrackDTO.builder()
//                .dateCreated(Convert.getCurrentDate())
//                .track_owner("user1")
//                .isRunning(true)
//                .build();
        return null;
    }

    @Override
    public Observable<TrackDTO> closeCurrentTrack() {
        return null;
    }

    @Override
    public Observable<TrackDTO> getCurrentTrackNoPoints() {
        return null;
    }

    @Override
    public Observable<PointDTO> saveTrackPoint(PointDTO dto) {
        return null;
    }
}
