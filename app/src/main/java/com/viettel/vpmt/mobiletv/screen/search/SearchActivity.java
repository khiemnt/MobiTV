package com.viettel.vpmt.mobiletv.screen.search;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.BaseActivity;
import com.viettel.vpmt.mobiletv.common.view.SmoothScrollListener;
import com.viettel.vpmt.mobiletv.screen.home.adapter.HomeBoxAdapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Search Activity
 * Created by neo on 5/30/2016.
 */
public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchView {
    @Bind(R.id.search_auto_complete_tv)
    MultiAutoCompleteTextView mSearchTv;
    @Bind(R.id.search_rv)
    RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void onPrepareLayout() {
        super.onPrepareLayout();

        mSearchTv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    doSearch();
                    return true;
                }
                return false;
            }
        });

        // List
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setOnScrollListener(new SmoothScrollListener(this));
    }

    @OnClick(R.id.search_back_iv)
    public void back() {
        finish();
    }

    @OnClick(R.id.search_search_iv)
    public void searchClick() {
        doSearch();
    }

    @OnTextChanged(R.id.search_auto_complete_tv)
    public void onTextChanged() {
        getPresenter().getSuggestion(mSearchTv.getText().toString());
    }

    private void doSearch() {
        getPresenter().search(mSearchTv.getText().toString());
    }

    @Override
    public SearchPresenter onCreatePresenter() {
        return new SearchPresenterImpl(this);
    }

    @Override
    public void loadBox(HomeBoxAdapter homeBoxAdapter) {
        mRecyclerView.setAdapter(homeBoxAdapter);
    }
}
