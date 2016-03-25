package com.viettel.vpmt.mobiletv.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Web services builder
 * Created by neo on 2/15/2016.
 */
public class ServiceBuilder {
    public static final String BASE_URL = "http://125.235.242.166/api_yii2.php/v1/";

    public static final int TOKEN_EXPIRED = 401;

    private static Retrofit sInstance;
    private static MobitvService sService;

    private static Retrofit getRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        if (sInstance == null) {
            sInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return sInstance;
    }

    public static MobitvService getService() {
        if (sService == null) {
            sService = getRetrofit().create(MobitvService.class);
        }

        return sService;
    }
}
