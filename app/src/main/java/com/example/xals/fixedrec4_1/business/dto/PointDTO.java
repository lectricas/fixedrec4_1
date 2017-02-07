package com.example.xals.fixedrec4_1.business.dto;


import com.activeandroid.sebbia.Model;
import com.activeandroid.sebbia.annotation.Column;
import com.activeandroid.sebbia.annotation.Table;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Builder;


@Getter
@Setter
@Table(name = PointDTO.TABLE_NAME)
public class PointDTO extends Model {
    public static final String TABLE_NAME = "Point";

    @Column(name = "uuid")
    int uuid;

    @Column(name = "lat")
    double lat;

    @Column(name = "lng")
    double lng;

    @Column(name = "accuracy")
    float accuracy;

    @Column(name = "dateCreated")
    Date dateCreated;

    @Column(name = "speed")
    float speed;

    @Column(name = "bearing")
    float bearing;

    public static final String COLUMN_TRACK_ID = "track_id";
    @Column(name = COLUMN_TRACK_ID)
    long trackId;
}