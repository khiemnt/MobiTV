package com.viettel.vpmt.mobiletv.common.util;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.viettel.vpmt.mobiletv.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Image Utils
 * Created by neo on 3/25/2016.
 */
public class ImageUtils {
    /**
     * Lazy load by Picasso
     */
    public static void loadImage(Context context, String imageUrl, ImageView imageView, boolean fit) {
        if (context == null || imageView == null) {
            return;
        }

        if (StringUtils.isEmpty(imageUrl)) {
            imageUrl = null;
        }

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

    public static Drawable getDrawable(Context context, int drawableId) {
        Drawable myDrawable;
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            myDrawable = context.getDrawable(drawableId);
        } else {
            myDrawable = context.getResources().getDrawable(drawableId);
        }

        return myDrawable;
    }
}
