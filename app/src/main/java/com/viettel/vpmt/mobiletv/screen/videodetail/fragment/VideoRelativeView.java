package com.viettel.vpmt.mobiletv.screen.videodetail.fragment;

import com.viettel.vpmt.mobiletv.base.BaseView;
import com.viettel.vpmt.mobiletv.screen.videodetail.fragment.adapter.RecyclerViewVideoAdapter;

/**
 * Created by ThanhTD on 3/25/2016.
 */
public interface VideoRelativeView extends BaseView<VideoRelativePresenter> {
    void loadRelativeVideo(RecyclerViewVideoAdapter recyclerViewVideoAdapter);
}
