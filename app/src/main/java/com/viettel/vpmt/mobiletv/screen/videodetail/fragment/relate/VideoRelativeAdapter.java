package com.viettel.vpmt.mobiletv.screen.videodetail.fragment.relate;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.common.util.ImageUtils;
import com.viettel.vpmt.mobiletv.screen.videodetail.activity.VideoDetailActivity;
import com.viettel.vpmt.mobiletv.screen.videodetail.fragment.item.ImageItem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * RecyclerView adapter for videos
 * Created by ThanhTD on 3/24/2016.
 */
public class VideoRelativeAdapter extends RecyclerView.Adapter<VideoRelativeAdapter.MyViewHolder> {

    List<ImageItem> imageItems;
    Context context;

    public VideoRelativeAdapter(List<ImageItem> imageItems, Context context) {
        this.imageItems = imageItems;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return imageItems.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.title.setText(imageItems.get(position).getName());
        // Lazy load cover
        ImageUtils.loadImage(context, imageItems.get(position).getUri(), holder.image, true);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((VideoDetailActivity) context).getFragment().getPresenter()
                        .getVideoDetail(0, imageItems.get(position).getVideoId());
            }
        });
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.item_video_iv);
            title = (TextView) itemView.findViewById(R.id.item_video_title_tv);
        }
    }
}
