package com.example.xals.fixedrec4_1.repository.dto;

import com.activeandroid.sebbia.Model;
import com.activeandroid.sebbia.annotation.Column;
import com.activeandroid.sebbia.annotation.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = TrackDTO.TABLE_NAME)
public class TrackDTO extends Model implements Serializable {
    public static final String TABLE_NAME = "Track";

    public static final String COLUMN_NAME_UUID = "dababaseId";
    @Column(name = COLUMN_NAME_UUID)
    String uuid;

    public static final String COLUMN_NAME_IS_RUNNING = "isRunning";
    @Column(name = COLUMN_NAME_IS_RUNNING)
    boolean isRunning;

    public static final String COLUMN_NAME_DISTANCE = "distance";
    @Column(name = COLUMN_NAME_DISTANCE)
    float distance;

    public static final String COLUMN_NAME_DATE_CREATED = "dateCreated";
    @Column(name = COLUMN_NAME_DATE_CREATED)
    Date dateCreated;

    public static final String COLUMN_NAME_DATECLOSED = "dateClosed";
    @Column(name = COLUMN_NAME_DATECLOSED)
    Date dateClosed;

    public static final String COLUMN_NAME_DATE_UPDATED = "dateUpdated";
    @Column(name = COLUMN_NAME_DATE_UPDATED)
    Date dateUpdated;

    public static final String COLUMN_NAME_COMMENTS = "comments";
    @Column(name = COLUMN_NAME_COMMENTS)
    String comments;

    public static final String COLUMN_NAME_TYPE = "type";
    @Column(name = COLUMN_NAME_TYPE)
    int type;

    public static final String COLUMN_NAME_STATUS = "status";
    @Column(name = COLUMN_NAME_STATUS)
    int status;

    public static final String COLUMN_NAME_TRACK_OWNER = "trackOwner";
    @Column(name = COLUMN_NAME_TRACK_OWNER)
    String trackOwner;

    List<PointDTO> points;
}
