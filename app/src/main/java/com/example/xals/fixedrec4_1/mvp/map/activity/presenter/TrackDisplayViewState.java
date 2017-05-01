package com.example.xals.fixedrec4_1.mvp.map.activity.presenter;

import com.example.xals.fixedrec4_1.business.model.Fix4SuccessResultModel;
import com.example.xals.fixedrec4_1.repository.dto.TrackDTO;
import com.example.xals.fixedrec4_1.mvp.base.presenter.BaseViewState;

public interface TrackDisplayViewState extends BaseViewState {
    void onTrackClosed(TrackDTO trackDTO);
    void onTrackForDisplayLoaded(TrackDTO dto, int gone);
}
