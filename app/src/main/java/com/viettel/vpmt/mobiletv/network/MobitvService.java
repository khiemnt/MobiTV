package com.viettel.vpmt.mobiletv.network;

import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.network.dto.ResponseDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Restful Services
 * Created by neo on 2/15/2016.
 */
public interface MobitvService {
    @GET("default/get-home")
    Call<ResponseDTO<List<Box>>> getHome();

    @POST("auth/authorize")
    Call login();

    @GET("{scope}/{path}")
    Call<ResponseDTO<List<Box>>> getHomeBox(@Path("scope") String scope,
                                            @Path("path") String path,
                                            @Query("id") String id);

    @GET("default/get-more-content")
    Call<ResponseDTO<List<Box>>> getMoreContent(@Query("id") String id,
                                                    @Query("offset") int offset,
                                                    @Query("limit") int limit);
}
