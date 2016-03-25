package com.viettel.vpmt.mobiletv.network;

import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.network.dto.ResponseDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Restful Services
 * Created by neo on 2/15/2016.
 */
public interface MobitvService {
    @GET("default/get-home")
    Call<ResponseDTO<List<Box>>> getHome();

}
