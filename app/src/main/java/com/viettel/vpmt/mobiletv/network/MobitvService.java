package com.viettel.vpmt.mobiletv.network;

import com.viettel.vpmt.mobiletv.network.dto.AuthenData;
import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.network.dto.FilmDetail;
import com.viettel.vpmt.mobiletv.network.dto.ResponseDTO;
import com.viettel.vpmt.mobiletv.network.dto.ResponseLikeUnlike;
import com.viettel.vpmt.mobiletv.network.dto.VideoDetail;
import com.viettel.vpmt.mobiletv.network.dto.VideoStream;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

    @GET("film/get-detail")
    Call<ResponseDTO<FilmDetail>> getDetailFilm(@Query("id") float filmId, @Query("part_id") Integer partOfFilm);

    @GET("film/get-video-stream")
    Call<ResponseDTO<VideoStream>> getFilmStream(@Header("Authorization") String authorization, @Query("id") float filmId);

    @GET("video/get-detail")
    Call<ResponseDTO<VideoDetail>> getDetailVideo(@Header("Authorization") String authorization, @Query("id") float videoId);

    @GET("video/get-video-stream")
    Call<ResponseDTO<VideoStream>> getVideoStream(@Header("Authorization") String authorization, @Query("id") float videoId);

    @POST("auth/authorize")
    Call<ResponseDTO<AuthenData>> authorize(@Query("grant_type") String grantType, @Query("msisdn") String msisdn);

    @POST("auth/authorize")
    Call<ResponseDTO<AuthenData>> autoLogin(@Query("grant_type") String grantType);


    @GET("{scope}/{path}")
    Call<ResponseDTO<List<Box>>> getHomeBox(@Path("scope") String scope,
                                            @Path("path") String path);

    @GET("default/get-more-content")
    Call<ResponseDTO<List<Box>>> getMoreContent(@Query("id") String id,
                                                @Query("offset") int offset,
                                                @Query("limit") int limit);

    @POST("film/toggle-like-film")
    Call<ResponseDTO<ResponseLikeUnlike>> postLikeFilm(@Header("Authorization") String authorization, @Query("id") float filmId);

    @POST("video/toggle-like-video")
    Call<ResponseDTO<ResponseLikeUnlike>> postLikeVideo(@Header("Authorization") String authorization, @Query("id") float filmId);
}
