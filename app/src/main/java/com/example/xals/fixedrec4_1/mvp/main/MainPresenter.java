package com.example.xals.fixedrec4_1.mvp.main;


import android.os.Bundle;


import com.example.xals.fixedrec4_1.mvp.base.BasePresenter;
import com.example.xals.fixedrec4_1.util.AppPreferences;

import javax.inject.Inject;

public class MainPresenter extends BasePresenter<MainActivity> {

    @Inject
    AppPreferences preferences;


    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


    }
}
