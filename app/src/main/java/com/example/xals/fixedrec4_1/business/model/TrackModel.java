package com.example.xals.fixedrec4_1.business.model;


import com.activeandroid.sebbia.annotation.Column;
import com.example.xals.fixedrec4_1.repository.dto.PointDTO;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackModel {

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

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof TrackModel)) {
            return false;
        }

        TrackModel advert = (TrackModel) o;

        return advert.getUuid().equals(uuid);
    }


    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + uuid.hashCode();
        return result;
    }
}
