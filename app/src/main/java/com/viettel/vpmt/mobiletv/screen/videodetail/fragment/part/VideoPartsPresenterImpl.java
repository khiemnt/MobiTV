package com.viettel.vpmt.mobiletv.screen.videodetail.fragment.part;

import com.viettel.vpmt.mobiletv.base.BasePresenterImpl;
import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.network.dto.PartOfVideo;

import java.util.ArrayList;
import java.util.List;

/**
 * Presenter for list of video parts
 * Created by ThanhTD on 3/29/2016.
 */
public class VideoPartsPresenterImpl extends BasePresenterImpl<VideoPartsView> implements VideoPartsPresenter {
    public VideoPartsPresenterImpl(VideoPartsView view) {
        super(view);
    }

    private List<Content> mVideos = new ArrayList<>();
    private int mPositionActive = 0;
    private String mVideoId;

    @Override
    public void getData() {
        List<PartOfVideo> partOfVideos = new ArrayList<>();
        for (Content content : mVideos) {
            partOfVideos.add(new PartOfVideo(content.getId(), content.getName(), content.getAvatarImage()));
        }
        VideoPartsAdapter videoPartsAdapter
                = new VideoPartsAdapter(mVideoId, partOfVideos, mPositionActive, mView.getViewContext(), Box.calculateItemSize(mView.getViewContext(), Box.Type.VOD));
        mView.loadDataToView(videoPartsAdapter);
    }

    @Override
    public void setData(List<Content> parts, String videoId) {
        mVideos = parts;
        mVideoId = videoId;
    }

    @Override
    public void setPositionActive(int positionActive) {
        mPositionActive = positionActive;
    }
}
