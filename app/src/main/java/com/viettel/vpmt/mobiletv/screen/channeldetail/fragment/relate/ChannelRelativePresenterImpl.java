package com.viettel.vpmt.mobiletv.screen.channeldetail.fragment.relate;

import com.viettel.vpmt.mobiletv.base.BasePresenterImpl;
import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.screen.home.adapter.ChannelAdapter;

import java.util.List;

/**
 * Presenter for channel relative
 * Created by ThanhTD on 3/25/2016.
 */
public class ChannelRelativePresenterImpl extends BasePresenterImpl<ChannelRelativeView> implements ChannelRelativePresenter {
    private List<Content> mContents;

    public ChannelRelativePresenterImpl(ChannelRelativeView view) {
        super(view);
    }

    @Override
    public void setData(List<Content> videos) {
        this.mContents = videos;
    }

    @Override
    public void getData() {
        ChannelAdapter adapter = new ChannelAdapter(mView.getViewContext(), mContents, 0);
        mView.loadRelativeChannel(adapter);
    }
}
