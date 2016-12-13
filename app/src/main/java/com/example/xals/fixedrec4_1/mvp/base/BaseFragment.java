package com.example.xals.fixedrec4_1.mvp.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xals.fixedrec4_1.error.ErrorCallback;

import butterknife.ButterKnife;
import lombok.val;
import nucleus.view.NucleusSupportFragment;

public abstract class BaseFragment<P extends BasePresenter> extends NucleusSupportFragment<P>
        implements ErrorCallback {

    @CallSuper
    @Override
    public void onCreate(Bundle bundle) {
        val superFactory = super.getPresenterFactory();
        setPresenterFactory(() -> {
            P presenter = superFactory.createPresenter();
            ((BaseActivity)getActivity()).inject(presenter);
            return presenter;
        });
        super.onCreate(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResource(), container, false);
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        if (getActivity() != null && getActivity() instanceof ErrorCallback && isAdded()) {
            ((ErrorCallback) getActivity()).showErrorMessage(errorMessage);
        }
    }

    protected abstract int getLayoutResource();
}
