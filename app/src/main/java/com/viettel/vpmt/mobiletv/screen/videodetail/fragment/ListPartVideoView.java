package com.viettel.vpmt.mobiletv.screen.videodetail.fragment;

import com.viettel.vpmt.mobiletv.base.BaseView;
import com.viettel.vpmt.mobiletv.screen.videodetail.fragment.adapter.RecyclerViewPartVideoAdapter;

/**
 * Created by ThanhTD on 3/29/2016.
 */
public interface ListPartVideoView extends BaseView<ListPartVideoPresenter> {
    void loadDataToView(RecyclerViewPartVideoAdapter viewPartVideoAdapter);
}
