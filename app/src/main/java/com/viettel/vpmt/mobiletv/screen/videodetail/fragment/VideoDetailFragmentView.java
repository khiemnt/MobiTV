package com.viettel.vpmt.mobiletv.screen.videodetail.fragment;

import com.viettel.vpmt.mobiletv.base.BaseView;
import com.viettel.vpmt.mobiletv.network.dto.DataStream;
import com.viettel.vpmt.mobiletv.network.dto.VideoDetail;
import com.viettel.vpmt.mobiletv.network.dto.VideoStream;

/**
 * Created by ThanhTD on 3/26/2016.
 */
public interface VideoDetailFragmentView extends BaseView<VideoDetailFragmentPresenter> {
    void doLoadToView(VideoDetail videoDetail, int positionActive);
    void doLoadVideoStream(DataStream videoStream);
    void doRefreshLike(boolean isLike);
}
