package com.viettel.vpmt.mobiletv.screen.filmdetail.fragment;

import com.google.android.exoplayer.util.Util;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.log.Logger;
import com.viettel.vpmt.mobiletv.common.Constants;
import com.viettel.vpmt.mobiletv.common.util.StringUtils;
import com.viettel.vpmt.mobiletv.common.view.ExpandableTextView;
import com.viettel.vpmt.mobiletv.media.PlayerFragment;
import com.viettel.vpmt.mobiletv.network.dto.DataStream;
import com.viettel.vpmt.mobiletv.network.dto.FilmDetail;
import com.viettel.vpmt.mobiletv.screen.filmdetail.activity.FilmActivityDetail;
import com.viettel.vpmt.mobiletv.screen.filmdetail.utils.WrapContentHeightViewPager;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Film detail fragment
 * Created by ThanhTD on 3/26/2016.
 */
public class FilmDetailFragment extends PlayerFragment<FilmDetailFragmentPresenter, FilmActivityDetail> implements FilmDetailFragmentView {
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

    FragmentStatePagerAdapter adapter;
    private String filmId = "";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_film_detail;
    }



    @Override
    public void onPrepareLayout() {
        Bundle bundle = getArguments();
        filmId = bundle.getString(Constants.Extras.ID);
        Integer partOfFilm = bundle.getInt(Constants.Extras.PART);
        getPresenter().getDetailVideo(0, filmId, partOfFilm, 0);
    }

    @OnClick(R.id.film_detail_thumb_up_down)
    void doClick() {
        getPresenter().postLikeFilm(cbLike.isChecked(), filmId);
    }

    @Override
    public FilmDetailFragmentPresenter onCreatePresenter() {
        return new FilmDetailFragmentPresenterImpl(this);
    }

    @Override
    public void doLoadToView(FilmDetail filmDetail, int positionPartActive, int tabIndex) {
        String url = filmDetail.getStreams().getUrlStreaming();
        Logger.e("URL=\n" + url);
        if (!StringUtils.isEmpty(url)) {
            initPlayer(Uri.parse(url), Util.TYPE_HLS);
        } else {
            //todo request login later
            getPresenter().getFilmStream(filmId);
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
        if (filmDetail.getContentRelated() != null) {
//            if (filmDetail.getParts() != null && filmDetail.getParts().size() == 0) {
                filmDetail.setParts(null);
//            }

            adapter = new FilmFragmentPagerAdapter(filmId, positionPartActive, filmDetail.getParts(), filmDetail.getContentRelated().getContents(),
                    getActivity().getSupportFragmentManager(), getActivity());

            viewPager.setAdapter(adapter);
        }
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(tabIndex).select();
        hideProgress();
    }

    @Override
    public void doLoadVideoStream(DataStream videoStream) {
        initPlayer(Uri.parse(videoStream.getStreams().getUrlStreaming()), Util.TYPE_HLS);
    }

    @Override
    public void doRefreshLike(boolean isLike) {
        cbLike.setChecked(isLike);
    }
}
