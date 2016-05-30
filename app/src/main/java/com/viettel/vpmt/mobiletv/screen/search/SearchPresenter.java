package com.viettel.vpmt.mobiletv.screen.search;

import com.viettel.vpmt.mobiletv.base.BasePresenter;

/**
 * Search presenter
 * Created by neo on 5/30/2016.
 */
public interface SearchPresenter extends BasePresenter {
    void search(String keyword);

    void getSuggestion(String keyword);
}
