package com.viettel.vpmt.mobiletv.common.util;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.viettel.vpmt.mobiletv.R;

/**
 * Image Utils
 * Created by neo on 3/25/2016.
 */
public class ImageUtils {
    /**
     * Lazy load by Picasso
     */
    public static void loadImage(Context context, String imageUrl, ImageView imageView, boolean fit) {
        RequestCreator creator = Picasso.with(context)
                .load(imageUrl)
                .placeholder(R.color.image_placeholder)
                .error(R.color.image_placeholder);

        if (fit) {
            creator.fit();
        }

        // Load image
        creator.into(imageView);
    }
}
