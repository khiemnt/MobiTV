package com.viettel.vpmt.mobiletv.screen.videodetail.fragment.part;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseFragment;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.screen.videodetail.activity.VideoDetailActivity;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * List of video parts fragment
 * Created by ThanhTD on 3/29/2016.
 */
public class VideoPartsFragment extends BaseFragment<VideoPartsPresenter, VideoDetailActivity> implements VideoPartsView {

    @Bind(R.id.list_recyclerView)
    RecyclerView mRecyclerView;

    private List<Content> parts = new ArrayList<>();
    private int positionActive = 0;
    private String mFilmId = "";

    public static VideoPartsFragment newInstance(List<Content> parts, String filmId, int positionActive) {
        VideoPartsFragment listPartFilmFragment = new VideoPartsFragment();
        listPartFilmFragment.setParts(parts);
        listPartFilmFragment.setPositionActive(positionActive);
        listPartFilmFragment.setFilmId(filmId);
        return listPartFilmFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_recycler_view;
    }

    @Override
    public void loadDataToView(VideoPartsAdapter viewPartVideoAdapter) {
        mRecyclerView.setAdapter(viewPartVideoAdapter);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onPrepareLayout() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        getPresenter().setData(parts, mFilmId);
        getPresenter().setPositionActive(positionActive);
        getPresenter().getData();
    }

    @Override
    public VideoPartsPresenter onCreatePresenter() {
        return new VideoPartsPresenterImpl(this);
    }

    public void setParts(List<Content> parts) {
        this.parts = parts;
    }

    public void setPositionActive(int positionActive) {
        this.positionActive = positionActive;
    }

    public void setFilmId(String filmId) {
        this.mFilmId = filmId;
    }
}
