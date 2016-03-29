package com.viettel.vpmt.mobiletv.screen.film.activity;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseActivity;
import com.viettel.vpmt.mobiletv.common.Constants;
import com.viettel.vpmt.mobiletv.screen.film.fragment.DetailFilmDetailFilmFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by ThanhTD on 3/22/2016.
 */
public class DetailFilmFilmActivity extends BaseActivity<DetailFilmPresenter> implements DetailFilmView {
    DetailFilmDetailFilmFragment fragment;
    String filmId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filmId = getIntent().getStringExtra(Constants.Extras.ID);
        addFragment(filmId);
    }

    private void addFragment(String filmId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putFloat("filmId", Float.valueOf(filmId));
        bundle.putFloat("part", 0);
        fragment = new DetailFilmDetailFilmFragment();
        fragment.setArguments(bundle);
        transaction.add(R.id.frame_layout, fragment);
        transaction.commit();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_or_film_detail;
    }

    @Override
    public DetailFilmPresenter onCreatePresenter() {
        return new DetailFilmPresenterImpl(this);
    }

    public DetailFilmDetailFilmFragment getFragment() {
        return fragment;
    }
}
