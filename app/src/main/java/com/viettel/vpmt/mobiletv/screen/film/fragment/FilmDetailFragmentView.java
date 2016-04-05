package com.viettel.vpmt.mobiletv.screen.film.fragment;

import com.viettel.vpmt.mobiletv.base.BaseView;
import com.viettel.vpmt.mobiletv.network.dto.FilmDetail;
import com.viettel.vpmt.mobiletv.network.dto.VideoStream;

/**
 * Created by ThanhTD on 3/26/2016.
 */
public interface FilmDetailFragmentView extends BaseView<FilmDetailFragmentPresenter> {
    void doLoadToView(FilmDetail filmDetail, int positionPartActive, int tabIndex);
    void doLoadVideoStream(VideoStream videoStream);
    void doRefreshLike(boolean isLike);
}
