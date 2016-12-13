package com.example.xals.fixedrec4_1.mvp.trackslist;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.xals.fixedrec4_1.R;
import com.example.xals.fixedrec4_1.mvp.base.BaseFragment;
import com.example.xals.fixedrec4_1.mvp.map.TrackViewActivity;
import com.example.xals.fixedrec4_1.mvp.map.TrackViewActivityIntentBuilder;
import com.example.xals.fixedrec4_1.mvp.model.TrackUI;
import com.example.xals.fixedrec4_1.mvp.trackslist.adapter.TrackAdapter;
import com.example.xals.fixedrec4_1.util.Convert;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(TracksPresenter.class)
public class TracksFragment extends BaseFragment<TracksPresenter> implements TrackAdapter.OnItemClickListener {

    @Bind(R.id.tracks_list)
    RecyclerView tracksRv;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Bind(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;

    TrackAdapter trackAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        trackAdapter = new TrackAdapter(getContext(), this);
        tracksRv.setLayoutManager(manager);
        tracksRv.setAdapter(trackAdapter);
        getPresenter().getAllTracks();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().checkTrackStateForView();
    }

    public void onGotTracks(List<TrackUI> tracks) {
        trackAdapter.setTracks(tracks);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_tracks_list;
    }

    @OnClick(R.id.fab)
    public void onClick() {
        requestPermissionReactive();
    }

    private void requestPermissionReactive() {

        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions
                .requestEach(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(permission -> {
                    if (permission.granted) {
                        getPresenter().createNewTrack();
                        Snackbar.make(coordinatorLayout, R.string.permision_available_location,
                                Snackbar.LENGTH_SHORT).show();
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        Snackbar.make(coordinatorLayout, R.string.permissions_not_granted,
                                Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(coordinatorLayout, "Go to settings to change the permission",
                                Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    //this callback determinates if track is running and if fab is visible
    public void showFab(boolean showFab) {
        fab.setVisibility(showFab? View.VISIBLE: View.GONE);
    }

    @Override
    public void onItemClicked(TrackUI trackDTO) {
        startActivityForResult(new TrackViewActivityIntentBuilder(
                false, trackDTO.getUuid()).build(getActivity()),
                Convert.REQUEST_TRACK_CLOSE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Convert.REQUEST_TRACK_CLOSE) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(getContext(), "TRACK CLOSED",
                        Toast.LENGTH_SHORT).show();
                getPresenter().getClosedTrackToUpdateUI();

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getContext(), getString(R.string.no_need_to_refresh),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public  void onClosedTrackLoadedUIUpdate(TrackUI closedTrack) {
        trackAdapter.update(closedTrack);
    }

    public void onNewTrackSaved(TrackUI track) {
        trackAdapter.add(track);
        startActivityForResult(new TrackViewActivityIntentBuilder(
                true, track.getUuid()).build(getActivity()),
                Convert.REQUEST_TRACK_CLOSE);
    }
}