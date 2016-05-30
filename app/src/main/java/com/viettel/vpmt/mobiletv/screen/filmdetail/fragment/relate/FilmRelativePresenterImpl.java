package com.viettel.vpmt.mobiletv.screen.filmdetail.fragment.relate;

import com.viettel.vpmt.mobiletv.base.BasePresenterImpl;
import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.screen.home.adapter.FilmAdapter;

import java.util.List;

/**
 * Related Film presenter
 * Created by ThanhTD on 3/25/2016.
 */
public class FilmRelativePresenterImpl extends BasePresenterImpl<FilmRelativeView> implements FilmRelativePresenter {
    private List<Content> mContents;

    public FilmRelativePresenterImpl(FilmRelativeView view) {
        super(view);
    }

    @Override
    public void setData(List<Content> videos) {
        this.mContents = videos;
    }

    @Override
    public void getData() {
//        List<ImageItem> imageItems = new ArrayList<>();
//        for (Content content : mContents) {
//            imageItems.add(new ImageItem(content.getId(), content.getAvatarImage(), content.getDescription(), content.getName()));
//        }
//        FilmRelativeAdapter filmRelativeAdapter = new FilmRelativeAdapter(imageItems, mView.getViewContext());
        FilmAdapter filmAdapter = new FilmAdapter(mView.getViewContext(), mContents, 0);
        mView.loadRelativeVideo(filmAdapter);
    }
}
