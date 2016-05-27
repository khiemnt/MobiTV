package com.viettel.vpmt.mobiletv.screen.channeldetail.fragment.schedule;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.log.Logger;
import com.viettel.vpmt.mobiletv.common.util.DeviceUtils;
import com.viettel.vpmt.mobiletv.network.dto.ChannelSchedule;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

/**
 * RecyclerView's adapter for Channel schedule
 * Created by neo on 3/29/2016.
 */
public class ChannelScheduleAdapter extends RecyclerView.Adapter<ChannelScheduleAdapter.MyViewHolder> {
    private List<ChannelSchedule> mSchedules;
    private Context mContext;
    private OnItemAction mOnItemAction;

    public ChannelScheduleAdapter(Context context, List<ChannelSchedule> schedules,
                                  OnItemAction onItemAction) {
        mSchedules = schedules;
        mContext = context;
        mOnItemAction = onItemAction;

        // Set playing program
        int currentIdx = getPresentPosition();
        if (currentIdx >= 0) {
            getItem(currentIdx).setPlaying(true);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_channel_schedule, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ChannelSchedule schedule = getItem(position);

        holder.mRoot.setSelected(schedule.isPlaying());
        holder.mTitleTv.setText(schedule.getName());
        holder.mTimeTv.setText(schedule.getExtractedBeginTime());

        int actionIconId;
        switch (schedule.getState()) {
            case PAST:
                actionIconId = schedule.isReplay() ? R.drawable.ic_play_circle_outline : android.R.color.transparent;
                break;
            case PRESENT:
                actionIconId = R.drawable.ic_live;
                break;
            case FUTURE:
                actionIconId = R.drawable.ic_overflow;
                break;
            case OTHER:
                actionIconId = android.R.color.transparent;
                break;
            default:
                actionIconId = android.R.color.transparent;
                break;
        }
        holder.mActionIv.setImageResource(actionIconId);

        holder.mActionIv.setOnClickListener(new ActionButtonClicked(schedule, holder.mActionIv, position));
    }

    @Override
    public int getItemCount() {
        return mSchedules.size();
    }

    public ChannelSchedule getItem(int position) {
        return mSchedules.get(position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitleTv;
        public TextView mTimeTv;
        public ImageView mActionIv;
        public View mRoot;

        public MyViewHolder(View itemView) {
            super(itemView);
            mRoot = itemView;
            mTitleTv = (TextView) itemView.findViewById(R.id.item_channel_schedule_title_tv);
            mTimeTv = (TextView) itemView.findViewById(R.id.item_channel_schedule_time_tv);
            mActionIv = (ImageView) itemView.findViewById(R.id.item_channel_schedule_action_iv);
            // Make this view clickable
            itemView.setClickable(true);
        }
    }

    public void setSchedules(List<ChannelSchedule> schedules) {
        mSchedules = schedules;
        playPresentProgram();
    }

    /**
     * Action button click listener
     */
    private class ActionButtonClicked implements View.OnClickListener {
        private ChannelSchedule mSchedule;
        private ImageView mActionButton;
        private int mPosition;

        public ActionButtonClicked(ChannelSchedule schedule, ImageView actionButton, int position) {
            mSchedule = schedule;
            mActionButton = actionButton;
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            switch (mSchedule.getState()) {
                case FUTURE:
                    showNotificationPopup(mActionButton, mSchedule);
                    break;
                case PRESENT:
                    playPresentProgram();
                    break;
                case PAST:
                    if (mSchedule.isReplay()) {
                        playPastProgram(mSchedule.getId(), mPosition);
                    }
                    break;
                default:
                    break;
            }
        }

        /**
         * Get stream URL and Play past program
         */
        private void playPastProgram(String programId, int position) {
            if (mOnItemAction != null) {
                mOnItemAction.playProgram(programId);
            }

            // Current playing index
            int currentPlayingIndex = getPlayingPosition();

            // Change to clicked index
            mSchedules.get(currentPlayingIndex).setPlaying(false);
            mSchedules.get(position).setPlaying(true);
            notifyDataSetChanged();
        }

        /**
         * Show popup to left of action button
         */
        private void showNotificationPopup(View anchor, final ChannelSchedule schedule) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View popupView = layoutInflater.inflate(R.layout.popup_notification, null);

            // Toggle checkbox
            ((CheckBox) popupView.findViewById(R.id.popup_notification_cb))
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            // TODO maybe use self push
//                            mOnItemAction.notifyChannel(schedule.getId());
                        }
                    });

            final PopupWindow popupWindow = new PopupWindow(popupView,
                    WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);

            popupWindow.setTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(false);
            // This to dismiss popup when click outside
            popupWindow.setBackgroundDrawable(new BitmapDrawable());

            // Location of anchor in screen
            int[] location = new int[2];
            anchor.getLocationOnScreen(location);
            int screenWidth = DeviceUtils.getDeviceSize((Activity) mContext).x;

            popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int popupWidth = popupView.getMeasuredWidth();

            int xOff = screenWidth - popupWidth - anchor.getWidth();
            int yOff = location[1];

            popupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, xOff, yOff);
        }
    }

    /**
     * Play current present program
     */
    public void playPresentProgram() {
        int presentIdx = getPresentPosition();
        if (presentIdx < 0) {
            return;
        }
        getItem(getPlayingPosition()).setPlaying(false);
        getItem(presentIdx).setPlaying(true);
        notifyItemChanged(presentIdx);
        if (mOnItemAction != null) {
            mOnItemAction.playPresentProgram();
        }
    }

    /**
     * Move playing program to next
     */
    public void moveNextProgram() {
        int currentIdx = getPresentPosition();
        if (currentIdx < 0) {
            return;
        }
        mSchedules.get(currentIdx).setState(ChannelSchedule.State.OTHER);

        if (currentIdx < mSchedules.size() - 1) {
            getItem(currentIdx + 1).setState(ChannelSchedule.State.PRESENT);
            if (getItem(currentIdx).isPlaying()) {
                getItem(currentIdx + 1).setPlaying(true);
            }
        }

        notifyDataSetChanged();
    }

    /**
     * Get current present program position
     */
    public int getPresentPosition() {
        int presentIdx = -1;
        int size = mSchedules.size();

        for (int i = 0; i < size; i++) {
            ChannelSchedule schedule = getItem(i);
            if (schedule.getState() == ChannelSchedule.State.PRESENT) {
                presentIdx = i;
                break;
            }
        }
        return presentIdx;
    }

    /**
     * Get current playing program position
     */
    public int getPlayingPosition() {
        int playingIdx = 0;
        int size = mSchedules.size();

        for (int i = 0; i < size; i++) {
            ChannelSchedule schedule = mSchedules.get(i);
            if (schedule.isPlaying()) {
                playingIdx = i;
                break;
            }
        }

        return playingIdx;
    }

    /**
     * Acion button clicked listener
     */
    public interface OnItemAction {
        void playProgram(String programId);

        void playPresentProgram();
    }
}
