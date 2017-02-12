package com.example.xals.fixedrec4_1.mvp.base.presenter;

import com.arellomobile.mvp.MvpView;
import com.example.xals.fixedrec4_1.error.FixedCustomError;


public interface BaseViewState extends MvpView {
    void onError(FixedCustomError throwable);
}
