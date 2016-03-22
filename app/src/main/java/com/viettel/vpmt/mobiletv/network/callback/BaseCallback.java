package com.viettel.vpmt.mobiletv.network.callback;

import com.viettel.vpmt.mobiletv.common.Constants;
import com.viettel.vpmt.mobiletv.network.dto.ResponseDTO;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Base callback for all services
 * Created by neo on 2/15/2016.
 */
public abstract class BaseCallback<T> implements Callback<ResponseDTO<T>> {
    public static final int NETWORK_ERROR = 999;

    @Override
    public void onResponse(Call<ResponseDTO<T>> call, Response<ResponseDTO<T>> response) {
        // Get body of request
        ResponseDTO<T> body = null;
        int errorCode = NETWORK_ERROR;
        String message = "";

        if (response.isSuccess()) {
            body = response.body();
            errorCode = body.getErrorCode();
            message = body.getMessage();
        } else {
            try {
                body = new Gson().fromJson(response.errorBody().string(), ResponseDTO.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (body == null) {
            onError(NETWORK_ERROR, "Unknown error");
        }

        // If not success
        if (errorCode != Constants.CODE_SUCCESS) {
            onError(errorCode, message);
            return;
        }

        // Request success
        onResponse(body.getResult());
    }

    @Override
    public void onFailure(Call<ResponseDTO<T>> call, Throwable t) {
        onError(NETWORK_ERROR, t.getMessage());
    }

    public abstract void onError(int errorCode, String errorMessage);
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
