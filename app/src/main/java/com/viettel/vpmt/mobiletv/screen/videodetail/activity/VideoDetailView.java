package com.viettel.vpmt.mobiletv.screen.videodetail.activity;

import com.viettel.vpmt.mobiletv.base.BaseView;
import com.viettel.vpmt.mobiletv.network.dto.VideoDetail;

/**
 * Created by ThanhTD on 3/22/2016.
 */
public interface VideoDetailView extends BaseView<VideoDetailPresenter>
{
    void doLoadToView(VideoDetail videoDetail);
}
