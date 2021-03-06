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

    @BindView(R.id.email_field)
    EditText emailField;
    @BindView(R.id.password_field)
    EditText passwordField;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.sign_up_text)
    TextView signUpText;

    TextView withoutLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);
        presenter.listernForCredentialsInput(emailField, passwordField, loginButton);
    }

    @OnClick(R.id.sign_up_text)
    public void signUp(View view) {
        SignUpActivity.startActivity(this);
    }

    @OnClick(R.id.login_button)
    public void login(View view) {
        presenter.login(emailField.getText().toString(), passwordField.getText().toString());
    }

    @OnClick(R.id.without_login)
    public void withoutLogin(View view) {
        MainActivity.startActivity(this);
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
