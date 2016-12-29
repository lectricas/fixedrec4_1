package com.example.xals.fixedrec4_1.business.interactor.database.requery.dto;

import android.os.Parcelable;

import java.util.Date;

import io.requery.CascadeAction;
import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.ManyToOne;
import io.requery.Persistable;

@Entity
public abstract class AbstractPointR implements Parcelable, Persistable {

    @Key
    @Generated
    int id;

    String uuid;
    Double lat;
    Double lng;
    Float accuracy;
    Date dateCreated;
    Float speed;
    Float bearing;
    Long track_id;

    @ManyToOne(cascade = {CascadeAction.DELETE, CascadeAction.SAVE})
    AbstractTrackR trackR;
}
