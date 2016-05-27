package com.viettel.vpmt.mobiletv.screen.filmdetail.fragment.relate;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.screen.filmdetail.activity.FilmDetailActivity;
import com.viettel.vpmt.mobiletv.screen.filmdetail.fragment.item.ImageItem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * RecyclerView adapter for films
 * Created by ThanhTD on 3/24/2016.
 */
public class FilmRelativeAdapter extends RecyclerView.Adapter<FilmRelativeAdapter.MyViewHolder> {

    List<ImageItem> imageItems;
    Context context;

    public FilmRelativeAdapter(List<ImageItem> imageItems, Context context) {
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
        holder.title.setText(imageItems.get(position).getName());
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
                ((FilmDetailActivity) context).getFragment().getPresenter().getFilmDetail(0, imageItems.get(position).getVideoId(), null, 1);
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
