package com.viettel.vpmt.mobiletv.screen.videodetail.fragment;

import com.viettel.vpmt.mobiletv.base.BasePresenter;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.network.dto.PartOfVideo;

import java.util.List;

/**
 * Created by ThanhTD on 3/29/2016.
 */
public interface ListPartVideoPresenter extends BasePresenter {
    void getData();
    void setData(List<Content> parts, Float videoId);
    void setPositionActive(int positionActive);
}
