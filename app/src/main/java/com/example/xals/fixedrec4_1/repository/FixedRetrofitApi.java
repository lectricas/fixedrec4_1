package com.example.xals.fixedrec4_1.repository;


import com.example.xals.fixedrec4_1.business.dto.Fix4SuccessResultModel;

import retrofit2.http.GET;
import rx.Observable;

public interface FixedRetrofitApi {

    String ENDPOINT = "https://obed18.herokuapp.com/";

    @GET("getPriceList")
    Observable<Fix4SuccessResultModel> getPriceList();
}