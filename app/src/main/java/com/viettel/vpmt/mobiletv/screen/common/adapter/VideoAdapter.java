package com.viettel.vpmt.mobiletv.screen.common.adapter;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.common.util.ImageUtils;
import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.screen.home.controller.ContentItemClickListener;
import com.viettel.vpmt.mobiletv.screen.videodetail.activity.VideoDetailActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Video contents Adapter
 * Created by neo on 3/24/2016.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private List<Content> mContents;
    private Context mContext;
    private int mItemWidth;

    public VideoAdapter(Context context, List<Content> contents, int itemWidth) {
        mContents = contents;
        mContext = context;
        mItemWidth = itemWidth;
    }

    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);

        if (mItemWidth > 0) {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(mItemWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(layoutParams);
        }

        return new VideoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoAdapter.ViewHolder holder, int position) {
        Content content = mContents.get(position);
        if (content == null) {
            return;
        }

        // Lazy load cover
        ImageUtils.loadImage(mContext, content.getAvatarImage(), holder.mImageView, true);

        // Set title
        holder.mTitleTextView.setText(content.getName());

        // Click item behavior
        if (mContents instanceof VideoDetailActivity) {
            holder.itemView.setOnClickListener(new RelateVideoClickListener(position));
        } else {
            holder.itemView.setOnClickListener(new ContentItemClickListener(mContext, content, Box.Type.VOD));
        }
    }

    @Override
    public int getItemCount() {
        return mContents.size();
    }

    // View holder for adapter view
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_video_iv)
        public ImageView mImageView;
        @Bind(R.id.item_video_title_tv)
        public TextView mTitleTextView;
        public View mRoot;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            mRoot = v;
        }
    }

    /**
     * Related Video item clicked
     */
    private class RelateVideoClickListener implements View.OnClickListener {
        private int mPosition;

        private RelateVideoClickListener(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            if (mContext instanceof VideoDetailActivity) {
                ((VideoDetailActivity) mContext)
                        .getFragment()
                        .getPresenter()
                        .getVideoDetail(0, mContents.get(mPosition).getId());
            }
        }
    }
}
