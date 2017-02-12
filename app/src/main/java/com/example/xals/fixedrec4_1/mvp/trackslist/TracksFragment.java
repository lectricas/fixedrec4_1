package com.example.xals.fixedrec4_1.mvp.trackslist;

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

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.xals.fixedrec4_1.R;
import com.example.xals.fixedrec4_1.business.model.TrackModel;
import com.example.xals.fixedrec4_1.mvp.map.activity.TrackDisplayActivity;
import com.example.xals.fixedrec4_1.mvp.trackslist.adapter.EndlessRecyclerViewScrollListener;
import com.example.xals.fixedrec4_1.mvp.trackslist.presenter.TracksPresenter;
import com.example.xals.fixedrec4_1.mvp.trackslist.presenter.TracksViewState;
import com.example.xals.fixedrec4_1.mvp.base.BaseFragment;
import com.example.xals.fixedrec4_1.mvp.trackslist.adapter.LinearManagerWithScroll;
import com.example.xals.fixedrec4_1.mvp.trackslist.adapter.TrackAdapter;
import com.example.xals.fixedrec4_1.util.Convert;

import java.util.List;


import butterknife.BindView;
import butterknife.OnClick;

public class TracksFragment extends BaseFragment implements TracksViewState {

    @InjectPresenter
    TracksPresenter presenter;

    @BindView(R.id.tracks_list)
    RecyclerView tracksRv;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;

    TrackAdapter trackAdapter;
    LinearManagerWithScroll manager;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        manager = new LinearManagerWithScroll(getActivity());
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        trackAdapter = new TrackAdapter(getContext(), model ->
                startActivityForResult(TrackDisplayActivity.intent(getContext(),
                        false, model.getUuid()), Convert.REQUEST_TRACK_CLOSE));
        // TODO: 12.02.17 SKIP, TAKE serverSide
//        tracksRv.addOnScrollListener(new EndlessRecyclerViewScrollListener(manager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                presenter.loadTracksFromServer();
//            }
//        });
        tracksRv.setLayoutManager(manager);
        tracksRv.setAdapter(trackAdapter);
        presenter.getAllTracksFromDb();
        presenter.loadTracksFromServer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.checkTrackStateForView();
    }

    @Override
    public void onGotTracks(List<TrackModel> tracks) {
        trackAdapter.addAll(tracks);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_tracks_list;
    }

    @OnClick(R.id.fab)
    public void onClick() {
        presenter.requestPermissionAndStart(getActivity());
    }

    @Override
    public void permissionNotGranted() {
        Snackbar.make(coordinatorLayout, R.string.permissions_not_granted,
                Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void notGrantedChangeInSettings() {
        Snackbar.make(coordinatorLayout, R.string.change_permission_in_settings,
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

    //this callback determinates if track is running and if fab is visible
    @Override
    public void showFab(Boolean showFab) {
        fab.setVisibility(showFab? View.VISIBLE: View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Convert.REQUEST_TRACK_CLOSE) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(getContext(), "TRACK CLOSED",
                        Toast.LENGTH_SHORT).show();
                presenter.getClosedTrackToUpdateUI();

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getContext(), getString(R.string.no_need_to_refresh),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClosedTrackLoadedUIUpdate(TrackModel closedTrack) {
        trackAdapter.add(closedTrack);
    }

    @Override
    public void onNewTrackSaved(TrackModel track) {
        trackAdapter.add(track);
        tracksRv.scrollToPosition(trackAdapter.getItemCount() - 1);
        startActivityForResult(TrackDisplayActivity.intent(getContext(),
                true, track.getUuid()), Convert.REQUEST_TRACK_CLOSE);
    }
}