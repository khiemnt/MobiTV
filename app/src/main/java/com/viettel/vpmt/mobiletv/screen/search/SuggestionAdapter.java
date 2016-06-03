package com.viettel.vpmt.mobiletv.screen.search;

import com.viettel.vpmt.mobiletv.network.dto.Box;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Search suggestion adapter
 * Created by neo on 5/31/2016.
 */
public class SuggestionAdapter extends BaseAdapter {
    private List<Box> mBoxes = new ArrayList<>();

    public SuggestionAdapter(Context context, List<Box> boxes) {
        mBoxes = boxes;
    }
    public int getCount() {
        return mBoxes.size();
    }

    public Box getItem(int position) {
        return mBoxes.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}