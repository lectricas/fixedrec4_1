package com.example.xals.fixedrec4_1.mvp.trackslist;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.xals.fixedrec4_1.R;
import com.example.xals.fixedrec4_1.business.dto.TrackDTO;
import com.example.xals.fixedrec4_1.mvp.base.BaseFragment;
import com.example.xals.fixedrec4_1.mvp.map.TrackViewActivity;
import com.example.xals.fixedrec4_1.mvp.trackslist.adapter.LinearManagerWithScroll;
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
    LinearManagerWithScroll manager;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        manager = new LinearManagerWithScroll(getActivity());
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        trackAdapter = new TrackAdapter(getContext(), this);
        tracksRv.setLayoutManager(manager);
        tracksRv.setAdapter(trackAdapter);
        getPresenter().getAllTracks();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().checkTrackStateForView();
    }

    public void onGotTracks(List<TrackDTO> tracks) {
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
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        Snackbar.make(coordinatorLayout, R.string.permissions_not_granted,
                                Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(coordinatorLayout, "Go to settings to change the permission",
                                Snackbar.LENGTH_SHORT)
                                .setAction("Settings", view -> {
                                    Intent intent = new Intent();
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                })
                                .setActionTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                                .show();
                    }
                });
    }

    //this callback determinates if track is running and if fab is visible
    public void showFab(boolean showFab) {
        fab.setVisibility(showFab? View.VISIBLE: View.GONE);
    }

    @Override
    public void onItemClicked(TrackDTO trackDTO) {
        startActivityForResult(TrackViewActivity.getIntentInstance(getContext(),
                false, trackDTO.getUuid()), Convert.REQUEST_TRACK_CLOSE);
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

    public void onClosedTrackLoadedUIUpdate(TrackDTO closedTrack) {
        trackAdapter.update(closedTrack);
    }

    public void onNewTrackSaved(TrackDTO track) {
        trackAdapter.add(track);
        tracksRv.scrollToPosition(trackAdapter.getItemCount() - 1);
        startActivityForResult(TrackViewActivity.getIntentInstance(getContext(),
                true, track.getUuid()), Convert.REQUEST_TRACK_CLOSE);
    }
}