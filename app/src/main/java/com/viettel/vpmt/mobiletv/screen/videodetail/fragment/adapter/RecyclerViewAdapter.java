package com.viettel.vpmt.mobiletv.screen.videodetail.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.screen.videodetail.fragment.adapter.item.ImageItem;

import java.util.List;

/**
 * Created by ThanhTD on 3/24/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>
{

    List<ImageItem> imageItems;
    Context context;

    public RecyclerViewAdapter(List<ImageItem> imageItems, Context context)
    {
        this.imageItems = imageItems;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.image_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount()
    {
        return imageItems.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        holder.title.setText(imageItems.get(position).getDes());
        Picasso.with(context)
                .load(imageItems.get(position).getUri())
                .into(holder.image, new Callback()
                {
                    @Override
                    public void onSuccess()
                    {
                    }

                    @Override
                    public void onError()
                    {
                    }
                });
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        CardView cv;
        public TextView title;
        public ImageView image;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cardView);
            image = (ImageView) itemView.findViewById(R.id.image_item);
            title = (TextView) itemView.findViewById(R.id.image_item_tvDes);
        }
    }

}
