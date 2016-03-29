package com.viettel.vpmt.mobiletv.screen.film.fragment.adapter;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.screen.film.activity.FilmDetailActivity;
import com.viettel.vpmt.mobiletv.screen.film.fragment.adapter.item.ImageItem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ThanhTD on 3/24/2016.
 */
public class RecyclerViewFilmAdapter extends RecyclerView.Adapter<RecyclerViewFilmAdapter.MyViewHolder> {

    List<ImageItem> imageItems;
    Context context;

    public RecyclerViewFilmAdapter(List<ImageItem> imageItems, Context context) {
        this.imageItems = imageItems;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_film, parent, false);
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
        holder.title.setText(imageItems.get(position).getDes());
        Picasso.with(context)
                .load(imageItems.get(position).getUri())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .into(holder.image, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                    }
                });
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FilmDetailActivity) context).getFragment().getPresenter().getDetailVideo(0, imageItems.get(position).getVideoId(), null);
            }
        });
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.item_film_iv);
            title = (TextView) itemView.findViewById(R.id.item_film_title_tv);
        }
    }
}
