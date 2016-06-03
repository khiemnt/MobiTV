package com.viettel.vpmt.mobiletv.screen.videodetail.fragment.part;

import com.viettel.vpmt.mobiletv.base.BasePresenter;
import com.viettel.vpmt.mobiletv.network.dto.Content;

import java.util.List;

/**
 * Presenter for list of video parts
 * Created by ThanhTD on 3/29/2016.
 */
public interface VideoPartsPresenter extends BasePresenter {
    void getData();
    void setData(List<Content> parts, String videoId);
    void setPositionActive(int positionActive);
}
