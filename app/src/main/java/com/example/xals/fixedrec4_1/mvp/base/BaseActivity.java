package com.example.xals.fixedrec4_1.mvp.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.example.xals.fixedrec4_1.R;
import com.example.xals.fixedrec4_1.error.FixError;
import com.example.xals.fixedrec4_1.error.FixedCustomError;
import com.example.xals.fixedrec4_1.mvp.base.presenter.BaseViewState;
import com.example.xals.fixedrec4_1.mvp.main.MainActivity;

import butterknife.ButterKnife;


public abstract class BaseActivity extends MvpAppCompatActivity implements BaseViewState {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        fragmentTransaction.commit();
    }

    @Override
    public void onError(FixedCustomError error) {
        showErrorDialog(error.getMessage());
    }

    private void showErrorDialog (String errorMessage) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.title_error_dialog));
        alertDialog.setMessage(errorMessage);
        if (errorMessage.equals(FixError.SOCKET_EXCEPTION)) {
            alertDialog.setMessage(getString(R.string.title_error_server_not_responding));
        }
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.title_ok),
                (dialog, which) -> {
                    alertDialog.cancel();
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.title_exit_from_app),
                (dialog, which) -> {
                    alertDialog.cancel();
                    finish();
                });
        alertDialog.show();
    }
}
