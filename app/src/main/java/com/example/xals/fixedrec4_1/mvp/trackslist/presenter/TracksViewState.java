package com.example.xals.fixedrec4_1.mvp.trackslist.presenter;


import com.example.xals.fixedrec4_1.business.model.TrackModel;
import com.example.xals.fixedrec4_1.mvp.base.presenter.BaseViewState;
import com.example.xals.fixedrec4_1.repository.dto.TrackDTO;

import java.util.List;

public interface TracksViewState extends BaseViewState {
    void onGotTracks(List<TrackModel> trackDTOs);

    void onNewTrackSaved(TrackModel model);

    void onClosedTrackLoadedUIUpdate(TrackModel model);

    void showFab(Boolean aBoolean);

    void permissionNotGranted();

    void notGrantedChangeInSettings();
}
