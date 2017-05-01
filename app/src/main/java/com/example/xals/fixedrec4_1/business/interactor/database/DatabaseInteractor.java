package com.example.xals.fixedrec4_1.business.interactor.database;


import com.example.xals.fixedrec4_1.business.model.TrackModel;
import com.example.xals.fixedrec4_1.repository.dto.PointDTO;
import com.example.xals.fixedrec4_1.repository.dto.TrackDTO;

import java.util.List;

import rx.Observable;

public interface DatabaseInteractor {

    Observable<TrackDTO> getTackByUUIDWithPoints(String uuid);

    Observable<List<TrackModel>> getAllTracks();

    Observable<TrackDTO> createNewTrack();

    Observable<TrackDTO> closeCurrentTrack(String trackUUID);

    Observable<TrackDTO> getCurrentTrackNoPoints();

    Observable<PointDTO> saveTrackPoint(PointDTO dto);
}
