package com.example.xals.fixedrec4_1.mvp.map.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.xals.fixedrec4_1.R;
import com.example.xals.fixedrec4_1.business.dto.PointDTO;
import com.example.xals.fixedrec4_1.mvp.base.BaseFragment;
import com.example.xals.fixedrec4_1.mvp.map.TrackViewActivity;
import com.example.xals.fixedrec4_1.mvp.map.fragment.presenter.MapPresenter;
import com.example.xals.fixedrec4_1.mvp.model.TrackUI;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(MapPresenter.class)
public class GoogleMapFragment extends BaseFragment<MapPresenter> implements OnMapReadyCallback {

    private GoogleMap map;
    private PolylineOptions polylineOptions;
    private LatLngBounds.Builder boundsBuilder;
    private TrackUI trackUI;

    @Bind(R.id.current_track_uuid)
    TextView currentTrackUUIDView;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_google_map;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeMap();
        trackUI = ((TrackViewActivity) getActivity()).getTrackUI();
        currentTrackUUIDView.setText(trackUI.getUuid());
    }

    public void initializeMap(){
        if (map == null){
            SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        polylineOptions = new PolylineOptions().width(5).color(Color.BLUE);
        // безопасно потому что франмент создается после инициализации trackUI
        drawPolyline(trackUI.getPoints());
    }

    private void drawPolyline(List<PointDTO> pointDTOs) {
        if (pointDTOs.size() == 0){
            return;
        }
        boundsBuilder = new LatLngBounds.Builder();
        map.clear();
        ArrayList<LatLng> latLngs = new ArrayList<>();
        LatLng latlng;
        for (PointDTO pointDTO : pointDTOs) {
            latlng = new LatLng(pointDTO.getLat(), pointDTO.getLng());
            latLngs.add(latlng);
            boundsBuilder.include(latlng);
        }

        polylineOptions.addAll(latLngs);
        map.addPolyline(polylineOptions);

//        if (!recording){
//            drawStartStopMarkersAndAnim(latLngs);
//        }
    }


    private void drawStartStopMarkersAndAnim(ArrayList<LatLng> latLngs){

        map.addMarker(new MarkerOptions().position(latLngs.get(0))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        map.addMarker(new MarkerOptions().position(latLngs.get(latLngs.size() - 1))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
        LatLngBounds tmpBounds = boundsBuilder.build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(tmpBounds, 50);
        map.animateCamera(cameraUpdate);
    }

    public static GoogleMapFragment getInstance() {
        return new  GoogleMapFragment();
    }
}
