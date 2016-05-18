package com.viettel.vpmt.mobiletv.screen.videodetail.fragment.part;

import com.viettel.vpmt.mobiletv.base.BaseView;

/**
 * Created by ThanhTD on 3/29/2016.
 */
public interface VideoPartsView extends BaseView<VideoPartsPresenter> {
    void loadDataToView(VideoPartsAdapter viewPartVideoAdapter);
}
