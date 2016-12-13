package com.example.xals.fixedrec4_1.mvp.model;


import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointUI {
    int uuid;

    double lat;

    double lng;

    float accuracy;

    Date dateCreated;

    float speed;

    long track_id;
}
