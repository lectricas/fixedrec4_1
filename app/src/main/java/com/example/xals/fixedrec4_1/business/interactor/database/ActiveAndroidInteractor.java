package com.example.xals.fixedrec4_1.business.interactor.database;


import com.example.xals.fixedrec4_1.business.model.TrackModel;
import com.example.xals.fixedrec4_1.business.model.Transform;
import com.example.xals.fixedrec4_1.repository.dto.PointDTO;
import com.example.xals.fixedrec4_1.repository.dto.TrackDTO;
import com.example.xals.fixedrec4_1.repository.database.ActiveAndroidHelper;
import com.example.xals.fixedrec4_1.repository.AppPreferences;
import com.example.xals.fixedrec4_1.util.Convert;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import rx.Observable;

public class ActiveAndroidInteractor implements DatabaseInteractor {

    AppPreferences preferences;

    @Inject
    public ActiveAndroidInteractor(AppPreferences preferences) {
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
    public Observable<List<TrackModel>> getAllTracks() {
            return ActiveAndroidHelper.getAllTracks()
                    .map(Transform::fromDtoList);
    }

    //пока не создали новый, будет этот трэк
    @Override
    public Observable<TrackDTO> createNewTrack() {
        TrackDTO dto = new TrackDTO();
        dto.setUuid(UUID.randomUUID().toString());
        dto.setDateCreated(Convert.getCurrentDate());
        dto.setTrackOwner("user1");
        dto.setRunning(true);

        return ActiveAndroidHelper.saveTrack(dto)
                .map(trackDTO -> {
                    preferences.setCurrentTrackUUID(trackDTO.getUuid());
                    return trackDTO;
                });
    }

    @Override
    public Observable<TrackDTO> closeCurrentTrack(String trackUUID) {
        return ActiveAndroidHelper.getTrackByUUID(trackUUID)
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
