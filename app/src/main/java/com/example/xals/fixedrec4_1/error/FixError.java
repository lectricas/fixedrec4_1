package com.example.xals.fixedrec4_1.error;


import android.text.TextUtils;

import retrofit2.adapter.rxjava.HttpException;

public class FixError extends Throwable {

    public static final String SOCKET_EXCEPTION = "java.net.SocketTimeoutException";

    public FixError(Throwable cause) {
        super(cause);
    }

    public String getCustomMessage() {
        String message = "";
        if (getCause() instanceof HttpException) {
            message = ((HttpException) getCause()).message();
        } else if (TextUtils.isEmpty(getCause().getMessage())) {
            message = this.toString();
        } else {
            message = getCause().getMessage();
        }
        return message;
    }
}
