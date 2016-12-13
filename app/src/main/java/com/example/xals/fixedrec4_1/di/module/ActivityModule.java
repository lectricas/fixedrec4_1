package com.example.xals.fixedrec4_1.di.module;

import android.app.Activity;


import com.example.xals.fixedrec4_1.business.interactor.database.DatabaseInteractor;
import com.example.xals.fixedrec4_1.business.interactor.database.IDatabaseInteractor;
import com.example.xals.fixedrec4_1.business.interactor.viewbindings.IViewInteractor;
import com.example.xals.fixedrec4_1.business.interactor.viewbindings.ViewInteractor;
import com.example.xals.fixedrec4_1.di.annotation.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity provideActivity() {
        return activity;
    }

    @Provides
    @PerActivity
    IDatabaseInteractor providesDatabaseInteractor(DatabaseInteractor databaseInteractor) {
        return databaseInteractor;
    }

    @Provides
    @PerActivity
    IViewInteractor providesViewInteractor(ViewInteractor viewInteractor) {
        return viewInteractor;
    }
}