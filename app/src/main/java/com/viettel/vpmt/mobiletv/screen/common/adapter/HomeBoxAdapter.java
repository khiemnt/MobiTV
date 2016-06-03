package com.viettel.vpmt.mobiletv.screen.common.adapter;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.common.util.CompatibilityUtil;
import com.viettel.vpmt.mobiletv.network.dto.Box;
import com.viettel.vpmt.mobiletv.network.dto.Content;
import com.viettel.vpmt.mobiletv.screen.bundle.BundleDividerDecoration;
import com.viettel.vpmt.mobiletv.screen.home.HomeBoxActivity;
import com.viettel.vpmt.mobiletv.screen.home.animation.DepthPageTransformer;
import com.viettel.vpmt.mobiletv.screen.home.animation.PagerRunner;
import com.viettel.vpmt.mobiletv.screen.home.controller.SeeAllClickListener;
import com.viewpagerindicator.CirclePageIndicator;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Common Adapter
 * Created by neo on 3/22/2016.
 */
public class HomeBoxAdapter extends RecyclerView.Adapter<HomeBoxAdapter.ViewHolder> {
    private Context mContext;
    private List<Box> mBoxes;
    private RecyclerView.ItemDecoration mItemDecoration;
    private GroupViewType mGroupViewType;
    private boolean mNoRightMargin;
    private boolean mIsTvHome;

    public HomeBoxAdapter(Context context, List<Box> boxes, GroupViewType groupViewType, boolean noRightMargin, boolean isTvHome) {
        mBoxes = boxes;
        mContext = context;
        mGroupViewType = groupViewType;
        mNoRightMargin = noRightMargin;
        mIsTvHome = isTvHome;
    }


    @Override
    public HomeBoxAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_box_home, parent, false);
        LinearLayout container = (LinearLayout) v.findViewById(R.id.item_box_home_recycler_view_container);
        int margin = CompatibilityUtil.getScreenMargin(mContext);
        if (mNoRightMargin) {
            container.setPadding(margin, margin, 0, margin);
        } else {
            container.setPadding(margin, margin, margin, margin);
        }

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HomeBoxAdapter.ViewHolder holder, int position) {
        Box box = getItem(position);
        if (box == null || box.getType() == null) {
            return;
        }

        int itemWidth = CompatibilityUtil.getWidthItemHasSpacing(mContext, box.getType());
        switch (box.getType()) {
            case BANNER:
                bindBannerBox(holder, box);
                break;
            case LIVETV:
                bindBoxSection(holder, box,
                        new ChannelAdapter(mContext, box.getContents(), itemWidth));
                break;
            case VOD:
                bindBoxSection(holder, box,
                        new VideoAdapter(mContext, box.getContents(), itemWidth));
                break;
            case TVSHOW:
                bindBoxSection(holder, box,
                        new VideoAdapter(mContext, box.getContents(), itemWidth));
                break;
            case CONTINUE:
                bindBoxSection(holder, box,
                        new ContinueAdapter(mContext, box.getContents(), itemWidth));
                break;
            case FOCUS:
                bindBoxSection(holder, box,
                        new FocusAdapter(mContext, box.getContents(), itemWidth));
                break;
            case FILM:
                bindBoxSection(holder, box,
                        new FilmAdapter(mContext, box.getContents(), itemWidth));
                break;
            default:
                bindBoxSection(holder, box,
                        new VideoAdapter(mContext, box.getContents(), itemWidth));
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

        if (mIsTvHome && box.getContents().size() < Box.NUMBER_CHANNEL_THRESHOLD) {
            holder.mSeeAllTv.setVisibility(View.GONE);
        } else {
            // See all content clicked
            if (mContext instanceof HomeBoxActivity) {
                holder.mSeeAllTv.setOnClickListener(new SeeAllClickListener((HomeBoxActivity) mContext, box));
            } else {
                holder.mSeeAllTv.setVisibility(View.GONE);
            }
        }

        // Bind content of channels
//        RecyclerView.LayoutManager layoutManager
//                = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
//        layoutManager.setAutoMeasureEnabled(true);
        holder.mRecyclerView.setLayoutManager(getLayoutManager(mGroupViewType, box.getType()));

        holder.mRecyclerView.setAdapter(adapter);

        // Items decoration
        holder.mRecyclerView.removeItemDecoration(getItemDecoration(box.getType()));
        holder.mRecyclerView.addItemDecoration(getItemDecoration(box.getType()));
    }

    private RecyclerView.ItemDecoration getItemDecoration(Box.Type type) {
        if (mItemDecoration == null) {
            int spacingInPixels = CompatibilityUtil.getItemSpacing(mContext);

            if (mIsTvHome && type == Box.Type.LIVETV) {
                mItemDecoration = new BundleDividerDecoration(mContext, CompatibilityUtil.getNumberItem(mContext, type));
            } else {
                mItemDecoration = new HorizontalItemDecoration(spacingInPixels);
            }
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

    public RecyclerView.LayoutManager getLayoutManager(GroupViewType groupViewType, Box.Type boxType) {
        RecyclerView.LayoutManager layoutManager;
        if (mIsTvHome && boxType == Box.Type.LIVETV) {
            groupViewType = GroupViewType.GRID;
        }

        switch (groupViewType) {
            case GRID:
                layoutManager = new GridLayoutManager(mContext, CompatibilityUtil.getNumberItem(mContext, boxType));
                break;
            case HORIZONTAL_SCROLL:
                layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                layoutManager.setAutoMeasureEnabled(true);
                break;
            default:
                layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                layoutManager.setAutoMeasureEnabled(true);
                break;
        }

        return layoutManager;
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

    public enum GroupViewType {
        HORIZONTAL_SCROLL, GRID
    }
}
