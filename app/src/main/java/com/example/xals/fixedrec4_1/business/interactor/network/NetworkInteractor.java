package com.example.xals.fixedrec4_1.business.interactor.network;


import com.example.xals.fixedrec4_1.business.model.Fix4SuccessResultModel;
import com.example.xals.fixedrec4_1.business.model.TrackModel;
import com.example.xals.fixedrec4_1.business.model.UserModel;
import com.example.xals.fixedrec4_1.repository.dto.Token;
import com.example.xals.fixedrec4_1.repository.dto.TrackDTO;

import java.util.List;

import rx.Observable;

public interface NetworkInteractor {

    Observable<List<TrackModel>> loadTracks();

    Observable<Token> login(String username, String password);

    Observable<UserModel> register(String email, String username, String password);

    Observable<Fix4SuccessResultModel> uploadTrack(String token, TrackDTO dto);
}
