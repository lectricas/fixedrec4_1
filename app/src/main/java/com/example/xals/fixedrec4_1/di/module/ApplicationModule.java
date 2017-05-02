package com.example.xals.fixedrec4_1.di.module;

import android.app.Application;
import android.content.Context;

import com.example.xals.fixedrec4_1.Fix4Application;
import com.example.xals.fixedrec4_1.business.interactor.database.ActiveAndroidInteractor;
import com.example.xals.fixedrec4_1.business.interactor.database.DatabaseInteractor;
import com.example.xals.fixedrec4_1.business.interactor.network.NetworkInteractor;
import com.example.xals.fixedrec4_1.business.interactor.network.RetrofitInteractor;
import com.example.xals.fixedrec4_1.business.interactor.viewbindings.IViewInteractor;
import com.example.xals.fixedrec4_1.business.interactor.viewbindings.ViewInteractor;
import com.example.xals.fixedrec4_1.repository.FixedRetrofitApi;
import com.example.xals.fixedrec4_1.repository.AppPreferences;
import com.example.xals.fixedrec4_1.util.RxBus;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import proxypref.ProxyPreferences;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {
    private final Fix4Application application;

    public ApplicationModule(Fix4Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }

    @Provides
    @Singleton
    AppPreferences providesAppPreferences() {
        return ProxyPreferences.build(AppPreferences.class,
                application.getSharedPreferences(AppPreferences.NAME, Context.MODE_PRIVATE));
    }

    @Provides
    @Singleton
    OkHttpClient provideHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    @Provides
    @Singleton
    FixedRetrofitApi provideServerApi(OkHttpClient client){
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(FixedRetrofitApi.ENDPOINT)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(
                        GsonConverterFactory.create(new GsonBuilder()
                                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                                .serializeNulls()
                                .create()))
                .build();

        return retrofit.create(FixedRetrofitApi.class);
    }

    @Provides
    @Singleton
    Gson providesGson() {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC, Modifier.PRIVATE);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    RxBus providesRxBus() {
        return new RxBus();
    }


    @Provides
    @Singleton
    DatabaseInteractor providesDatabaseInteractor(ActiveAndroidInteractor activeAndroidInteractor) {
        return activeAndroidInteractor;
    }

    @Provides
    @Singleton
    NetworkInteractor providesNetworkInteractor(RetrofitInteractor interactor) {
        return interactor;
    }

    @Provides
    @Singleton
    IViewInteractor providesViewInteractor(ViewInteractor viewInteractor) {
        return viewInteractor;
    }
}
