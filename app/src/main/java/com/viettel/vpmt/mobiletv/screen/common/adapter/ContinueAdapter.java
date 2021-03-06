package com.viettel.vpmt.mobiletv.screen.common.adapter;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.common.util.ImageUtils;
import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.screen.home.controller.ContentItemClickListener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Continue contents Adapter
 * Created by neo on 3/24/2016.
 */
public class ContinueAdapter extends RecyclerView.Adapter<ContinueAdapter.ViewHolder> {
    private static final int MAX_PROGRESS = 100;
    private List<Content> mContents;
    private Context mContext;
    private int mItemWidth;

    public ContinueAdapter(Context context, List<Content> contents, int itemWidth) {
        mContents = contents;
        mContext = context;
        mItemWidth = itemWidth;
    }

    @Override
    public ContinueAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_continue, parent, false);

        if (mItemWidth > 0) {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(mItemWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(layoutParams);
        }

        return new ContinueAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContinueAdapter.ViewHolder holder, int position) {
        Content content = mContents.get(position);
        if (content == null) {
            return;
        }

//        if (mItemWidth > 0) {
//            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(mItemWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
//            holder.mRoot.setLayoutParams(layoutParams);
//        }

        // Lazy load cover
        ImageUtils.loadImage(mContext, content.getAvatarImage(), holder.mImageView, true);

        // Set title
        holder.mTitleTextView.setText(content.getName());

        // Set progress
        holder.mProgressBar.setMax(MAX_PROGRESS);
        holder.mProgressBar.setProgress(content.getProgress());

        // Click item behavior
        holder.itemView.setOnClickListener(new ContentItemClickListener(mContext, content, Box.Type.CONTINUE));
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
