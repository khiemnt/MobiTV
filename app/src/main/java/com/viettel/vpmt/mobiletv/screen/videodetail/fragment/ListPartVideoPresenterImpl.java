package com.viettel.vpmt.mobiletv.screen.videodetail.fragment;

import com.viettel.vpmt.mobiletv.base.BasePresenterImpl;
import com.viettel.vpmt.mobiletv.network.dto.Content;
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

    List<Content> videos = new ArrayList<>();
    int positionActive = 0;
    float videoId;

    @Override
    public void getData() {
        List<PartOfVideo> partOfVideos = new ArrayList<>();
        for (Content content : videos) {
            partOfVideos.add(new PartOfVideo(Float.valueOf(content.getId()), content.getName(), content.getAvatarImage()));
        }
        RecyclerViewPartVideoAdapter recyclerViewPartVideoAdapter = new RecyclerViewPartVideoAdapter(videoId, partOfVideos, positionActive, mView.getViewContext());
        mView.loadDataToView(recyclerViewPartVideoAdapter);
    }

    @Override
    public void setData(List<Content> parts, Float videoId) {
        this.videos = parts;
        this.videoId = videoId;
    }

    @Override
    public void setPositionActive(int positionActive) {
        this.positionActive = positionActive;
    }
}
