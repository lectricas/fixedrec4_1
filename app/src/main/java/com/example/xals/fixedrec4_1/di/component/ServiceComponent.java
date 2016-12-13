package com.example.xals.fixedrec4_1.di.component;


import com.example.xals.fixedrec4_1.di.annotation.PerService;
import com.example.xals.fixedrec4_1.di.module.ServiceModule;
import com.example.xals.fixedrec4_1.service.LocationService;

import dagger.Component;


@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent extends ApplicationComponent {
    void inject(LocationService locationService);
}
