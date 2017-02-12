package com.example.xals.fixedrec4_1.mvp.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.xals.fixedrec4_1.R;
import com.example.xals.fixedrec4_1.business.model.UserModel;
import com.example.xals.fixedrec4_1.mvp.auth.presenter.SignUpPresenter;
import com.example.xals.fixedrec4_1.mvp.auth.presenter.SignUpViewState;
import com.example.xals.fixedrec4_1.mvp.base.BaseActivity;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity  extends BaseActivity implements SignUpViewState {

    @InjectPresenter
    SignUpPresenter presenter;

    @BindView(R.id.email_field)
    EditText emailField;
    @BindView(R.id.username_field)
    EditText usernameField;
    @BindView(R.id.password_field)
    EditText passwordField;
    @BindView(R.id.signup_button)
    Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        presenter.listernFieldsSignUp(emailField, usernameField, passwordField, signupButton);
    }

    @OnClick(R.id.signup_button)
    public void onClick() {
        presenter.signUp(emailField.getText().toString(), usernameField.getText().toString(),
                passwordField.getText().toString());
    }

    @Override
    public void onRegistered(UserModel model) {
        LoginActivity.startActivity(this);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}