package com.viettel.vpmt.mobiletv.screen.home.adapter;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.common.util.ImageUtils;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.screen.channeldetail.activity.ChannelDetailActivity;
import com.viettel.vpmt.mobiletv.screen.home.controller.ContentItemClickListener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Channel adapter
 * Created by neo on 3/24/2016.
 */
public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolder> {
    private List<Content> mContents;
    private Context mContext;

    public ChannelAdapter(Context context, List<Content> contents) {
        mContents = contents;
        mContext = context;
    }

    @Override
    public ChannelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_channel, parent, false);

        return new ChannelAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChannelAdapter.ViewHolder holder, int position) {
        Content content = mContents.get(position);
        if (content == null) {
            return;
        }

        ImageUtils.loadImage(mContext, content.getAvatarImage(), holder.mImageView, false);

        // Click item behavior
        if (mContext instanceof ChannelDetailActivity) {
            holder.itemView.setOnClickListener(new RelateChannelClickListener(position));
        } else {
            holder.itemView.setOnClickListener(new ContentItemClickListener(mContext, content));
        }
    }

    @Override
    public int getItemCount() {
        return mContents.size();
    }

    // View holder for adapter view
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_channel_iv)
        public ImageView mImageView;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    /**
     * Related Channel item clicked
     */
    private class RelateChannelClickListener implements View.OnClickListener {
        private int mPosition;

        private RelateChannelClickListener(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            if (mContext instanceof ChannelDetailActivity) {
                ((ChannelDetailActivity) mContext)
                        .getFragment()
                        .getPresenter()
                        .getChannelDetail(mContents.get(mPosition).getId());
            }
        }
    }
}
