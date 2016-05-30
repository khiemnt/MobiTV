package com.viettel.vpmt.mobiletv.screen.home.adapter;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.common.util.ImageUtils;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.screen.filmdetail.activity.FilmDetailActivity;
import com.viettel.vpmt.mobiletv.screen.home.controller.ContentItemClickListener;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Film contents Adapter
 * Created by neo on 3/24/2016.
 */
public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {
    private List<Content> mContents;
    private Context mContext;
    private int mItemWidth;

    public FilmAdapter(Context context, List<Content> contents, int itemWidth) {
        mContents = contents;
        mContext = context;
        mItemWidth = itemWidth;
    }

    @Override
    public FilmAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film, parent, false);

        return new FilmAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FilmAdapter.ViewHolder holder, int position) {
        Content content = mContents.get(position);
        if (content == null) {
            return;
        }

        if (mItemWidth > 0) {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(mItemWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.mRoot.setLayoutParams(layoutParams);
        }

        // Lazy load cover
        ImageUtils.loadImage(mContext, content.getAvatarImage(), holder.mImageView, true);

        // Set title
        holder.mTitleTextView.setText(content.getName());

        // Click item behavior
        if (mContext instanceof FilmDetailActivity) {
            // Item clicked in Film detail activity
            holder.itemView.setOnClickListener(new RelateFilmClickListener(position));
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
        @Bind(R.id.item_film_iv)
        public ImageView mImageView;
        @Bind(R.id.item_film_title_tv)
        public TextView mTitleTextView;
        public View mRoot;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            mRoot = v;
        }
    }

    /**
     * Related Film item clicked
     */
    private class RelateFilmClickListener implements View.OnClickListener {
        private int mPosition;

        private RelateFilmClickListener(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            if (mContext instanceof FilmDetailActivity) {
                ((FilmDetailActivity) mContext)
                        .getFragment()
                        .getPresenter()
                        .getFilmDetail(0, mContents.get(mPosition).getId(), null, 1);
            }
        }
    }
}
