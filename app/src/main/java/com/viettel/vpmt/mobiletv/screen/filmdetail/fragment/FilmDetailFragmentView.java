package com.viettel.vpmt.mobiletv.screen.filmdetail.fragment;

import com.viettel.vpmt.mobiletv.base.BaseView;
import com.viettel.vpmt.mobiletv.network.dto.DataStream;
import com.viettel.vpmt.mobiletv.network.dto.FilmDetail;

/**
 * Film Detail Fragment View
 * Created by ThanhTD on 3/26/2016.
 */
public interface FilmDetailFragmentView extends BaseView<FilmDetailFragmentPresenter> {
    void doLoadToView(FilmDetail filmDetail, int positionPartActive, int tabIndex);
    void doLoadVideoStream(DataStream videoStream);
    void doRefreshLike(boolean isLike);
}
