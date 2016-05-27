package com.viettel.vpmt.mobiletv.screen.videodetail.fragment;

import com.viettel.vpmt.mobiletv.base.BasePresenter;

/**
 * Presenter for video detail fragment
 * Created by ThanhTD on 3/26/2016.
 */
public interface VideoDetailFragmentPresenter extends BasePresenter {
    void getVideoDetail(int position, String videoId, String partOfVideo);
    void getVideoStream(String videoId);
    void postLikeVideo(boolean isLike, String videoId);
}
