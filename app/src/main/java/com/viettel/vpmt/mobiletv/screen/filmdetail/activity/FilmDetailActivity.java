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
public class FilmDetailActivity extends BaseActivity<FilmDetailPresenter> implements FilmDetailView {
    private FilmDetailFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String filmId = getIntent().getStringExtra(Constants.Extras.ID);
        String title = getIntent().getStringExtra(Constants.Extras.TITLE);
        String coverImageUrl = getIntent().getStringExtra(Constants.Extras.COVER_IMAGE_URL);
        addFragment(filmId, title, coverImageUrl);
    }

    private void addFragment(String filmId, String title, String coverImageUrl) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        fragment = FilmDetailFragment.newInstance(filmId, title, coverImageUrl);

        transaction.add(R.id.frame_layout, fragment);
        transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out);
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
