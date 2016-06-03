package com.viettel.vpmt.mobiletv.screen.videodetail.fragment.relate;

import com.viettel.vpmt.mobiletv.base.BaseView;
import com.viettel.vpmt.mobiletv.screen.common.adapter.VideoAdapter;

/**
 * Created by ThanhTD on 3/25/2016.
 */
public interface VideoRelativeView extends BaseView<VideoRelativePresenter> {
    void loadRelativeVideo(VideoAdapter videoRelativeAdapter);
}
