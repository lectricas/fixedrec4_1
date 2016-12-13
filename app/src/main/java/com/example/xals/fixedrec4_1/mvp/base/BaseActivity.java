package com.example.xals.fixedrec4_1.mvp.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;


import com.example.xals.fixedrec4_1.Fix4Application;
import com.example.xals.fixedrec4_1.R;
import com.example.xals.fixedrec4_1.di.component.ActivityComponent;
import com.example.xals.fixedrec4_1.di.component.ApplicationComponent;
import com.example.xals.fixedrec4_1.di.component.DaggerActivityComponent;
import com.example.xals.fixedrec4_1.di.module.ActivityModule;
import com.example.xals.fixedrec4_1.di.reflection.ComponentReflectionInjector;
import com.example.xals.fixedrec4_1.error.ErrorCallback;
import com.example.xals.fixedrec4_1.error.FixError;
import com.example.xals.fixedrec4_1.mvp.main.MainActivity;

import butterknife.ButterKnife;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.val;
import nucleus.view.NucleusAppCompatActivity;


public abstract class BaseActivity<P extends BasePresenter> extends NucleusAppCompatActivity<P> implements ErrorCallback {

    @Getter(AccessLevel.PROTECTED)
    private ActivityComponent activityComponent;
    private ComponentReflectionInjector refComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();

        refComponent = new ComponentReflectionInjector<>(DaggerActivityComponent.class,
                (DaggerActivityComponent) activityComponent);
        val superFactory = super.getPresenterFactory();
        setPresenterFactory(() -> {
            P presenter = superFactory.createPresenter();
            inject(presenter);
            return presenter;
        });
        super.onCreate(savedInstanceState);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((Fix4Application)getApplication()).getApplicationComponent();
    }

    protected void inject(Object target) {
        refComponent.inject(target);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    public void switchFragment(Fragment fragment, boolean addBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        if (addBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void showErrorMessage(String messsage) {
        showErrorDialog(messsage);
    }

    private void showErrorDialog (String errorMessage) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.title_error_dialog));
        if (errorMessage.equals(FixError.SOCKET_EXCEPTION)) {
            alertDialog.setMessage(getString(R.string.title_error_server_not_responding));
        }
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.title_ok),
                (dialog, which) -> {
                    finish();
                    startActivity(new Intent(BaseActivity.this, MainActivity.class));
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.title_exit_from_app),
                (dialog, which) -> {
                    finish();
                });
        alertDialog.show();
    }
}
