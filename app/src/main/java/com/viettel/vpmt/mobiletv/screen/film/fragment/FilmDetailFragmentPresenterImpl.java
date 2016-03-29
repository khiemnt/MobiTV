package com.viettel.vpmt.mobiletv.screen.film.fragment;

import com.viettel.vpmt.mobiletv.base.BasePresenterImpl;
import com.viettel.vpmt.mobiletv.common.util.NetworkUtils;
import com.viettel.vpmt.mobiletv.network.ServiceBuilder;
import com.viettel.vpmt.mobiletv.network.callback.BaseCallback;
import com.viettel.vpmt.mobiletv.network.dto.FilmDetail;

/**
 * Created by ThanhTD on 3/26/2016.
 */
public class FilmDetailFragmentPresenterImpl extends BasePresenterImpl<DetailFilmFragmentView> implements FilmDetailFragmentPresenter {
    public FilmDetailFragmentPresenterImpl(DetailFilmFragmentView view) {
        super(view);
    }

    @Override
    public void getDetailVideo(final int position, float videoId, Float partOfFilm) {
        if (!NetworkUtils.checkNetwork(mView.getViewContext())) {
            return;
        }
        mView.showProgress();
        ServiceBuilder.getService().getDetailFilm(videoId, partOfFilm).enqueue(new BaseCallback<FilmDetail>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                mView.onRequestError(errorCode, errorMessage);
            }

            @Override
            public void onResponse(FilmDetail data) {
                mView.doLoadToView(data, position);
            }
        });
    }
}
