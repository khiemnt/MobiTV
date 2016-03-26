package com.viettel.vpmt.mobiletv.screen.bundle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.network.dto.Content;

import java.util.List;

/**
 * Bundle video adapter
 * Created by neo on 3/26/2016.
 */
public class BundleVideoAdapter extends RecyclerView.Adapter<BundleVideoAdapter.ViewHolder> {
    private Context mContext;
    private List<Content> mContents;
    private Box.Type mContentType;

    public BundleVideoAdapter(Context context, List<Content> contents, Box.Type contentType) {
        mContext = context;
        mContents = contents;
        mContentType = contentType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mContents.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
