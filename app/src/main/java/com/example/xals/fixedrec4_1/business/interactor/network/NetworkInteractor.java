package com.example.xals.fixedrec4_1.business.interactor.network;


import com.example.xals.fixedrec4_1.business.model.TrackModel;
import com.example.xals.fixedrec4_1.business.model.UserModel;
import com.example.xals.fixedrec4_1.repository.dto.Token;

import java.util.List;

import rx.Observable;

public interface NetworkInteractor {

    Observable<List<TrackModel>> loadTracks();

    Observable<Token> login(String username, String password);

    Observable<UserModel> register(String email, String username, String password);
}
