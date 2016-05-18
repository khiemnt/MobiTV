package com.viettel.vpmt.mobiletv.network.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ThanhTD on 3/28/2016.
 */
public class FilmDetail {
    @SerializedName("film_detail")
    private Content filmDetail;
    @SerializedName("streams")
    private Streams streams;
    @SerializedName("parts")
    private List<PartOfFilm> parts;
    @SerializedName("film_related")
    ContentRelated mContentRelated;

    public Content getFilmDetail() {
        return filmDetail;
    }

    public void setFilmDetail(Content filmDetail) {
        this.filmDetail = filmDetail;
    }

    public Streams getStreams() {
        return streams;
    }

    public void setStreams(Streams streams) {
        this.streams = streams;
    }

    public List<PartOfFilm> getParts() {
        return parts;
    }

    public void setParts(List<PartOfFilm> parts) {
        this.parts = parts;
    }

    public ContentRelated getContentRelated() {
        return mContentRelated;
    }

    public void setContentRelated(ContentRelated contentRelated) {
        this.mContentRelated = contentRelated;
    }
}
