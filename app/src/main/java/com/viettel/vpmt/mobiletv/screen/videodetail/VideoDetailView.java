package com.viettel.vpmt.mobiletv.screen.videodetail;

import com.viettel.vpmt.mobiletv.base.BaseView;

/**
 * Created by ThanhTD on 3/22/2016.
 */
public interface VideoDetailView extends BaseView<VideoDetailPresenter>
{
    void doLoadVideo(String url);
}
