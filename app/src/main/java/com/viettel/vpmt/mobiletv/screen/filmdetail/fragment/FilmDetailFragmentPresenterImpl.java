package com.viettel.vpmt.mobiletv.screen.filmdetail.fragment;

import com.viettel.vpmt.mobiletv.base.BasePresenterImpl;
import com.viettel.vpmt.mobiletv.common.pref.PrefManager;
import com.viettel.vpmt.mobiletv.common.util.NetworkUtils;
import com.viettel.vpmt.mobiletv.network.ServiceBuilder;
import com.viettel.vpmt.mobiletv.network.callback.BaseCallback;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.network.dto.DataStream;
import com.viettel.vpmt.mobiletv.network.dto.FilmDetail;
import com.viettel.vpmt.mobiletv.network.dto.ResponseLikeUnlike;
import com.viettel.vpmt.mobiletv.network.dto.VideoStream;

/**
 * Presenter for film detail fragment
 * Created by ThanhTD on 3/26/2016.
 */
public class FilmDetailFragmentPresenterImpl extends BasePresenterImpl<FilmDetailFragmentView> implements FilmDetailFragmentPresenter {
    private FilmDetail mFilmDetail;

    public FilmDetailFragmentPresenterImpl(FilmDetailFragmentView view) {
        super(view);
    }

    @Override
    public void getFilmDetail(final int position, String filmId, String partOfFilm, final int tabIndex) {
        if (!NetworkUtils.checkNetwork(mView.getViewContext())) {
            return;
        }
        mView.showProgress();
        ServiceBuilder.getService().getDetailFilm(filmId, partOfFilm).enqueue(new BaseCallback<FilmDetail>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                mView.onRequestError(errorCode, errorMessage);
            }

            @Override
            public void onResponse(FilmDetail data) {
                mView.doLoadToView(data, position, tabIndex);
                mFilmDetail = data;
            }
        });
    }

    @Override
    public void getFilmStream(String filmId) {
        if (!NetworkUtils.checkNetwork(mView.getViewContext())) {
            return;
        }
        String header = PrefManager.getHeader(mView.getViewContext());
        ServiceBuilder.getService().getFilmStream(header, filmId).enqueue(new BaseCallback<DataStream>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                mView.onRequestError(errorCode, errorMessage);
            }

            @Override
            public void onResponse(DataStream data) {
                mView.doLoadVideoStream(data);
            }
        });
    }

    @Override
    public void postLikeFilm(String filmId) {
        if (!NetworkUtils.checkNetwork(mView.getViewContext())) {
            return;
        }

        String header = PrefManager.getHeader(mView.getViewContext());
        ServiceBuilder.getService().postLikeFilm(header, filmId).enqueue(new BaseCallback<ResponseLikeUnlike>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                mView.onRequestError(errorCode, errorMessage);
            }

            @Override
            public void onResponse(ResponseLikeUnlike data) {
                mView.doRefreshLike(data.isLike());
            }
        });
    }

    @Override
    public FilmDetail getFilmDetail() {
        return mFilmDetail;
    }
}
