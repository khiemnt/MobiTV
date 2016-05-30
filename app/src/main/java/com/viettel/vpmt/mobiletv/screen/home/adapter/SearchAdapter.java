package com.viettel.vpmt.mobiletv.screen.home.adapter;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.log.Logger;
import com.viettel.vpmt.mobiletv.common.Constants;
import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.screen.home.HomeBoxActivity;
import com.viettel.vpmt.mobiletv.screen.home.animation.DepthPageTransformer;
import com.viettel.vpmt.mobiletv.screen.home.animation.PagerRunner;
import com.viettel.vpmt.mobiletv.screen.home.controller.SeeAllClickListener;
import com.viewpagerindicator.CirclePageIndicator;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Search Adapter
 * Created by neo on 3/22/2016.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context mContext;
    private List<Box> mBoxes;
    private HorizontalItemDecoration mItemDecoration;

    public SearchAdapter(Context context, List<Box> boxes) {
        mBoxes = boxes;
        mContext = context;
    }


    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_box_home, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {
        Box box = getItem(position);
        if (box == null) {
            return;
        }
        if (box.getType() == null) {
            Logger.e("@@@", "Type NULLLL " + box.getId());
        }
        switch (box.getType()) {
            case BANNER:
                bindBannerBox(holder, box);
                break;
            case LIVETV:
                bindBoxSection(holder, box,
                        new ChannelAdapter(mContext, box.getContents(),
                                mContext.getResources().getDimensionPixelSize(R.dimen.item_channel_width)));
                break;
            case VOD:
                bindBoxSection(holder, box,
                        new VideoAdapter(mContext, box.getContents(),
                                mContext.getResources().getDimensionPixelSize(R.dimen.item_video_width)));
                break;
            case TVSHOW:
                bindBoxSection(holder, box,
                        new VideoAdapter(mContext, box.getContents(),
                                mContext.getResources().getDimensionPixelSize(R.dimen.item_channel_width)));
                break;
            case CONTINUE:
                bindBoxSection(holder, box,
                        new ContinueAdapter(mContext, box.getContents(),
                                mContext.getResources().getDimensionPixelSize(R.dimen.item_video_width)));
                break;
            case FOCUS:
                bindBoxSection(holder, box,
                        new FocusAdapter(mContext, box.getContents(),
                                mContext.getResources().getDimensionPixelSize(R.dimen.item_video_width)));
                break;
            case FILM:
                bindBoxSection(holder, box,
                        new FilmAdapter(mContext, box.getContents(),
                                mContext.getResources().getDimensionPixelSize(R.dimen.item_film_width)));
                break;
            default:
                bindBoxSection(holder, box,
                        new VideoAdapter(mContext, box.getContents(),
                                mContext.getResources().getDimensionPixelSize(R.dimen.item_video_width)));
                break;
        }
    }

    /**
     * Bind all horizontal sections in home box
     */
    private void bindBoxSection(ViewHolder holder, Box box, RecyclerView.Adapter adapter) {
        holder.mViewPagerLayout.setVisibility(View.GONE);
        holder.mRecyclerView.setVisibility(View.VISIBLE);
        holder.mTitleTv.setVisibility(View.VISIBLE);
        holder.mSeeAllTv.setVisibility(View.VISIBLE);

        holder.mTitleTv.setText(box.getName());

        // See all content clicked
        if (mContext instanceof HomeBoxActivity) {
            holder.mSeeAllTv.setOnClickListener(new SeeAllClickListener((HomeBoxActivity) mContext, box));
        } else {
            holder.mSeeAllTv.setVisibility(View.GONE);
        }

        // Bind content of channels
        RecyclerView.LayoutManager layoutManager
                = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setAutoMeasureEnabled(true);
        holder.mRecyclerView.setLayoutManager(layoutManager);

        holder.mRecyclerView.setAdapter(adapter);

        // Items decoration
        holder.mRecyclerView.removeItemDecoration(getItemDecoration(box.getType()));
        holder.mRecyclerView.addItemDecoration(getItemDecoration(box.getType()));
    }

    private HorizontalItemDecoration getItemDecoration(Box.Type type) {
        if (mItemDecoration == null) {
            int spacingInPixels;

            if (type == Box.Type.FILM || type == Box.Type.LIVETV) {
                spacingInPixels = 4;
            } else {
                spacingInPixels = Constants.ITEM_SPACING;
            }

            mItemDecoration = new HorizontalItemDecoration(spacingInPixels);
        }

        return mItemDecoration;
    }

    /**
     * Bind view for Banner
     */
    private void bindBannerBox(ViewHolder holder, Box box) {
        holder.mViewPagerLayout.setVisibility(View.VISIBLE);
        holder.mRecyclerView.setVisibility(View.GONE);
        holder.mTitleTv.setVisibility(View.GONE);
        holder.mSeeAllTv.setVisibility(View.GONE);

        // Bind content of Banner
        List<Content> contents = box.getContents();
        BannerAdapter bannerAdapter = new BannerAdapter(mContext, contents);
        holder.mViewPager.setAdapter(bannerAdapter);
        holder.mViewPager.setPageTransformer(true, new DepthPageTransformer());
        bannerAdapter.notifyDataSetChanged();

        holder.mIndicator.setViewPager(holder.mViewPager);

        new PagerRunner(holder.mViewPager).autoScrollPager();
    }


    /**
     * Get Box item of position
     */
    public Box getItem(int position) {
        return mBoxes.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mBoxes.size();
    }

    // View holder for adapter view
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View mRoot;

        @Bind(R.id.box_common_see_all_tv)
        public TextView mSeeAllTv;
        @Bind(R.id.box_common_title_tv)
        public TextView mTitleTv;
        @Bind(R.id.box_common_rv)
        public RecyclerView mRecyclerView;
        @Bind(R.id.item_banner_pager)
        public ViewPager mViewPager;
        @Bind(R.id.box_common_pager_rl)
        public RelativeLayout mViewPagerLayout;
        @Bind(R.id.item_banner_pager_indicator)
        public CirclePageIndicator mIndicator;


        public ViewHolder(View v) {
            super(v);
            mRoot = v;
            ButterKnife.bind(this, v);
        }
    }
}
