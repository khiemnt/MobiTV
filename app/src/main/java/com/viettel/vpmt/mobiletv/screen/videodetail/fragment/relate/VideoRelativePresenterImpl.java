package com.viettel.vpmt.mobiletv.screen.videodetail.fragment.relate;

import com.viettel.vpmt.mobiletv.base.BasePresenterImpl;
import com.viettel.vpmt.mobiletv.common.util.CompatibilityUtil;
import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.screen.common.adapter.VideoAdapter;
import com.viettel.vpmt.mobiletv.screen.videodetail.fragment.item.ImageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThanhTD on 3/25/2016.
 */
public class VideoRelativePresenterImpl extends BasePresenterImpl<VideoRelativeView> implements VideoRelativePresenter {
    private List<Content> videos;

    public VideoRelativePresenterImpl(VideoRelativeView view) {
        super(view);
    }

    @Override
    public void setData(List<Content> videos) {
        this.videos = videos;
    }

    @Override
    public void getData() {
        List<ImageItem> imageItems = new ArrayList<>();
        for (Content content : videos) {
            imageItems.add(new ImageItem(content.getId(), content.getAvatarImage(), content.getDescription(), content.getName()));
        }
//        VideoRelativeAdapter videoRelativeAdapter = new VideoRelativeAdapter(imageItems, mView.getViewContext());
        int itemWidth = CompatibilityUtil.getWidthItemNoSpacing(mView.getViewContext(), Box.Type.VOD);
        VideoAdapter videoRelativeAdapter = new VideoAdapter(mView.getViewContext(), videos, itemWidth);
        mView.loadRelativeVideo(videoRelativeAdapter);
    }
}
