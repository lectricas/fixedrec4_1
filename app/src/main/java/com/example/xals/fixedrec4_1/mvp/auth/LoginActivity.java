package com.example.xals.fixedrec4_1.mvp.auth;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.xals.fixedrec4_1.R;
import com.example.xals.fixedrec4_1.mvp.auth.presenter.LoginPresenter;
import com.example.xals.fixedrec4_1.mvp.auth.presenter.LoginViewState;
import com.example.xals.fixedrec4_1.mvp.base.BaseActivity;
import com.example.xals.fixedrec4_1.mvp.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginViewState {

    @InjectPresenter
    LoginPresenter presenter;

    @BindView(R.id.emailField)
    EditText emailField;
    @BindView(R.id.passwordField)
    EditText passwordField;
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.signUpText)
    TextView signUpText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);
        presenter.listernForCredentialsInput(emailField, passwordField, loginButton);
    }

    @OnClick(R.id.signUpText)
    public void signUp(View view) {
        SignUpActivity.startActivity(this);
    }

    @OnClick(R.id.loginButton)
    public void login(View view) {
        presenter.login(emailField.getText().toString(), passwordField.getText().toString());
    }

    @Override
    public void onLogin(String token) {
        MainActivity.startActivity(this);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
