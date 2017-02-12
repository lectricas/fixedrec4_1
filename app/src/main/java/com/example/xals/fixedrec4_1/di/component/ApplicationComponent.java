package com.example.xals.fixedrec4_1.di.component;

import com.example.xals.fixedrec4_1.di.module.ApplicationModule;
import com.example.xals.fixedrec4_1.mvp.auth.presenter.LoginPresenter;
import com.example.xals.fixedrec4_1.mvp.auth.presenter.SignUpPresenter;
import com.example.xals.fixedrec4_1.mvp.main.presenter.MainPresenter;
import com.example.xals.fixedrec4_1.mvp.map.fragment.presenter.MapPresenter;
import com.example.xals.fixedrec4_1.mvp.map.activity.presenter.TrackViewPresenter;
import com.example.xals.fixedrec4_1.mvp.splash.presenter.SplashPresenter;
import com.example.xals.fixedrec4_1.mvp.trackslist.presenter.TracksPresenter;
import com.example.xals.fixedrec4_1.service.LocationService;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = ApplicationModule.class)
@Singleton
public interface ApplicationComponent {

    void inject(LoginPresenter loginPresenter);
    void inject(SignUpPresenter signUpPresenter);
    void inject(SplashPresenter splashPresenter);
    void inject(TracksPresenter tracksPresenter);
    void inject(MainPresenter presenter);
    void inject(TrackViewPresenter presenter);
    void inject(MapPresenter presenter);

    void inject(LocationService locationService);
}
