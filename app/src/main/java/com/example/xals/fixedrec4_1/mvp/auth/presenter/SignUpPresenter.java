package com.example.xals.fixedrec4_1.mvp.auth.presenter;

import android.widget.Button;
import android.widget.EditText;

import com.arellomobile.mvp.InjectViewState;
import com.example.xals.fixedrec4_1.Fix4Application;
import com.example.xals.fixedrec4_1.business.interactor.network.NetworkInteractor;
import com.example.xals.fixedrec4_1.business.model.Transform;
import com.example.xals.fixedrec4_1.mvp.base.presenter.BasePresenter;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@InjectViewState
public class SignUpPresenter extends BasePresenter<SignUpViewState> {

    @Inject
    NetworkInteractor network;

    public SignUpPresenter() {
        Fix4Application.getApplicationComponent().inject(this);
    }

    public void signUp(String email, String username, String password) {

        Subscription register = network.register(email, username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userModel -> {
                    getViewState().onRegistered(userModel);
                });
        unsubscribeOnDestroy(register);
    }

    public void listernFieldsSignUp(EditText emailField, EditText username, EditText passwordField, Button signUpButton) {
        Observable<TextViewTextChangeEvent> emailObservable = RxTextView.textChangeEvents(emailField);
        Observable<TextViewTextChangeEvent> usernameObserbale = RxTextView.textChangeEvents(username);
        Observable<TextViewTextChangeEvent> passwordObservable = RxTextView.textChangeEvents(passwordField);
        Observable.combineLatest(emailObservable, passwordObservable, usernameObserbale,
                (emailEvent, passwordEvent, usernameEvent) -> {
                    boolean emailCheck = emailEvent.text().length() >= 3;
                    boolean passwordCheck = passwordEvent.text().length() >= 6;
                    boolean usernameCheck = usernameEvent.text().length() >= 4;
                    return emailCheck && passwordCheck && usernameCheck;
                }).subscribe(signUpButton::setEnabled);
    }
}
