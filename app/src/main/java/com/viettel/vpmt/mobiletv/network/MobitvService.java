package com.viettel.vpmt.mobiletv.network;

import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.network.dto.FilmDetail;
import com.viettel.vpmt.mobiletv.network.dto.ResponseDTO;
import com.viettel.vpmt.mobiletv.network.dto.VideoDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Restful Services
 * Created by neo on 2/15/2016.
 */
public interface MobitvService {
    @GET("default/get-home")
    Call<ResponseDTO<List<Box>>> getHome();

    @GET("video/get-detail")
    Call<ResponseDTO<VideoDetail>> getDetailVideo(@Query("id") float videoId);

    @GET("film/get-detail")
    Call<ResponseDTO<FilmDetail>> getDetailFilm(@Query("id") float filmId, @Query("part_id") Float partOfFilm);
}
