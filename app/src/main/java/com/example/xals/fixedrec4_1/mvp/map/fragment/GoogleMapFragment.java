package com.example.xals.fixedrec4_1.mvp.map.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.xals.fixedrec4_1.R;
import com.example.xals.fixedrec4_1.repository.dto.PointDTO;
import com.example.xals.fixedrec4_1.repository.dto.TrackDTO;
import com.example.xals.fixedrec4_1.mvp.base.BaseFragment;
import com.example.xals.fixedrec4_1.mvp.map.fragment.presenter.MapViewState;
import com.example.xals.fixedrec4_1.mvp.map.activity.TrackDisplayActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class GoogleMapFragment extends BaseFragment implements OnMapReadyCallback, MapViewState {

    private GoogleMap map;
    private PolylineOptions polylineOptions;
    private LatLngBounds.Builder boundsBuilder;
    private TrackDTO trackUI;
    private boolean canAddPoints;

    @BindView(R.id.current_track_uuid)
    TextView currentTrackUUIDView;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_google_map;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeMap();

        trackUI = ((TrackDisplayActivity) getActivity()).getTrackDTO();
        currentTrackUUIDView.setText(trackUI.getUuid());
    }

    public void initializeMap(){
        canAddPoints = false;
        if (map == null){
            SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void pointReceived(PointDTO point) {
        if (canAddPoints) {
            map.clear();
            LatLng latLng = new LatLng(point.getLat(), point.getLng());
            boundsBuilder.include(latLng);
            polylineOptions.add(latLng);
            map.addPolyline(polylineOptions);
            drawMePointer(latLng, point.getBearing());
            animateCameraToUser(latLng);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        polylineOptions = new PolylineOptions().width(5).color(Color.BLUE);
        boundsBuilder = new LatLngBounds.Builder();
        canAddPoints = true;
        // безопасно потому что франмент создается после инициализации trackUI
        drawPolyline(trackUI.getPoints());
    }

    private void drawPolyline(List<PointDTO> pointDTOs) {
        if (pointDTOs.size() == 0){
            return;
        }

        map.clear();
        ArrayList<LatLng> latLngs = new ArrayList<>();
        LatLng latlng;
        for (PointDTO point : pointDTOs) {
            latlng = new LatLng(point.getLat(), point.getLng());
            latLngs.add(latlng);
            boundsBuilder.include(latlng);
        }

        polylineOptions.addAll(latLngs);
        map.addPolyline(polylineOptions);


        if (!trackUI.isRunning()){
            drawStartStopMarkersAndAnim(latLngs);
        } else {
            animateCameraWholeTrack();
        }
    }

    private void drawStartStopMarkersAndAnim(ArrayList<LatLng> latLngs){
        Marker marker = map.addMarker(new MarkerOptions().position(latLngs.get(0))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        map.addMarker(new MarkerOptions().position(latLngs.get(latLngs.size() - 1))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
        animateCameraWholeTrack();
    }

    private void animateCameraWholeTrack() {
        LatLngBounds tmpBounds = boundsBuilder.build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(tmpBounds, 50);
        map.animateCamera(cameraUpdate);
    }

    private void animateCameraToUser(LatLng latLng) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 50);
        map.animateCamera(cameraUpdate);
    }

    private void drawMePointer(LatLng latLng, float bearing) {
        map.addMarker(new MarkerOptions().position(latLng).rotation(bearing)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
    }

    public static GoogleMapFragment getInstance() {
        return new  GoogleMapFragment();
    }
}
