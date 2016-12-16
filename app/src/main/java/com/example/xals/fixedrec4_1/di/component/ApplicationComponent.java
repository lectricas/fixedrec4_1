package com.example.xals.fixedrec4_1.di.component;

import android.app.Application;
import android.content.Context;

import com.example.xals.fixedrec4_1.di.module.ApplicationModule;
import com.example.xals.fixedrec4_1.repository.FixedRetrofitApi;
import com.example.xals.fixedrec4_1.util.AppPreferences;
import com.example.xals.fixedrec4_1.util.RxBus;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = ApplicationModule.class)
@Singleton
public interface ApplicationComponent {
    Context provideContext();

    Application providesApplication();

    AppPreferences providesAppPreferences();

    FixedRetrofitApi provideServerApi();

    Gson providesGson();

    RxBus providesRxBus();
}
