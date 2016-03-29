package com.viettel.vpmt.mobiletv.screen.videodetail.fragment;

import com.viettel.vpmt.mobiletv.base.BasePresenter;

/**
 * Created by ThanhTD on 3/26/2016.
 */
public interface VideoDetailFragmentPresenter extends BasePresenter {
    void getDetailVideo(int position, float videoId, Float partOfVideo);
}
