package com.viettel.vpmt.mobiletv.network;

import com.viettel.vpmt.mobiletv.network.dto.AuthenData;
import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.network.dto.ChannelDetail;
import com.viettel.vpmt.mobiletv.network.dto.DataStream;
import com.viettel.vpmt.mobiletv.network.dto.FilmDetail;
import com.viettel.vpmt.mobiletv.network.dto.ResponseDTO;
import com.viettel.vpmt.mobiletv.network.dto.ResponseLikeUnlike;
import com.viettel.vpmt.mobiletv.network.dto.ScheduleData;
import com.viettel.vpmt.mobiletv.network.dto.PlayerSetting;
import com.viettel.vpmt.mobiletv.network.dto.VideoDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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
    Call<ResponseDTO<FilmDetail>> getDetailFilm(@Query("id") String filmId, @Query("part_id") String partOfFilm);

    @GET("channel/get-detail")
    Call<ResponseDTO<ChannelDetail>> getDetailChannel(@Header("Authorization") String authorization, @Query("id") String channelId);

    @GET("film/get-video-stream")
    Call<ResponseDTO<DataStream>> getFilmStream(@Header("Authorization") String authorization, @Query("id") String filmId);

    @GET("channel/get-channel-stream")
    Call<ResponseDTO<DataStream>> getChannelStream(@Header("Authorization") String authorization, @Query("id") String channelId);

    @GET("channel/get-program-stream")
    Call<ResponseDTO<DataStream>> getProgramStream(@Header("Authorization") String authorization, @Query("id") String programId);

    @GET("video/get-detail")
    Call<ResponseDTO<VideoDetail>> getDetailVideo(@Header("Authorization") String authorization, @Query("id") String videoId);

    @GET("video/get-video-stream")
    Call<ResponseDTO<DataStream>> getVideoStream(@Header("Authorization") String authorization, @Query("id") String videoId);

    @POST("auth/authorize")
    Call<ResponseDTO<AuthenData>> authorize(@Query("grant_type") String grantType, @Query("msisdn") String msisdn, @Query("refresh_token") String refreshToken);

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
    Call<ResponseDTO<ResponseLikeUnlike>> postLikeFilm(@Header("Authorization") String authorization, @Query("id") String filmId);

    @POST("video/toggle-like-video")
    Call<ResponseDTO<ResponseLikeUnlike>> postLikeVideo(@Header("Authorization") String authorization, @Query("id") String videoId);

    @POST("channel/toggle-like-channel")
    Call<ResponseDTO<ResponseLikeUnlike>> postLikeChannel(@Header("Authorization") String authorization, @Query("id") String channelId);

    @POST("channel/notify-channel")
    Call<ResponseDTO<String>> notifyChannel(@Header("Authorization") String authorization, @Query("id") String channelId);

    @POST("channel/get-channel-program")
    Call<ResponseDTO<ScheduleData>> getChannelProgram(@Header("Authorization") String authorization, @Query("id") String channelId, @Query("date") String date);

    @POST("default/feed-back")
    @FormUrlEncoded
    Call<ResponseDTO<ScheduleData>> sendFeedback(@Header("Authorization") String authorization, @Field("id") String id, @Field("content") String content);

    @GET("default/get-setting")
    Call<ResponseDTO<PlayerSetting>> getSettings(@Header("Authorization") String authorization);

    @POST("default/search")
    Call<ResponseDTO<List<Box>>> search(@Header("Authorization") String authorization, @Query("query") String query);
}
