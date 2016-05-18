package com.viettel.vpmt.mobiletv.screen.filmdetail.fragment.part;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.network.dto.PartOfFilm;
import com.viettel.vpmt.mobiletv.screen.filmdetail.activity.FilmActivityDetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * RecyclerView adapter for parts of films
 * Created by ThanhTD on 3/29/2016.
 */
public class FilmPartsAdapter extends RecyclerView.Adapter<FilmPartsAdapter.MyViewHolder> {

    List<PartOfFilm> mParts;
    Context mContext;
    private String mFilmId;
    private int mPositionActive = 0;

    public FilmPartsAdapter(String filmId, List<PartOfFilm> parts, int positionActive, Context context) {
        mFilmId = filmId;
        mParts = parts;
        mContext = context;
        mPositionActive = positionActive;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.part_film, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.title.setText(mParts.get(position).getName());
        holder.title.setBackgroundResource(R.drawable.background_part_unselect);
        if (position == mPositionActive) {
            holder.title.setBackgroundResource(R.drawable.background_part_selected);
        }
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.title.setBackgroundResource(R.drawable.background_part_selected);
                ((FilmActivityDetail) mContext).getFragment().getPresenter().getDetailVideo(position, mFilmId, mParts.get(position).getId(), 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mParts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tvNumber);
        }
    }

}
