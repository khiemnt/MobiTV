package com.viettel.vpmt.mobiletv.screen.channeldetail.fragment.schedule;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.network.dto.ChannelSchedule;
import com.viettel.vpmt.mobiletv.screen.channeldetail.fragment.ChannelDetailFragmentPresenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * RecyclerView's adapter for Channel schedule
 * Created by neo on 3/29/2016.
 */
public class ChannelScheduleAdapter extends RecyclerView.Adapter<ChannelScheduleAdapter.MyViewHolder> {
    private List<ChannelSchedule> mSchedules;
    private Context mContext;
    private ChannelDetailFragmentPresenter mChannelDetailFragmentPresenter;

    public ChannelScheduleAdapter(Context context, List<ChannelSchedule> schedules,
                                  ChannelDetailFragmentPresenter channelDetailFragmentPresenter) {
        mSchedules = schedules;
        mContext = context;
        mChannelDetailFragmentPresenter = channelDetailFragmentPresenter;
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
                actionIconId = R.drawable.ic_play_circle_outline_small;
                break;
            case PRESENT:
                actionIconId = R.drawable.ic_live;
                break;
            case FUTURE:
                actionIconId = R.drawable.ic_overflow;
                break;
            default:
                actionIconId = R.drawable.ic_play_circle_outline_small;
                break;
        }
        holder.mActionIv.setImageResource(actionIconId);

        holder.mActionIv.setOnClickListener(new ActionButtonClicked(schedule));
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
        }
    }

    /**
     * Action button click listener
     */
    private class ActionButtonClicked implements View.OnClickListener{
        ChannelSchedule mSchedule;

        public ActionButtonClicked(ChannelSchedule schedule) {
            mSchedule = schedule;
        }

        @Override
        public void onClick(View v) {
            switch (mSchedule.getState()) {
                case FUTURE:// TODO add notification
                    break;
                case PAST:
                    playPastProgram(mSchedule.getId());
                    break;
                default:
                    break;
            }
        }

        //
        private void playPastProgram(String programId) {
            mChannelDetailFragmentPresenter.playProgram(programId);
        }
    }
}
