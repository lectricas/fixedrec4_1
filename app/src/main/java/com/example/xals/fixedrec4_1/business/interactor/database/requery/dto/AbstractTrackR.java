package com.example.xals.fixedrec4_1.business.interactor.database.requery.dto;


import android.os.Parcelable;

import java.util.Date;
import java.util.List;

import io.requery.CascadeAction;
import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.OneToMany;
import io.requery.Persistable;

@Entity
public abstract class AbstractTrackR implements Parcelable, Persistable {

    @Key
    @Generated
    int id;

    String uuid;
    Boolean isRunning;
    Float distance;
    Date dateCreated;
    Date dateClosed;
    Date dateUpdated;
    String comments;
    Integer type;
    Integer status;
    String track_owner;

    @OneToMany(cascade = {CascadeAction.DELETE, CascadeAction.SAVE})
    List<AbstractPointR> points;
}
