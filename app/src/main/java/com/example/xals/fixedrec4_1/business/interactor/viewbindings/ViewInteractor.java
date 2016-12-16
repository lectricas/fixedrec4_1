package com.example.xals.fixedrec4_1.business.interactor.viewbindings;


import com.example.xals.fixedrec4_1.repository.database.ActiveAndroidHelper;
import com.example.xals.fixedrec4_1.util.AppPreferences;

import javax.inject.Inject;

import rx.Observable;

public class ViewInteractor implements IViewInteractor {

    AppPreferences preferences;

    @Inject
    public ViewInteractor(AppPreferences preferences) {
        super();
        this.preferences = preferences;
    }


    @Override
    public Observable<Boolean> controlFabButton() {
        return ActiveAndroidHelper.getTrackByUUID(preferences.getCurrentTrackUUID())
                .map(trackDTO -> trackDTO == null || !trackDTO.isRunning());
    }
}
