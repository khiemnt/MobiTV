package com.viettel.vpmt.mobiletv.network;

import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.network.dto.FilmDetail;
import com.viettel.vpmt.mobiletv.network.dto.ResponseDTO;
import com.viettel.vpmt.mobiletv.network.dto.VideoDetail;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

/**
 * Restful Services
 * Created by neo on 2/15/2016.
 */
public interface MobitvService {
    @GET("default/get-home")
    Call<ResponseDTO<List<Box>>> getHome();

    @GET("film/get-detail")
    Call<ResponseDTO<FilmDetail>> getDetailFilm(@Query("id") float filmId, @Query("part_id") Float partOfFilm);

    @GET("video/get-detail")
    Call<ResponseDTO<VideoDetail>> getDetailVideo(@Query("id") float videoId);
    @POST("auth/authorize")
    Call login();

    @GET("{scope}/{path}")
    Call<ResponseDTO<List<Box>>> getHomeBox(@Path("scope") String scope,
                                            @Path("path") String path);

    @GET("default/get-more-content")
    Call<ResponseDTO<List<Box>>> getMoreContent(@Query("id") String id,
                                                    @Query("offset") int offset,
                                                    @Query("limit") int limit);
}
