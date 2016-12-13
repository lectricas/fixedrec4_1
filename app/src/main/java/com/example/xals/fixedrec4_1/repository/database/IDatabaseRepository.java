package com.example.xals.fixedrec4_1.repository.database;




import com.example.xals.fixedrec4_1.business.dto.PointDTO;
import com.example.xals.fixedrec4_1.business.dto.TrackDTO;

import java.util.List;

import rx.Observable;

public interface IDatabaseRepository {
    Observable<TrackDTO> getTrackByUUID(String trackUUID);
    Observable<List<TrackDTO>> getAllTracks();
    Observable<TrackDTO> saveTrack(TrackDTO trackDTO);
    Observable<PointDTO> savePoint(PointDTO pointDTO);
    Observable<List<PointDTO>> getPointsByTrackId(Long trackId);
}
