package com.example.xals.fixedrec4_1;

import android.app.Application;

import com.activeandroid.sebbia.ActiveAndroid;
import com.example.xals.fixedrec4_1.di.component.ApplicationComponent;
import com.example.xals.fixedrec4_1.di.component.DaggerApplicationComponent;
import com.example.xals.fixedrec4_1.di.component.DaggerServiceComponent;
import com.example.xals.fixedrec4_1.di.component.ServiceComponent;
import com.example.xals.fixedrec4_1.di.module.ApplicationModule;
import com.example.xals.fixedrec4_1.di.module.ServiceModule;
import com.facebook.stetho.Stetho;

import lombok.Getter;

public class Fix4Application extends Application {

    @Getter
    private ApplicationComponent applicationComponent;

    @Getter
    ServiceComponent serviceComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        ActiveAndroid.initialize(this);

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        serviceComponent = DaggerServiceComponent.builder()
                .serviceModule(new ServiceModule())
                .applicationComponent(applicationComponent)
                .build();

        Stetho.initializeWithDefaults(this);
    }
}