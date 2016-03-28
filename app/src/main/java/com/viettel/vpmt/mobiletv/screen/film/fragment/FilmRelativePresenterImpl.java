package com.viettel.vpmt.mobiletv.screen.film.fragment;

import com.viettel.vpmt.mobiletv.base.BasePresenterImpl;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.screen.film.fragment.adapter.RecyclerViewFilmAdapter;
import com.viettel.vpmt.mobiletv.screen.film.fragment.adapter.item.ImageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThanhTD on 3/25/2016.
 */
public class FilmRelativePresenterImpl extends BasePresenterImpl<FilmRelativeView> implements FilmRelativePresenter
{
    private List<Content> videos;

    public FilmRelativePresenterImpl(FilmRelativeView view)
    {
        super(view);
    }

    @Override
    public void setData(List<Content> videos)
    {
        this.videos = videos;
    }

    @Override
    public void getData()
    {
        List<ImageItem> imageItems = new ArrayList<>();
        for (Content content : videos)
        {
            imageItems.add(new ImageItem(content.getId(), content.getAvatarImage(), content.getDescription()));
        }
        RecyclerViewFilmAdapter recyclerViewFilmAdapter = new RecyclerViewFilmAdapter(imageItems, mView.getViewContext());
        mView.loadRelativeVideo(recyclerViewFilmAdapter);
    }
}
