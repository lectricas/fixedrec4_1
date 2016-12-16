package com.example.xals.fixedrec4_1.di.component;



import com.example.xals.fixedrec4_1.di.annotation.PerActivity;
import com.example.xals.fixedrec4_1.di.module.ActivityModule;
import com.example.xals.fixedrec4_1.mvp.main.MainPresenter;
import com.example.xals.fixedrec4_1.mvp.map.TrackViewPresenter;
import com.example.xals.fixedrec4_1.mvp.map.fragment.presenter.MapPresenter;
import com.example.xals.fixedrec4_1.mvp.trackslist.TracksPresenter;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = ActivityModule.class)
public interface ActivityComponent extends ApplicationComponent {

    void inject(MainPresenter presenter);
    void inject(TracksPresenter presenter);
    void inject(TrackViewPresenter presenter);
    void inject(MapPresenter presenter);
}