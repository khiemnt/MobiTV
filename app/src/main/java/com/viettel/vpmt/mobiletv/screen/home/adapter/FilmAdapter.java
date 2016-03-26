package com.viettel.vpmt.mobiletv.screen.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.common.util.ImageUtils;
import com.viettel.vpmt.mobiletv.network.dto.Content;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Video contents Adapter
 * Created by neo on 3/24/2016.
 */
public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {
    private List<Content> mContents;
    private Context mContext;

    public FilmAdapter(Context context, List<Content> contents) {
        mContents = contents;
        mContext = context;
    }

    @Override
    public FilmAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film, parent, false);

        return new FilmAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FilmAdapter.ViewHolder holder, int position) {
        Content content = mContents.get(position);

        // Lazy load cover
        ImageUtils.loadImage(mContext, content.getAvatarImage(), holder.mImageView, true);

        // Set title
        holder.mTitleTextView.setText(content.getName());
    }

    @Override
    public int getItemCount() {
        return mContents.size();
    }

    // View holder for adapter view
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_film_iv)
        public ImageView mImageView;
        @Bind(R.id.item_film_title_tv)
        public TextView mTitleTextView;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
