package com.example.xals.fixedrec4_1.business.model;


import com.google.gson.annotations.SerializedName;

public class Product {
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_COMPOSITION = "composition";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_PRICE = "price";

    @SerializedName(COLUMN_NAME)
    String name;
    @SerializedName(COLUMN_COMPOSITION)
    String composition;
    @SerializedName(COLUMN_WEIGHT)
    String weight;
    @SerializedName(COLUMN_PRICE)
    int price;
}
