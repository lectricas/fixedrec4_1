package com.example.xals.fixedrec4_1.business.model;


import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointModel {

    int dababaseId;
    double lat;
    double lng;
    float accuracy;
    Date dateCreated;
    float speed;
    float bearing;
    long trackId;

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof TrackModel)) {
            return false;
        }

        PointModel pointModel = (PointModel) o;

        return pointModel.getDababaseId() == dababaseId;
    }


    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Integer.toString(dababaseId).hashCode();
        return result;
    }
}
