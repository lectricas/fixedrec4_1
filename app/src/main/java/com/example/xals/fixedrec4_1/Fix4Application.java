package com.example.xals.fixedrec4_1;

import android.app.Application;

import com.activeandroid.sebbia.ActiveAndroid;
import com.example.xals.fixedrec4_1.di.component.ApplicationComponent;
import com.example.xals.fixedrec4_1.di.component.DaggerApplicationComponent;
import com.example.xals.fixedrec4_1.di.module.ApplicationModule;
import com.facebook.stetho.Stetho;

import lombok.Getter;

public class Fix4Application extends Application {

    @Getter
    private static ApplicationComponent applicationComponent;


    @Override
    public void onCreate() {
        super.onCreate();

        ActiveAndroid.initialize(this);

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        Stetho.initializeWithDefaults(this);
    }
}