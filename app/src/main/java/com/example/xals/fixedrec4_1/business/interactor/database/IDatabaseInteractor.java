package com.example.xals.fixedrec4_1.business.interactor.database;


import com.example.xals.fixedrec4_1.business.dto.PointDTO;
import com.example.xals.fixedrec4_1.business.dto.TrackDTO;
import com.example.xals.fixedrec4_1.mvp.model.TrackUI;

import java.util.List;

import rx.Observable;

public interface IDatabaseInteractor {

    Observable<TrackUI> getTackByUUID(String uuid);

    Observable<List<TrackUI>> getAllTracks();

    Observable<TrackUI> createNewTrack();

    Observable<TrackUI> closeCurrentTrack();

    Observable<TrackDTO> getCurrentTrack();

    Observable<TrackUI> getCurrentTrackForUI();

    Observable<TrackUI> getCurrentTrackWithPoints();

    Observable<PointDTO> saveTrackPoint(Long trackId, double lat, double lng, float accuracy, float speed);

    Observable<List<PointDTO>> getTrackPoints(Long trackId);
}
