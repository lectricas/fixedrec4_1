package com.example.xals.fixedrec4_1.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.xals.fixedrec4_1.Fix4Application;
import com.example.xals.fixedrec4_1.R;
import com.example.xals.fixedrec4_1.business.dto.TrackDTO;
import com.example.xals.fixedrec4_1.business.interactor.database.IDatabaseInteractor;
import com.example.xals.fixedrec4_1.mvp.map.TrackViewActivity;
import com.example.xals.fixedrec4_1.util.RxBus;

import javax.annotation.Nullable;
import javax.inject.Inject;


public class LocationService extends Service implements LocationListener, GpsStatus.Listener {

    private static final int LOCATION_DELAY = 2000;
    private static final float MIN_DISTANCE = 2;

    @Inject
    RxBus rxBus;

    @Inject
    IDatabaseInteractor databaseInteractor;

    private final IBinder mBinder = new MyBinder();

    private LocationManager manager;
    private TrackDTO currentTrackDTO;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ((Fix4Application) getApplication()).getServiceComponent().inject(this);
        databaseInteractor.getCurrentTrackNoPoints().subscribe(trackDTO -> {
            currentTrackDTO = trackDTO;
            initializeNotificationIntent(trackDTO);
        });

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    private void initializeNotificationIntent(TrackDTO currentTrackDTO) {
        Intent notificationIntent = TrackViewActivity.getIntentInstance(this,
                true, currentTrackDTO.getUuid());

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);


        if (currentTrackDTO.isRunning()) {
            Notification notification = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("My Awesome App")
                    .setContentText("Doing some work...")
                    .setContentIntent(pendingIntent).build();
            startForeground(1337, notification);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @SuppressWarnings("ResourceType")
    public void registerLocationUpdates() {
        manager.addGpsStatusListener(this);
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                LOCATION_DELAY, MIN_DISTANCE, this);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_DELAY,
                MIN_DISTANCE, this);
    }

    @SuppressWarnings("ResourceType")
    public void unregisterLocationUpdates() {
        manager.removeGpsStatusListener(this);
        manager.removeUpdates(this);
        stopForeground(true);
    }

    @Override
    public void onLocationChanged(Location location) {

        if (currentTrackDTO.isRunning()) {
            databaseInteractor.saveTrackPoint(
                    currentTrackDTO.getId(),
                    location.getLatitude(),
                    location.getLongitude(),
                    location.getAccuracy(),
                    location.getSpeed()
            );
            Log.d("LocationService", "PointSaved");
        } else {
            Log.d("LocationService", "SkipPoint");
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    //GpsStatusListener only method
    @Override
    public void onGpsStatusChanged(int i) {

    }

    public class MyBinder extends Binder {
        public LocationService getService() {
            return LocationService.this;
        }
    }
}