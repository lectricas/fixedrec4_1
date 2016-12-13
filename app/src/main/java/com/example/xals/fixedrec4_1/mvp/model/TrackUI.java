package com.example.xals.fixedrec4_1.mvp.model;


import com.example.xals.fixedrec4_1.business.dto.PointDTO;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Builder;

@Getter
@Setter
@Builder
public class TrackUI {
    String uuid;

    boolean isRunning;

    float distance;

    Date dateCreated;

    Date dateClosed;

    Date dateUpdated;

    String comments;

    int type;

    int status;

    String track_owner;

    List<PointDTO> points;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TrackUI trackUI = (TrackUI) o;
        return uuid.equals(trackUI.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode() * 325;
    }
}
