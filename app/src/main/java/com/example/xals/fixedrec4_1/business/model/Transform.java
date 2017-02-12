package com.example.xals.fixedrec4_1.business.model;


import com.example.xals.fixedrec4_1.repository.dto.PointDTO;
import com.example.xals.fixedrec4_1.repository.dto.TrackDTO;
import com.example.xals.fixedrec4_1.repository.dto.UserDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Transform {

    String uuid;
    boolean isRunning;
    float distance;
    Date dateCreated;
    Date dateClosed;
    Date dateUpdated;
    String comments;
    int type;
    int status;
    String trackOwner;
    List<PointDTO> points;

    public static TrackDTO toDto(TrackModel model) {
        TrackDTO dto = new TrackDTO();
        dto.setUuid(model.getUuid());
        dto.setRunning(model.isRunning());
        dto.setDistance(model.getDistance());
        dto.setDateCreated(model.getDateCreated());
        dto.setDateClosed(model.getDateClosed());
        dto.setDateUpdated(model.getDateUpdated());
        dto.setComments(model.getComments());
        dto.setType(model.getType());
        dto.setStatus(model.getStatus());
        dto.setTrackOwner(model.getTrackOwner());
        return dto;
    }

    public static TrackModel fromDto(TrackDTO dto) {
        TrackModel model = new TrackModel();
        model.setUuid(dto.getUuid());
        model.setRunning(dto.isRunning());
        model.setDistance(dto.getDistance());
        model.setDateCreated(dto.getDateCreated());
        model.setDateClosed(dto.getDateClosed());
        model.setDateUpdated(dto.getDateUpdated());
        model.setComments(dto.getComments());
        model.setType(dto.getType());
        model.setStatus(dto.getStatus());
        model.setTrackOwner(dto.getTrackOwner());
        return model;
    }

    public static UserDto toDto(UserModel model) {
        UserDto dto = new UserDto();
        dto.setId(model.getId());
        dto.setUuid(model.getUuid());
        dto.setUsername(model.getUsername());
        dto.setEmail(dto.getEmail());
        dto.setSomefield(model.getSomefield());
        return dto;
    }

    public static UserModel fromDto(UserDto dto) {
        UserModel model = new UserModel();
        model.setId(dto.getId());
        model.setUuid(dto.getUuid());
        model.setUsername(dto.getUsername());
        model.setEmail(model.getEmail());
        model.setSomefield(dto.getSomefield());
        return model;
    }

    public static List<TrackModel> fromDtoList(List<TrackDTO> trackDTOs) {
        List<TrackModel> models = new ArrayList<>();
        for (TrackDTO trackDTO : trackDTOs) {
            models.add(fromDto(trackDTO));
        }
        return models;
    }
}
