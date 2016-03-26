package com.viettel.vpmt.mobiletv.screen.home.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.common.util.ImageUtils;
import com.viettel.vpmt.mobiletv.network.dto.Content;

import java.util.List;

/**
 * Banner Adapter
 * Created by neo on 3/23/2016.
 */
public class BannerAdapter extends PagerAdapter {
    private Context mContext;
    private List<Content> mContents;

    public BannerAdapter(Context context, List<Content> contents) {
        mContext = context;
        mContents = contents;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        Content content = getItem(position);
        if (content == null) {
            return new View(mContext);
        }

        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.item_banner, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.item_banner_image_view);

        ImageUtils.loadImage(mContext, content.getCoverImage(), imageView, true);

        container.addView(view, 0);

        return view;
    }

//    @Override
//    public int getItemPosition(Object object) {
//        return POSITION_NONE;
//    }

    // Used by ViewPager.
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
        view = null;
    }

    @Override
    public int getCount() {
        return mContents.size();
    }

    /**
     * Get content item at position
     */
    public Content getItem(int position) {
        return mContents.get(position);
    }
}
