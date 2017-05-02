package com.example.xals.fixedrec4_1.mvp.base.presenter;


import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.EditText;

import com.arellomobile.mvp.MvpPresenter;
import com.example.xals.fixedrec4_1.error.FixedCustomError;
import com.example.xals.fixedrec4_1.repository.FixedRetrofitApi;
import com.example.xals.fixedrec4_1.repository.AppPreferences;
import com.example.xals.fixedrec4_1.util.RxBus;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import java.net.SocketTimeoutException;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public class BasePresenter<V extends BaseViewState> extends MvpPresenter<V> {

    @Inject
    public RxBus rxBus;

    @Inject
    protected FixedRetrofitApi api;

    @Inject
    protected AppPreferences preferences;

    public void listernForCredentialsInput(EditText emailField, EditText passwordField, Button signUpButton) {
        Observable<TextViewTextChangeEvent> emailObservable = RxTextView.textChangeEvents(emailField);
        Observable<TextViewTextChangeEvent> passwordObservable = RxTextView.textChangeEvents(passwordField);
        Observable.combineLatest(emailObservable, passwordObservable,
                (emailEvent, passwordEvent) -> {
                    boolean emailCheck = emailEvent.text().length() >= 3;
                    boolean passwordCheck = passwordEvent.text().length() >= 6;
                    return emailCheck && passwordCheck;
                }).subscribe(signUpButton::setEnabled);
    }

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    protected void unsubscribeOnDestroy(@NonNull Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        compositeSubscription.clear();
    }

    protected void returnError(Throwable throwable) {
        FixedCustomError error = new FixedCustomError();
        if (throwable instanceof SocketTimeoutException) {
            error.setMessage("Server not responding");
        } else {
            error.setMessage(throwable.getMessage());
        }
        getViewState().onError(error);
    }
}