package com.example.xals.fixedrec4_1.business.dto;

import com.activeandroid.sebbia.Model;
import com.activeandroid.sebbia.annotation.Column;
import com.activeandroid.sebbia.annotation.Table;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Builder;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TrackDTO.TABLE_NAME)
public class TrackDTO extends Model {
    public static final String TABLE_NAME = "Track";

    public static final String COLUMN_NAME_UUID = "uuid";
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

    public static final String COLUMN_NAME_TRACK_OWNER = "track_owner";
    @Column(name = COLUMN_NAME_TRACK_OWNER)
    String track_owner;

    List<PointDTO> points;

    public static class TrackDTOBuilder {
        String uuid = UUID.randomUUID().toString();
    }
}


//    RealmList<PointDTO> getPoints() {
//        return points;
//    }
//
//    void setPoints(RealmList<PointDTO> points) {
//        this.points = points;
//    }


//    static final int TRACK_OPENED = 0;
//    static final int TRACK_CLOSED = 1;
//    static final int TRACK_DEFAULT = -1;




//
//    boolean isRunning;
//
//    private float distance;
//
//    private Date dateCreated;
//
//    @Column(name = "date_closed")
//    private Date dateClosed;
//
//    @Column(name = "date_updated")
//    private Date dateUpdated;
//
//    @Column(name = "comments")
//    private String comments;
//
//    @Column(name = "type")
//    private int type;
//
//    @Column(name = "status")
//    private int status;
//
//    @Column(name = "track_owner")
//    private String track_owner;
//
////    private List<PointDTO> points;
//
//
//    TrackDTO(Date dateCreated, String track_owner) {
//        this.dateCreated = dateCreated;
//        this.dateUpdated = dateCreated;
//        this.track_owner = track_owner;
//        this.isRunning = true;
////        this.points = new RealmList<>();
//    }
//
//    TrackDTO() {
//
//    }



//    static TrackDTO getCurrenTrack(String uuid, Realm realm){
//        return realm.where(TrackDTO.class).equalTo(TrackDTO._UUID, uuid).findFirst();
//    }


//    //overriding for contains method
//    @Override
//    boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        TrackDTO track = (TrackDTO) o;
//
//        if (uuid != track.uuid) return false;
//
//        return true;
//    }
//
//    @Override
//    int hashCode() {
//        int result = uuid.hashCode();
//        result = 31 * result;
//        return result;
//    }
