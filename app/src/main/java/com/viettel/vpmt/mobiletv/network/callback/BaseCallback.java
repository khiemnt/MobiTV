package com.viettel.vpmt.mobiletv.network.callback;

import com.viettel.vpmt.mobiletv.common.Constants;
import com.viettel.vpmt.mobiletv.network.dto.ResponseDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Base callback for all services
 * Created by neo on 2/15/2016.
 */
public abstract class BaseCallback<T> implements Callback<ResponseDTO<T>> {
    public static final String NETWORK_ERROR = "999";

    @Override
    public void onResponse(Call<ResponseDTO<T>> call, Response<ResponseDTO<T>> response) {
        // Get body of request
        ResponseDTO<T> body = null;
        String responseCode = NETWORK_ERROR;
        String message = "";

        if (response.isSuccess()) {
            body = response.body();
            responseCode = body.getResponseCode();
            message = body.getMessage();
        }

        if (body == null) {
            onError(NETWORK_ERROR, "Unknown error");
            return;
        }

        // If not success
        if (!Constants.CODE_SUCCESS.equals(responseCode)) {
            onError(responseCode, message);
            return;
        }

        // Request success
        onResponse(body.getResult());
    }

    @Override
    public void onFailure(Call<ResponseDTO<T>> call, Throwable t) {
        onError(NETWORK_ERROR, t.getMessage());
    }

    public abstract void onError(String errorCode, String errorMessage);

    public abstract void onResponse(T data);

//    public static <T> BaseCallback<T> newInstance(final BaseView view) {
//        return new BaseCallback<T>() {
//            @Override
//            public void onError(int errorCode, String errorMessage) {
//                view.onRequestError(errorCode, errorMessage);
//            }
//
//            @Override
//            public void onResponse(T data) {
//                view.onRequestSuccess();
//            }
//        };
//    }
}
