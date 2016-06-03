package com.viettel.vpmt.mobiletv.screen.search;

import com.viettel.vpmt.mobiletv.base.BaseView;
import com.viettel.vpmt.mobiletv.screen.common.adapter.HomeBoxAdapter;

/**
 * Search view
 * Created by neo on 5/30/2016.
 */
public interface SearchView extends BaseView<SearchPresenter>{
    void loadBox(HomeBoxAdapter homeBoxAdapter);

    void loadSuggestion(SuggestionAdapter adapter);
}
