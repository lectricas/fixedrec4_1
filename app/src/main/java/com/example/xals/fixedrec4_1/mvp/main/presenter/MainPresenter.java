package com.example.xals.fixedrec4_1.mvp.main.presenter;


import com.example.xals.fixedrec4_1.Fix4Application;
import com.example.xals.fixedrec4_1.mvp.base.presenter.BasePresenter;

public class MainPresenter extends BasePresenter<MainViewState> {


    public MainPresenter() {
        Fix4Application.getApplicationComponent().inject(this);
    }

    public void logout() {
        preferences.setToken("");
    }
}
