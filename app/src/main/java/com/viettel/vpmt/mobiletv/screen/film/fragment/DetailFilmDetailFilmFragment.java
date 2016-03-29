package com.viettel.vpmt.mobiletv.screen.film.fragment;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseFragment;
import com.viettel.vpmt.mobiletv.common.util.CustomTextViewExpandable;
import com.viettel.vpmt.mobiletv.network.dto.FilmDetail;
import com.viettel.vpmt.mobiletv.screen.film.activity.DetailFilmFilmActivity;
import com.viettel.vpmt.mobiletv.screen.film.fragment.adapter.FilmFragmentPagerAdapter;
import com.viettel.vpmt.mobiletv.screen.film.utils.WrapContentHeightViewPager;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import butterknife.Bind;

/**
 * Created by ThanhTD on 3/26/2016.
 */
public class DetailFilmDetailFilmFragment extends BaseFragment<DetailFilmFragmentPresenter, DetailFilmFilmActivity> implements DetailFilmFragmentView {
    @Bind(R.id.common_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.view_transparent)
    View transparent;
    @Bind(R.id.film_detail_layout_video)
    VideoView videoView;
    @Bind(R.id.fragment_film_detail_tvTitle)
    TextView tvTitle;
    @Bind(R.id.fragment_film_detail_tvShortDes)
    TextView tvShortDes;
    @Bind(R.id.fragment_film_detail_tvFullDes)
    TextView tvFullDes;
    @Bind(R.id.fragment_film_detail_tvActor)
    TextView tvActor;
    @Bind(R.id.fragment_film_detail_tvCountry)
    TextView tvCountry;
    @Bind(R.id.viewpager)
    WrapContentHeightViewPager viewPager;
    @Bind(R.id.sliding_tabs)
    TabLayout tabLayout;
    FilmFragmentPagerAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_film_detail;
    }

    @Override
    public void showProgress() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
            transparent.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
            transparent.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPrepareLayout() {
        Bundle bundle = getArguments();
        float videoId = bundle.getFloat("filmId");
        float partOfFilm = bundle.getFloat("part");
        getPresenter().getDetailVideo(videoId, partOfFilm);
        CustomTextViewExpandable.makeTextViewResizable(tvFullDes, 3, getString(R.string.view_more), false);
    }

    @Override
    public DetailFilmFragmentPresenter onCreatePresenter() {
        return new DetailFilmFragmentPresenterImpl(this);
    }

    @Override
    public void doLoadToView(FilmDetail filmDetail) {
        videoView.setVideoURI(Uri.parse(filmDetail.getStreams().getUrlStreaming()));
        MediaController mediaController = new MediaController(getActivity());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse("https://ia700401.us.archive.org/19/items/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4"));
        videoView.start();
        tvTitle.setText(filmDetail.getFilmDetail().getName());
        tvShortDes.setText(filmDetail.getFilmDetail().getShortDescription());
        if (filmDetail.getFilmDetail().getDescription() != null) {
            tvFullDes.setText(filmDetail.getFilmDetail().getDescription());
        } else {
            tvFullDes.setVisibility(View.GONE);
        }
        if (filmDetail.getFilmDetail().getActors() != null) {
            tvActor.setText(getString(R.string.actor) + filmDetail.getFilmDetail().getActors());
        } else {
            tvActor.setVisibility(View.GONE);
        }
        if (filmDetail.getFilmDetail().getCountry() != null) {
            tvCountry.setText(getString(R.string.country) + filmDetail.getFilmDetail().getCountry());
        } else {
            tvCountry.setVisibility(View.GONE);
        }
        if (filmDetail.getFilmRelated() != null) {
            adapter = new FilmFragmentPagerAdapter(filmDetail.getFilmRelated().getContents(), getActivity().getSupportFragmentManager(), getActivity());
            viewPager.setAdapter(adapter);
        }
        tabLayout.setupWithViewPager(viewPager);
        hideProgress();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
