package com.example.xals.fixedrec4_1.business.dto;


import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Fix4SuccessResultModel {
    public static final String COLUMN_MENU = "menu";
    public static final String COLUMN_DATE = "date";
    @SerializedName(COLUMN_MENU)
    Map<String, List<Product>> menu;
    @SerializedName(COLUMN_DATE)
    Date date;
}
