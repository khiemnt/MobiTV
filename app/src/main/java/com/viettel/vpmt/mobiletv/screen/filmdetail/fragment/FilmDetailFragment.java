package com.viettel.vpmt.mobiletv.screen.filmdetail.fragment;

import com.google.android.exoplayer.util.Util;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.log.Logger;
import com.viettel.vpmt.mobiletv.common.Constants;
import com.viettel.vpmt.mobiletv.common.util.StringUtils;
import com.viettel.vpmt.mobiletv.common.view.ExpandableTextView;
import com.viettel.vpmt.mobiletv.media.PlayerFragment;
import com.viettel.vpmt.mobiletv.media.player.PlayerController;
import com.viettel.vpmt.mobiletv.network.dto.DataStream;
import com.viettel.vpmt.mobiletv.network.dto.FilmDetail;
import com.viettel.vpmt.mobiletv.network.dto.PartOfFilm;
import com.viettel.vpmt.mobiletv.screen.filmdetail.activity.FilmDetailActivity;
import com.viettel.vpmt.mobiletv.screen.filmdetail.utils.WrapContentHeightViewPager;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Film detail fragment
 * Created by ThanhTD on 3/26/2016.
 */
public class FilmDetailFragment extends PlayerFragment<FilmDetailFragmentPresenter, FilmDetailActivity> implements FilmDetailFragmentView {
    @Bind(R.id.fragment_film_detail_tvTitle)
    TextView mTitleTv;
    @Bind(R.id.fragment_film_detail_tvShortDes)
    TextView mShortDesTv;
    @Bind(R.id.fragment_film_detail_tvFullDes)
    ExpandableTextView mFullDesTv;
    @Bind(R.id.fragment_film_detail_tvActor)
    TextView mActorTv;
    @Bind(R.id.fragment_film_detail_tvCountry)
    TextView mCountryTv;
    @Bind(R.id.viewpager)
    WrapContentHeightViewPager mViewPager;
    @Bind(R.id.sliding_tabs)
    TabLayout mTabLayout;
    @Bind(R.id.film_detail_thumb_up_down)
    CheckBox mLikeCb;
    @Bind(R.id.film_detail_number_of_view)
    CheckBox mPlayCb;

    FragmentStatePagerAdapter adapter;
    private String mFilmId = "";

