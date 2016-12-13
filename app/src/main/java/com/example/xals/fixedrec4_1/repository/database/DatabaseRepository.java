package com.example.xals.fixedrec4_1.repository.database;


import com.example.xals.fixedrec4_1.business.dto.PointDTO;
import com.example.xals.fixedrec4_1.business.dto.TrackDTO;

import java.util.List;

import rx.Observable;


public class DatabaseRepository implements IDatabaseRepository {
    @Override
    public Observable<TrackDTO> getTrackByUUID(String trackUUID) {
        return ActiveAndroidHelper.getTrackByUUID(trackUUID);
    }

    @Override
    public Observable<List<TrackDTO>> getAllTracks() {
        return ActiveAndroidHelper.getAllTracks();
    }

    @Override
    public Observable<TrackDTO> saveTrack(TrackDTO trackDTO) {
        return ActiveAndroidHelper.saveTrack(trackDTO);
    }

    @Override
    public Observable<PointDTO> savePoint(PointDTO pointDTO) {
        return ActiveAndroidHelper.savePoint(pointDTO);
    }

    @Override
    public Observable<List<PointDTO>> getPointsByTrackId(Long trackId) {
        return ActiveAndroidHelper.getAllPoints(trackId);
    }
}
