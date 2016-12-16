package com.example.xals.fixedrec4_1.business.interactor.database;


import com.example.xals.fixedrec4_1.business.dto.PointDTO;
import com.example.xals.fixedrec4_1.business.dto.TrackDTO;
import com.example.xals.fixedrec4_1.repository.database.ActiveAndroidHelper;
import com.example.xals.fixedrec4_1.util.AppPreferences;
import com.example.xals.fixedrec4_1.util.Convert;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class DatabaseInteractor implements IDatabaseInteractor {

    AppPreferences preferences;

    @Inject
    public DatabaseInteractor(AppPreferences preferences) {
        super();
        this.preferences = preferences;
    }


    @Override
    public Observable<TrackDTO> getTackByUUIDWithPoints(String uuid) {
        return ActiveAndroidHelper.getTrackByUUID(uuid)
                .flatMap(trackDTO -> ActiveAndroidHelper.getAllPoints(trackDTO.getId())
                        .map(pointDTOs -> {
                            trackDTO.setPoints(pointDTOs);
                            return trackDTO;
                        }));
    }

    @Override
    public Observable<List<TrackDTO>> getAllTracks() {
            return ActiveAndroidHelper.getAllTracks();
    }

    //пока не создали новый, будет этот трэк
    @Override
    public Observable<TrackDTO> createNewTrack() {
        TrackDTO dto = TrackDTO.builder()
                .dateCreated(Convert.getCurrentDate())
                .track_owner("user1")
                .isRunning(true)
                .build();

        return ActiveAndroidHelper.saveTrack(dto)
                .map(trackDTO -> {
                    preferences.setCurrentTrackUUID(trackDTO.getUuid());
                    return trackDTO;
                });
    }

    @Override
    public Observable<TrackDTO> closeCurrentTrack() {
        return ActiveAndroidHelper.getTrackByUUID(preferences.getCurrentTrackUUID())
                .flatMap(trackDTO -> {
                    trackDTO.setDateClosed(Convert.getCurrentDate());
                    trackDTO.setDateUpdated(Convert.getCurrentDate());
                    trackDTO.setRunning(false);
                    return ActiveAndroidHelper.saveTrack(trackDTO);
                });
    }

    @Override
    public Observable<TrackDTO> getCurrentTrackNoPoints() {
        String currenttrackuuid = preferences.getCurrentTrackUUID();
        return ActiveAndroidHelper.getTrackByUUID(preferences.getCurrentTrackUUID());
    }

    @Override
    public Observable<PointDTO> saveTrackPoint(PointDTO pointDTO) {
        return ActiveAndroidHelper.savePoint(pointDTO);
    }
}