    public static FilmDetailFragment newInstance(String filmId, String title, String coverImageUrl) {
        FilmDetailFragment fragment = new FilmDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Constants.Extras.ID, filmId);
        bundle.putString(Constants.Extras.TITLE, title);
        bundle.putString(Constants.Extras.COVER_IMAGE_URL, coverImageUrl);
        bundle.putString(Constants.Extras.PART, "");
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_film_detail;
    }


    @Override
    public void onPrepareLayout() {
        Bundle bundle = getArguments();
        mFilmId = bundle.getString(Constants.Extras.ID);
        final String partOfFilm = bundle.getString(Constants.Extras.PART);

        getPresenter().getFilmDetail(0, mFilmId, partOfFilm, 0);

        // Pre-fill
        mTitleTv.setText(bundle.getString(Constants.Extras.TITLE));
    }

    @Override
    protected void firstConfigPlayerController() {
        mPlayerController.setLikeButtonVisibility(View.GONE);
        mPlayerController.setShareButtonVisibility(View.GONE);
    }

    @OnClick(R.id.film_detail_thumb_up_down)
    void onLikeClicked() {
        getPresenter().postLikeFilm(mFilmId);
    }

    @Override
    public FilmDetailFragmentPresenter onCreatePresenter() {
        return new FilmDetailFragmentPresenterImpl(this);
    }

    @Override
    public void doLoadToView(FilmDetail filmDetail, int positionPartActive, int tabIndex) {
        mFilmId = filmDetail.getFilmDetail().getId();

        String url = filmDetail.getStreams().getUrlStreaming();
        Logger.e("URL=\n" + url);
        if (!StringUtils.isEmpty(url)) {
            initPlayer(Uri.parse(url), Util.TYPE_HLS, filmDetail.getFilmDetail().getCoverImage());
        } else {
            //todo request login later
            getPresenter().getFilmStream(mFilmId);
        }

        // Title & Description
        mTitleTv.setText(filmDetail.getFilmDetail().getName());
        mShortDesTv.setText(filmDetail.getFilmDetail().getShortDesc());
        if (!StringUtils.isEmpty(filmDetail.getFilmDetail().getDescription())) {
            mFullDesTv.setTrim(true);
            mFullDesTv.setText(filmDetail.getFilmDetail().getDescription(), TextView.BufferType.SPANNABLE);
        } else {
            mFullDesTv.setVisibility(View.GONE);
        }

        // Like
        mLikeCb.setChecked(filmDetail.getFilmDetail().isFavourite());
        mLikeCb.setText(filmDetail.getFilmDetail().getLikeCount() != null
                ? filmDetail.getFilmDetail().getLikeCount().toString() : "0");

        // Play count
        mPlayCb.setText(filmDetail.getFilmDetail().getPlayCount() != null ? filmDetail.getFilmDetail().getPlayCount().toString() : "0");

        // Actors
        if (!StringUtils.isEmpty(filmDetail.getFilmDetail().getActors())) {
            mActorTv.setText(String.format(getString(R.string.actor_format), filmDetail.getFilmDetail().getActors()));
        } else {
            mActorTv.setVisibility(View.GONE);
        }

        // Country
        if (!StringUtils.isEmpty(filmDetail.getFilmDetail().getCountry())) {
            mCountryTv.setText(String.format(getString(R.string.country_format), filmDetail.getFilmDetail().getCountry()));
        } else {
            mCountryTv.setVisibility(View.GONE);
        }

        // List of parts, Related films and comments

        // Parts of film
        List<PartOfFilm> partOfFilms = filmDetail.getParts();
        if (partOfFilms == null || partOfFilms.size() == 0) {
            // Has no part
            mPlayerController.setNextVisibility(View.GONE);
            mPlayerController.setPreviousVisibility(View.GONE);
        } else {
            // Has parts
            setPlayerParts(partOfFilms, positionPartActive);
        }

        adapter = new FilmFragmentPagerAdapter(mFilmId, positionPartActive, filmDetail.getParts(), filmDetail.getContentRelated().getContents(),
                getActivity().getSupportFragmentManager(), getActivity());

        mViewPager.setAdapter(adapter);
//        }
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(tabIndex).select();
        hideProgress();
    }

    private void setPlayerParts(List<PartOfFilm> partOfFilms, int positionPartActive) {
        // Visible next/prev buttons
        mPlayerController.setNextVisibility(View.VISIBLE);
        mPlayerController.setPreviousVisibility(View.VISIBLE);

        // Pass data to controller
        List<PlayerController.VideoPart> videoParts = new ArrayList<>();
        for (int i = 0; i < partOfFilms.size(); i++) {
            PartOfFilm partOfFilm = partOfFilms.get(i);
            videoParts.add(new PlayerController.VideoPart(i, partOfFilm.getName()));
        }

        mPlayerController.setVideoParts(videoParts);
        mPlayerController.setPartPosition(positionPartActive);
        mPlayerController.setPrevNextListeners(new NextClicked(partOfFilms), new PrevClicked(partOfFilms));

        // Validate views
        if (positionPartActive == 0) {
            mPlayerController.setNextEnabled(true);
            mPlayerController.setPreviousEnabled(false);
        } else if (positionPartActive == partOfFilms.size() - 1) {
            mPlayerController.setNextEnabled(false);
            mPlayerController.setPreviousEnabled(true);
        } else {
            mPlayerController.setNextEnabled(true);
            mPlayerController.setPreviousEnabled(true);
        }
    }

    @Override
    public void doLoadVideoStream(DataStream videoStream) {
        initPlayer(Uri.parse(videoStream.getStreams().getUrlStreaming()), Util.TYPE_HLS);
    }

    @Override
    public void doRefreshLike(boolean isLike) {
        mLikeCb.setChecked(isLike);
        try {
            int likeCount = Integer.valueOf(mLikeCb.getText().toString());
            likeCount = isLike ? likeCount + 1 : likeCount - 1;
            if (likeCount < 0) {
                likeCount = 0;
            }
            mLikeCb.setText(String.valueOf(likeCount));
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    }

    private class NextClicked implements View.OnClickListener {
        private List<PartOfFilm> mPartOfFilms;

        public NextClicked(List<PartOfFilm> partOfFilms) {
            mPartOfFilms = partOfFilms;
        }

        @Override
        public void onClick(View v) {
            int position = mPlayerController.getPartPosition() + 1;
            getPresenter().getFilmDetail(position, mFilmId,
                    mPartOfFilms.get(position).getId(),
                    mTabLayout.getSelectedTabPosition());
        }
    }

    private class PrevClicked implements View.OnClickListener {
        private List<PartOfFilm> mPartOfFilms;

        public PrevClicked(List<PartOfFilm> partOfFilms) {
            mPartOfFilms = partOfFilms;
        }
        @Override
        public void onClick(View v) {
            int position = mPlayerController.getPartPosition() - 1;
            getPresenter().getFilmDetail(position, mFilmId,
                    mPartOfFilms.get(position).getId(),
                    mTabLayout.getSelectedTabPosition());
        }
    }
}
