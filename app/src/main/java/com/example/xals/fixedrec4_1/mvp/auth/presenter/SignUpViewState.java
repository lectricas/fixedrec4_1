package com.example.xals.fixedrec4_1.mvp.auth.presenter;


import com.example.xals.fixedrec4_1.business.model.UserModel;
import com.example.xals.fixedrec4_1.mvp.base.presenter.BaseViewState;

public interface SignUpViewState extends BaseViewState {

    void onRegistered(UserModel model);
}
