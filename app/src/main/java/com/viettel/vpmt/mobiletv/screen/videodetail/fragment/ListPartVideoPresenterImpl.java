package com.viettel.vpmt.mobiletv.screen.videodetail.fragment;

import com.viettel.vpmt.mobiletv.base.BasePresenterImpl;
import com.viettel.vpmt.mobiletv.network.dto.PartOfVideo;
import com.viettel.vpmt.mobiletv.screen.videodetail.fragment.adapter.RecyclerViewPartVideoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThanhTD on 3/29/2016.
 */
public class ListPartVideoPresenterImpl extends BasePresenterImpl<ListPartVideoView> implements ListPartVideoPresenter {
    public ListPartVideoPresenterImpl(ListPartVideoView view) {
        super(view);
    }

    List<PartOfVideo> parts = new ArrayList<>();
    int positionActive = 0;
    float videoId;

    @Override
    public void getData() {
        RecyclerViewPartVideoAdapter recyclerViewPartVideoAdapter = new RecyclerViewPartVideoAdapter(videoId, parts, positionActive, mView.getViewContext());
        mView.loadDataToView(recyclerViewPartVideoAdapter);
    }

    @Override
    public void setData(List<PartOfVideo> parts, Float videoId) {
        this.parts = parts;
        this.videoId = videoId;
    }

    @Override
    public void setPositionActive(int positionActive) {
        this.positionActive = positionActive;
    }
}
