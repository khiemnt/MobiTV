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
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Focus contents Adapter
 * Created by neo on 3/24/2016.
 */
public class FocusAdapter extends RecyclerView.Adapter<FocusAdapter.ViewHolder> {
    private List<Content> mContents;
    private Context mContext;
    private int mItemWidth;

    public FocusAdapter(Context context, List<Content> contents, int itemWidth) {
        mContents = contents;
        mContext = context;
        mItemWidth = itemWidth;
    }

    @Override
    public FocusAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_focus, parent, false);

        if (mItemWidth > 0) {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(mItemWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(layoutParams);
        }

        return new FocusAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FocusAdapter.ViewHolder holder, int position) {
        Content content = mContents.get(position);
        if (content == null) {
            return;
        }

        // Lazy load cover
        ImageUtils.loadImage(mContext, content.getAvatarImage(), holder.mImageView, true);

        // Set title
        holder.mTitleTextView.setText(content.getName());

        // Set Desc
        holder.mDescTextView.setText(content.getShortDesc());

        // Live status
        if (!content.isLive()) {
            holder.mDescTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }

        // Click item behavior
        holder.itemView.setOnClickListener(new ContentItemClickListener(mContext, content, Box.Type.FOCUS));
    }

    @Override
    public int getItemCount() {
        return mContents.size();
    }

    // View holder for adapter view
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_focus_iv)
        public ImageView mImageView;
        @Bind(R.id.item_focus_title_tv)
        public TextView mTitleTextView;
        @Bind(R.id.item_focus_desc_tv)
        public TextView mDescTextView;
        public View mRoot;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            mRoot = v;
        }
    }
}
