package com.example.xals.fixedrec4_1.di.module;


import com.example.xals.fixedrec4_1.business.interactor.database.DatabaseInteractor;
import com.example.xals.fixedrec4_1.business.interactor.database.IDatabaseInteractor;
import com.example.xals.fixedrec4_1.di.annotation.PerService;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    @Provides
    @PerService
    IDatabaseInteractor providesDatabaseInteractor(DatabaseInteractor databaseInteractor) {
        return databaseInteractor;
    }
}
