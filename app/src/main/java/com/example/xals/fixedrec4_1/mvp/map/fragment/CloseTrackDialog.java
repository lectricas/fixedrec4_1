package com.example.xals.fixedrec4_1.mvp.map.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.xals.fixedrec4_1.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CloseTrackDialog extends DialogFragment {

    public static final String ERROR_FRAGMENT = "error_fragment";

    Unbinder unbinder;

    @BindView(R.id.comment)
    EditText message;

    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d!=null){
            d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.error_fragment_dialog, container, false);
        getDialog().setTitle("Simple Dialog");
        unbinder = ButterKnife.bind(this, rootView);
//        message.setText(error.getLocalizedMessage(getContext()));
        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.positive)
    public void onPositiveClick(View view) {
        dismiss();
//        callback.positive(error);
    }

    @OnClick(R.id.negative)
    public void onNegativeClick(View view) {
        dismiss();
//        callback.negative(error);
    }

//    public static void show(FragmentManager manager, ErrorCallback callback) {
//        CloseTrackDialog dialog = new CloseTrackDialog();
//        dialog.setCallback(callback);
//        dialog.show(manager, CloseTrackDialog.ERROR_FRAGMENT);
//    }
}
