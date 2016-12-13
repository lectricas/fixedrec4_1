package com.example.xals.fixedrec4_1.mvp.base;


import com.example.xals.fixedrec4_1.error.ErrorCallback;
import com.example.xals.fixedrec4_1.error.FixError;
import com.example.xals.fixedrec4_1.util.RxBus;

import javax.inject.Inject;

import nucleus.presenter.RxPresenter;


public class BasePresenter<V> extends RxPresenter<V> {

    @Inject
    public RxBus rxBus;

    public void onError(V view, Throwable throwable) {
        if (view != null && view instanceof ErrorCallback) {
            FixError error = new FixError(throwable);
            error.printStackTrace();
            ((ErrorCallback) view).showErrorMessage(error.getCustomMessage());
        }
    }
}