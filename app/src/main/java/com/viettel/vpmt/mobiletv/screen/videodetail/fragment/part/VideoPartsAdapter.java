package com.viettel.vpmt.mobiletv.screen.videodetail.fragment.part;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.common.util.CompatibilityUtil;
import com.viettel.vpmt.mobiletv.common.util.ImageUtils;
import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.network.dto.PartOfVideo;
import com.viettel.vpmt.mobiletv.screen.videodetail.activity.VideoDetailActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * RecyclerView adapter for video parts
 * Created by ThanhTD on 3/29/2016.
 */
public class VideoPartsAdapter extends RecyclerView.Adapter<VideoPartsAdapter.MyViewHolder> {
    private final int mItemImageWidth;
    private List<PartOfVideo> mParts;
    private Context mContext;
    private String mVideoId;
    private int mPositionActive = 0;
    private int mItemWidth;

    public VideoPartsAdapter(String videoId, List<PartOfVideo> parts, int positionActive, Context context, int itemWidth) {
        mVideoId = videoId;
        mParts = parts;
        mContext = context;
        mPositionActive = positionActive;
        mItemWidth = itemWidth;
        mItemImageWidth = CompatibilityUtil.getWidthItemHasSpacing(context, Box.Type.VOD);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_video, parent, false);

        if (mItemWidth > 0) {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(mItemWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(layoutParams);
        }


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (mItemImageWidth > 0) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mItemImageWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
            holder.mImageView.setLayoutParams(layoutParams);
        }
        holder.title.setText(mParts.get(position).getName());
        holder.mImageView.setBackgroundResource(R.drawable.background_part_unselect);
        holder.mImageView.setPadding(0, 0, 0, 0);
        if (position == mPositionActive) {
            holder.mImageView.setBackgroundResource(R.drawable.background_part_selected);
            holder.mImageView.setPadding(2, 2, 2, 2);
        }
        ImageUtils.loadImage(mContext, mParts.get(position).getUrlAvatar(), holder.mImageView, true);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mImageView.setBackgroundResource(R.drawable.background_part_selected);
                holder.mImageView.setPadding(2, 2, 2, 2);
                ((VideoDetailActivity) mContext).getFragment().getPresenter()
                        .getVideoDetail(position, mParts.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mParts.size();
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
