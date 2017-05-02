package com.example.xals.fixedrec4_1.business.interactor.network;


import com.example.xals.fixedrec4_1.business.model.TrackModel;
import com.example.xals.fixedrec4_1.business.model.Transform;
import com.example.xals.fixedrec4_1.business.model.UserModel;
import com.example.xals.fixedrec4_1.repository.FixedRetrofitApi;
import com.example.xals.fixedrec4_1.repository.dto.Token;
import com.example.xals.fixedrec4_1.util.AppPreferences;
import com.example.xals.fixedrec4_1.util.Convert;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

public class RetrofitInteractor implements NetworkInteractor {

    AppPreferences preferences;
    FixedRetrofitApi api;

    DatabaseInteractor database;

    @Inject
    public RetrofitInteractor(FixedRetrofitApi api, AppPreferences preferences) {
        super();
        this.preferences = preferences;
        this.api = api;
        this.database = databaseInteractor;
    }

    @Override
    public Observable<List<TrackModel>> loadTracks() {
        return Observable.defer(() -> Observable.just(preferences.getToken()))
                .delay(1, TimeUnit.SECONDS)
                .flatMap(token -> {
                    if (token.equals("")) {
                        return database.getAllTracks();
                    } else {
                        return api.loadTracks(Token.configure(preferences.getToken()), preferences.getLastUpdateTime())
                                .onErrorResumeNext(throwable -> {
                                    return database.getAllTracks();
                                });
                    }
                })
                .map(trackDTOs -> {
                    preferences.setLastUpdateTime(Convert.getCurrentDate().getTime());
                    return Transform.fromDtoList(trackDTOs);
                });
    }

    @Override
    public Observable<Token> login(String username, String password) {
        return api.login(username, password)
                .map(token -> {
                    preferences.setToken(token.getAuth_token());
                    return token;
                });
    }

    @Override
    public Observable<UserModel> register(String email, String username, String password) {
        return api.register(email, username, password)
                .map(Transform::fromDto);

    }

    @Override
    public Observable<Fix4SuccessResultModel> uploadTrack(String token, TrackDTO dto) {
        return api.uploadTrack(token, dto);
    }
}
