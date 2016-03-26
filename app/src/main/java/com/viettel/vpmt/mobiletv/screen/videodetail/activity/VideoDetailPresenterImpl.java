package com.viettel.vpmt.mobiletv.screen.videodetail.activity;

import com.viettel.vpmt.mobiletv.base.BasePresenterImpl;
import com.viettel.vpmt.mobiletv.common.util.NetworkUtils;
import com.viettel.vpmt.mobiletv.network.ServiceBuilder;
import com.viettel.vpmt.mobiletv.network.callback.BaseCallback;
import com.viettel.vpmt.mobiletv.network.dto.VideoDetail;

/**
 * Created by ThanhTD on 3/22/2016.
 */
public class VideoDetailPresenterImpl extends BasePresenterImpl<VideoDetailView> implements VideoDetailPresenter
{
    public VideoDetailPresenterImpl(VideoDetailView view)
    {
        super(view);
    }
}
