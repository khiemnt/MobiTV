package com.viettel.vpmt.mobiletv.screen.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.network.dto.Content;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Continue contents Adapter
 * Created by neo on 3/24/2016.
 */
public class ContinueAdapter extends RecyclerView.Adapter<ContinueAdapter.ViewHolder> {
    private List<Content> mContents;
    private Context mContext;

    public ContinueAdapter(Context context, List<Content> contents) {
        mContents = contents;
        mContext = context;
    }

    @Override
    public ContinueAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_continue, parent, false);

        return new ContinueAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContinueAdapter.ViewHolder holder, int position) {
        Content content = mContents.get(position);

        // Lazy load cover
        Picasso.with(mContext)
                .load(content.getCoverImage())
                .placeholder(R.drawable.app_logo)
                .error(R.drawable.app_logo)
                .fit()
                .into(holder.mImageView);

        // Set title
        holder.mTitleTextView.setText(content.getName());

        // Set progress
        holder.mProgressBar.setMax(10);
        holder.mProgressBar.setProgress(5);

    }

    @Override
    public int getItemCount() {
        return mContents.size();
    }

    // View holder for adapter view
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View mRoot;

        @Bind(R.id.item_continue_iv)
        public ImageView mImageView;
        @Bind(R.id.item_continue_title_tv)
        public TextView mTitleTextView;
        @Bind(R.id.item_continue_progress)
        public ProgressBar mProgressBar;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            mRoot = v;
        }
    }
}
