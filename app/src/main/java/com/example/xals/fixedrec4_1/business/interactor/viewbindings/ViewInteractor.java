package com.example.xals.fixedrec4_1.business.interactor.viewbindings;


import com.example.xals.fixedrec4_1.repository.database.IDatabaseRepository;
import com.example.xals.fixedrec4_1.util.AppPreferences;

import javax.inject.Inject;

import rx.Observable;

public class ViewInteractor implements IViewInteractor {

    AppPreferences preferences;
    IDatabaseRepository databaseRepository;

    @Inject
    public ViewInteractor(AppPreferences preferences, IDatabaseRepository databaseRepository) {
        super();
        this.preferences = preferences;
        this.databaseRepository = databaseRepository;
    }


    @Override
    public Observable<Boolean> controlFabButton() {
        return databaseRepository.getTrackByUUID(preferences.getCurrentTrackUUID())
                .map(trackDTO -> trackDTO == null || !trackDTO.isRunning());
    }
}
