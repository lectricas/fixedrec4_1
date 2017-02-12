package com.example.xals.fixedrec4_1.repository.dto;


import com.activeandroid.sebbia.Model;
import com.activeandroid.sebbia.annotation.Column;
import com.activeandroid.sebbia.annotation.Table;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Table(name = PointDTO.TABLE_NAME)
public class PointDTO extends Model {
    public static final String TABLE_NAME = "Point";

    @Column(name = "dababaseId")
    String uuid;

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