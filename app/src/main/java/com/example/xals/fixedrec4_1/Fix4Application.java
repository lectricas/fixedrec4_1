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

//    SingleEntityStore<Persistable> dataStore;

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


//        TrackR trackR = new TrackR();
//        trackR.setDateCreated(Convert.getCurrentDate());
//        trackR.setTrack_owner("user1");
//        trackR.setIsRunning(true);
//        PointR pointR = new PointR();
//        pointR.setDateCreated(Convert.getCurrentDate());
//        trackR.getPoints().add(pointR);
//
//
//        DatabaseSource source = new DatabaseSource(this, Models.DEFAULT, 1);
//        Configuration configuration = source.getConfiguration();
//        EntityDataStore generalDataStore = new EntityDataStore<>(configuration);
//        generalDataStore.insert(trackR);
//
//        Result<TrackR> result = generalDataStore.select(TrackR.class).get();
    }


//    public SingleEntityStore<Persistable> getData() {
//        if(dataStore == null) {
//            DatabaseSource source = new DatabaseSource(this, Models.DEFAULT, "databaseRequery", 1);
//            Configuration configuration = source.getConfiguration();
//            dataStore = RxSupport.toReactiveStore(new EntityDataStore<Persistable>(configuration));
//        }
//        return dataStore;
//    }
}