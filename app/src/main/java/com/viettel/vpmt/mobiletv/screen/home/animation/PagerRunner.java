package com.viettel.vpmt.mobiletv.screen.home.animation;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;

/**
 * Automatically run on pagers
 * Created by neo on 3/24/2016.
 */
public class PagerRunner {
    private static final int ANIM_VIEWPAGER_DELAY = 5000; // in milliseconds
    private static final int ANIM_VIEWPAGER_DELAY_USER_VIEW = 5000;
    private boolean stopSliding;
    private Handler handler;
    private Runnable animateViewPager;
    private ViewPager mViewPager;
    private int mSize;

    public PagerRunner(ViewPager viewPager) {
        mViewPager = viewPager;
        mSize = mViewPager.getAdapter().getCount();
    }

    /**
     * Switch pagers automatically
     */
    public void autoScrollPager() {
        mViewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction()) {

                    case MotionEvent.ACTION_CANCEL:
                        break;

                    case MotionEvent.ACTION_UP:
                        // calls when touch release on ViewPager
                        if (mSize > 0) {
                            stopSliding = false;
                            runnable();
                            handler.postDelayed(animateViewPager,
                                    ANIM_VIEWPAGER_DELAY_USER_VIEW);
                        }
                        break;

                    case MotionEvent.ACTION_MOVE:
                        // calls when ViewPager touch
                        if (handler != null && !stopSliding) {
                            stopSliding = true;
                            handler.removeCallbacks(animateViewPager);
                        }
                        break;
                }
                return false;
            }
        });

        runnable();
    }

    /**
     * Auto runnable ViewPager
     */
    public void runnable() {
        handler = new Handler();
        animateViewPager = new Runnable() {
            public void run() {
                if (!stopSliding) {
                    if (mViewPager.getCurrentItem() == mSize - 1) {
                        mViewPager.setCurrentItem(0);
                    } else {
                        mViewPager.setCurrentItem(
                                mViewPager.getCurrentItem() + 1, true);
                    }
                    handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
                }
            }
        };

        handler.postDelayed(animateViewPager,
                ANIM_VIEWPAGER_DELAY_USER_VIEW);
    }
}
