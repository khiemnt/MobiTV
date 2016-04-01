package com.viettel.vpmt.mobiletv.screen.film.fragment;

import com.google.android.exoplayer.util.Util;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.log.Logger;
import com.viettel.vpmt.mobiletv.common.util.StringUtils;
import com.viettel.vpmt.mobiletv.common.view.ExpandableTextView;
import com.viettel.vpmt.mobiletv.media.PlayerFragment;
import com.viettel.vpmt.mobiletv.network.dto.FilmDetail;
import com.viettel.vpmt.mobiletv.network.dto.VideoStream;
import com.viettel.vpmt.mobiletv.screen.film.activity.FilmDetailActivity;
import com.viettel.vpmt.mobiletv.screen.film.fragment.adapter.FilmFragmentPagerAdapter;
import com.viettel.vpmt.mobiletv.screen.film.fragment.adapter.FilmPartFragmentPagerAdapter;
import com.viettel.vpmt.mobiletv.screen.film.utils.WrapContentHeightViewPager;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.Bind;

/**
 * Created by ThanhTD on 3/26/2016.
 */
public class FilmDetailFragment extends PlayerFragment<FilmDetailFragmentPresenter, FilmDetailActivity> implements DetailFilmFragmentView {
    @Bind(R.id.common_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.view_transparent)
    View transparent;
    @Bind(R.id.fragment_film_detail_tvTitle)
    TextView tvTitle;
    @Bind(R.id.fragment_film_detail_tvShortDes)
    TextView tvShortDes;
    @Bind(R.id.fragment_film_detail_tvFullDes)
    ExpandableTextView tvFullDes;
    @Bind(R.id.fragment_film_detail_tvActor)
    TextView tvActor;
    @Bind(R.id.fragment_film_detail_tvCountry)
    TextView tvCountry;
    @Bind(R.id.viewpager)
    WrapContentHeightViewPager viewPager;
    @Bind(R.id.sliding_tabs)
    TabLayout tabLayout;
    @Bind(R.id.film_detail_thumb_up_down)
    CheckBox cbLike;
    @Bind(R.id.film_detail_number_of_view)
    CheckBox cbPlay;
    @Bind(R.id.mainScroll)
    ScrollView mScrollView;

    FragmentStatePagerAdapter adapter;
    private float filmId = 0;

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
        filmId = bundle.getFloat("filmId");
        Integer partOfFilm = bundle.getInt("part");
        getPresenter().getDetailVideo(0, filmId, partOfFilm);
    }

    @Override
    public FilmDetailFragmentPresenter onCreatePresenter() {
        return new FilmDetailFragmentPresenterImpl(this);
    }

    @Override
    public void doLoadToView(FilmDetail filmDetail, int positionPartActive) {
        String url = filmDetail.getStreams().getUrlStreaming();
        Logger.e("URL=\n" + url);
        if (!StringUtils.isEmpty(url)) {
            initPlayer(Uri.parse(url), Util.TYPE_HLS);
        } else {
            //todo request login later
            getPresenter().getVideoStream(filmId);
        }

        tvTitle.setText(filmDetail.getFilmDetail().getName());
        tvShortDes.setText(filmDetail.getFilmDetail().getShortDesc());
        if (!StringUtils.isEmpty(filmDetail.getFilmDetail().getDescription())) {
            tvFullDes.setTrim(true);
            tvFullDes.setText(filmDetail.getFilmDetail().getDescription(), TextView.BufferType.SPANNABLE);
        } else {
            tvFullDes.setVisibility(View.GONE);
        }
        cbLike.setChecked(filmDetail.getFilmDetail().isFavourite());
        cbLike.setText(filmDetail.getFilmDetail().getLikeCount() != null ? filmDetail.getFilmDetail().getLikeCount().toString() : "0");
        cbPlay.setText(filmDetail.getFilmDetail().getPlayCount() != null ? filmDetail.getFilmDetail().getPlayCount().toString() : "0");
        if (!StringUtils.isEmpty(filmDetail.getFilmDetail().getActors())) {
            tvActor.setText(getString(R.string.actor) + filmDetail.getFilmDetail().getActors());
        } else {
            tvActor.setVisibility(View.GONE);
        }
        if (!StringUtils.isEmpty(filmDetail.getFilmDetail().getCountry())) {
            tvCountry.setText(getString(R.string.country) + filmDetail.getFilmDetail().getCountry());
        } else {
            tvCountry.setVisibility(View.GONE);
        }
        if (filmDetail.getFilmRelated() != null) {
            if (filmDetail.getParts().size() > 0) {
                adapter = new FilmPartFragmentPagerAdapter(filmId, positionPartActive, filmDetail.getParts(), filmDetail.getFilmRelated().getContents(),
                        getActivity().getSupportFragmentManager(), getActivity());
            } else {
                adapter = new FilmFragmentPagerAdapter(filmDetail.getFilmRelated().getContents(), getActivity().getSupportFragmentManager(), getActivity());
            }
            viewPager.setAdapter(adapter);
        }
        tabLayout.setupWithViewPager(viewPager);
        mScrollView.fullScroll(ScrollView.FOCUS_UP);
        hideProgress();
    }

    @Override
    public void doLoadVideoStream(VideoStream videoStream) {
        initPlayer(Uri.parse(videoStream.getStreams()), Util.TYPE_OTHER);
    }
}
