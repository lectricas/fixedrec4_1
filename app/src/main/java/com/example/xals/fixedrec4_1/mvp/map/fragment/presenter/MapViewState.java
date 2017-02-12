package com.example.xals.fixedrec4_1.mvp.map.fragment.presenter;


import com.example.xals.fixedrec4_1.repository.dto.PointDTO;
import com.example.xals.fixedrec4_1.mvp.base.presenter.BaseViewState;

public interface MapViewState extends BaseViewState {
    void pointReceived(PointDTO pointDTO);
}
