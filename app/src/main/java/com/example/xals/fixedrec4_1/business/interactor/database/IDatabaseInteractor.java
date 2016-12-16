package com.example.xals.fixedrec4_1.business.interactor.database;


import com.example.xals.fixedrec4_1.business.dto.PointDTO;
import com.example.xals.fixedrec4_1.business.dto.TrackDTO;

import java.util.List;

import rx.Observable;

public interface IDatabaseInteractor {

    Observable<TrackDTO> getTackByUUIDWithPoints(String uuid);

    Observable<List<TrackDTO>> getAllTracks();

    Observable<TrackDTO> createNewTrack();

    Observable<TrackDTO> closeCurrentTrack();

    Observable<TrackDTO> getCurrentTrackNoPoints();

    Observable<PointDTO> saveTrackPoint(PointDTO dto);
}
