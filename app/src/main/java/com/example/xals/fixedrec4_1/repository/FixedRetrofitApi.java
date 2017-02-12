package com.example.xals.fixedrec4_1.repository;


import com.example.xals.fixedrec4_1.business.model.Fix4SuccessResultModel;
import com.example.xals.fixedrec4_1.repository.dto.TrackDTO;
import com.example.xals.fixedrec4_1.repository.dto.Token;
import com.example.xals.fixedrec4_1.repository.dto.UserDto;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface FixedRetrofitApi {

//    String ENDPOINT = "https://obed18.herokuapp.com/";
//    String ENDPOINT = "http://10.0.3.2:8000/";
    String ENDPOINT = "http://10.0.2.2:8000";

    @GET("getPriceList")
    Observable<Fix4SuccessResultModel> getPriceList();

//    @FormUrlEncoded
    @POST("locations/new_tracks/")
    Observable<Fix4SuccessResultModel> uploadTrack(@Header("Authorization") String tokenString,
                                                   @Body TrackDTO dto);

    @FormUrlEncoded
    @POST("auth/register/")
    Observable<UserDto> register(@Field("email") String email,
                                 @Field("username") String username,
                                 @Field("password") String password);

    @FormUrlEncoded
    @POST("auth/login/")
    Observable<Token> login(@Field("username") String username,
                            @Field("password") String password);

    @GET("locations/new_tracks/{last_update}")
    Observable<List<TrackDTO>> loadTracks(@Header("Authorization") String tokenString,
                                          @Path("last_update") Long lastUpdate);
}
