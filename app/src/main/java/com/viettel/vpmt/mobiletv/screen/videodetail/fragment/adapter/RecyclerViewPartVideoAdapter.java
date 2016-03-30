package com.viettel.vpmt.mobiletv.screen.videodetail.fragment.adapter;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.common.util.ImageUtils;
import com.viettel.vpmt.mobiletv.network.dto.PartOfVideo;
import com.viettel.vpmt.mobiletv.screen.videodetail.activity.VideoDetailActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ThanhTD on 3/29/2016.
 */
public class RecyclerViewPartVideoAdapter extends RecyclerView.Adapter<RecyclerViewPartVideoAdapter.MyViewHolder> {
    List<PartOfVideo> parts;
    Context mContext;
    private float videoId;
    private int positionActive = 0;

    public RecyclerViewPartVideoAdapter(Float videoId, List<PartOfVideo> parts, int positionActive, Context context) {
        this.videoId = videoId;
        this.parts = parts;
        this.mContext = context;
        this.positionActive = positionActive;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_video, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.title.setText(parts.get(position).getName());
        holder.mImageView.setBackgroundResource(R.drawable.background_part_unselect);
        holder.mImageView.setPadding(0, 0, 0, 0);
        if (position == positionActive) {
            holder.mImageView.setBackgroundResource(R.drawable.background_part_selected);
            holder.mImageView.setPadding(2, 2, 2, 2);
        }
        ImageUtils.loadImage(mContext, parts.get(position).getUrlAvatar(), holder.mImageView, true);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mImageView.setBackgroundResource(R.drawable.background_part_selected);
                holder.mImageView.setPadding(2, 2, 2, 2);
                ((VideoDetailActivity) mContext).getFragment().getPresenter().getDetailVideo(position, videoId, parts.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return parts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.item_video_iv);
            title = (TextView) itemView.findViewById(R.id.item_video_title_tv);
        }
    }
}