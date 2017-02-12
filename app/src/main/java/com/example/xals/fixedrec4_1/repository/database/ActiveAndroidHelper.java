package com.example.xals.fixedrec4_1.repository.database;


import com.activeandroid.sebbia.query.Select;
import com.example.xals.fixedrec4_1.repository.dto.PointDTO;
import com.example.xals.fixedrec4_1.repository.dto.TrackDTO;

import java.util.List;

import rx.Observable;


public class ActiveAndroidHelper {

    public static Observable<TrackDTO> getTrackByUUID(String uuid){
        return new Select()
                .from(TrackDTO.class)
                .eq(TrackDTO.COLUMN_NAME_UUID, uuid)
                .observableSingle();
    }

    public static Observable<PointDTO> savePoint(PointDTO pointDTO) {
        pointDTO.save();
        return Observable.just(pointDTO);
    }

    public static Observable<TrackDTO> saveTrack(TrackDTO dto) {
        dto.save();
        return Observable.just(dto);
    }

    public static Observable<List<TrackDTO>> getAllTracks(){
        return new Select()
                .from(TrackDTO.class)
                .observableExecute();
    }

    public static Observable<List<PointDTO>> getAllPoints(long track_id){
        return new Select()
                .from(PointDTO.class)
                .eq(PointDTO.COLUMN_TRACK_ID, track_id)
                .observableExecute();
    }
}
