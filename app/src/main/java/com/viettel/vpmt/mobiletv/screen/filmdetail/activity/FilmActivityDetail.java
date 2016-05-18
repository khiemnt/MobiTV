package com.viettel.vpmt.mobiletv.screen.filmdetail.activity;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseActivity;
import com.viettel.vpmt.mobiletv.common.Constants;
import com.viettel.vpmt.mobiletv.screen.filmdetail.fragment.FilmDetailFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

/**
 * Film detail activity
 * Created by ThanhTD on 3/22/2016.
 */
public class FilmActivityDetail extends BaseActivity<FilmDetailPresenter> implements FilmDetailView {
    FilmDetailFragment fragment;
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
        bundle.putString(Constants.Extras.ID, filmId);
        bundle.putInt(Constants.Extras.PART, 0);
        fragment = new FilmDetailFragment();
        fragment.setArguments(bundle);
        transaction.add(R.id.frame_layout, fragment);
        transaction.commit();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common_detail;
    }

    @Override
    public FilmDetailPresenter onCreatePresenter() {
        return new FilmDetailPresenterImpl(this);
    }

    public FilmDetailFragment getFragment() {
        return fragment;
    }
}
