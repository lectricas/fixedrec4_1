package com.example.xals.fixedrec4_1.business.interactor.database;


import com.example.xals.fixedrec4_1.business.dto.PointDTO;
import com.example.xals.fixedrec4_1.business.dto.TrackDTO;
import com.example.xals.fixedrec4_1.mvp.model.TrackUI;
import com.example.xals.fixedrec4_1.repository.database.IDatabaseRepository;
import com.example.xals.fixedrec4_1.util.AppPreferences;
import com.example.xals.fixedrec4_1.util.Convert;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class DatabaseInteractor implements IDatabaseInteractor {

    AppPreferences preferences;
    IDatabaseRepository databaseRepository;

    @Inject
    public DatabaseInteractor(AppPreferences preferences, IDatabaseRepository databaseRepository) {
        super();
        this.preferences = preferences;
        this.databaseRepository = databaseRepository;
    }

    @Override
    public Observable<TrackUI> getTackByUUID(String uuid) {
        return databaseRepository.getTrackByUUID(uuid).map(this::convertTrack);
    }

    @Override
    public Observable<List<TrackUI>> getAllTracks() {
            return databaseRepository.getAllTracks()
                    .map(this::convertTracks);
    }

    //пока не создали новый, будет этот трэк
    @Override
    public Observable<TrackUI> createNewTrack() {
        TrackDTO dto = TrackDTO.builder()
                .dateCreated(Convert.getCurrentDate())
                .track_owner("user1")
                .isRunning(true)
                .build();

        return databaseRepository.saveTrack(dto)
                .map(trackDTO -> {
                    preferences.setCurrentTrackUUID(trackDTO.getUuid());
                    return convertTrack(trackDTO);
                });
    }

    @Override
    public Observable<TrackUI> closeCurrentTrack() {
        return databaseRepository.getTrackByUUID(preferences.getCurrentTrackUUID())
                .flatMap(trackDTO -> {
                    trackDTO.setDateClosed(Convert.getCurrentDate());
                    trackDTO.setDateUpdated(Convert.getCurrentDate());
                    trackDTO.setRunning(false);
                    preferences.setCurrentTrackUUID("");
                    return databaseRepository.saveTrack(trackDTO);
                })
                .map(this::convertTrack);
    }

    @Override
    public Observable<TrackDTO> getCurrentTrack() {
        return databaseRepository.getTrackByUUID(preferences.getCurrentTrackUUID());
    }

    @Override
    public Observable<TrackUI> getCurrentTrackForUI() {
        return databaseRepository.getTrackByUUID(preferences.getCurrentTrackUUID())
                .map(this::convertTrack);
    }

    @Override
    public Observable<TrackUI> getCurrentTrackWithPoints() {
        return getCurrentTrack().flatMap(trackDTO -> {
            return databaseRepository.getPointsByTrackId(trackDTO.getId())
                    .map(pointDTOs -> {
                        trackDTO.setPoints(pointDTOs);
                        return trackDTO;
                    });
        }).map(this::convertTrack);
    }

    @Override
    public Observable<PointDTO> saveTrackPoint(Long trackId, double lat, double lng, float accuracy, float speed) {
        return databaseRepository.savePoint(PointDTO.builder()
                .track_id(trackId)
                .lat(lat)
                .lng(lng)
                .accuracy(accuracy)
                .dateCreated(Convert.getCurrentDate())
                .speed(speed)
                .build());
    }

    @Override
    public Observable<List<PointDTO>> getTrackPoints(Long trackId) {
        return databaseRepository.getPointsByTrackId(trackId);
    }

    private List<TrackUI> convertTracks(List<TrackDTO> trackDTOs) {
        List<TrackUI> trackUIs = new ArrayList<>();
        for (TrackDTO trackDTO : trackDTOs) {
            trackUIs.add(convertTrack(trackDTO));
        }
        return trackUIs;
    }

    private TrackUI convertTrack(TrackDTO trackDTO) {
        return TrackUI.builder()
                .uuid(trackDTO.getUuid())
                .isRunning(trackDTO.isRunning())
                .distance(trackDTO.getDistance())
                .dateCreated(trackDTO.getDateCreated())
                .dateClosed(trackDTO.getDateClosed())
                .dateUpdated(trackDTO.getDateUpdated())
                .comments(trackDTO.getComments())
                .type(trackDTO.getType())
                .status(trackDTO.getStatus())
                .points(trackDTO.getPoints())
                .track_owner(trackDTO.getTrack_owner()).build();
    }
}
