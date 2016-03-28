package com.viettel.vpmt.mobiletv.network.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ThanhTD on 3/28/2016.
 */
public class FilmDetail
{
    @SerializedName("film_detail")
    private Content filmDetail;
    @SerializedName("streams")
    private Streams streams;
    @SerializedName("parts")
    private List<PartOfFilm> parts;
    @SerializedName("film_related")
    FilmRelated filmRelated;

    public Content getFilmDetail()
    {
        return filmDetail;
    }

    public void setFilmDetail(Content filmDetail)
    {
        this.filmDetail = filmDetail;
    }

    public Streams getStreams()
    {
        return streams;
    }

    public void setStreams(Streams streams)
    {
        this.streams = streams;
    }

    public List<PartOfFilm> getParts()
    {
        return parts;
    }

    public void setParts(List<PartOfFilm> parts)
    {
        this.parts = parts;
    }

    public FilmRelated getFilmRelated()
    {
        return filmRelated;
    }

    public void setFilmRelated(FilmRelated filmRelated)
    {
        this.filmRelated = filmRelated;
    }
}
