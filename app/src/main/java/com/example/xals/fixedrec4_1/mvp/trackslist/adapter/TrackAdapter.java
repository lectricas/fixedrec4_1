package com.example.xals.fixedrec4_1.mvp.trackslist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xals.fixedrec4_1.R;
import com.example.xals.fixedrec4_1.business.dto.TrackDTO;
import com.example.xals.fixedrec4_1.util.Convert;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder>  {

    private Context context;
    private List<TrackDTO> tracks;
    private OnItemClickListener listener;
    private LayoutInflater inflater;

    public TrackAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.tracks = new ArrayList<>();
    }

    public void add(TrackDTO track) {
        if (!tracks.contains(track)) {
            tracks.add(track);
        }
        notifyDataSetChanged();
    }

    public void update(TrackDTO track) {
        tracks.remove(track);
        tracks.add(track);
        notifyDataSetChanged();
    }

    public void setTracks(List<TrackDTO> tracks) {
        this.tracks.clear();
        this.tracks = tracks;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_track_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        TrackDTO trackDTO = tracks.get(position);
        vh.name.setText(trackDTO.getUuid());
        if (trackDTO.isRunning()){
            vh.thumbnail.setImageResource(R.mipmap.ic_launcher);
            vh.distanceTimeTv.setText(context.getString(R.string.recording_now));
            blink(vh.thumbnail);
        }else {
            vh.thumbnail.setAnimation(null);
            String distance = Convert.getKmIfNeeded(trackDTO.getDistance(), context);
            String totalTime = Convert.getDateDiff(trackDTO.getDateCreated(),
                    trackDTO.getDateClosed(), context);
            vh.distanceTimeTv.setText(distance + "(" + totalTime + ")");
//            switch (trackDTO.getType()) {
//                case StrStore.TYPE_CAR:
//                    vh.thumbnail.setImageResource(R.drawable.ic_car);
//                    break;
//                case StrStore.TYPE_BIKE:
//                    vh.thumbnail.setImageResource(R.drawable.ic_bike);
//                    break;
//                case StrStore.TYPE_FOOT:
//                    vh.thumbnail.setImageResource(R.drawable.ic_walk);
//                    break;
//                default:
//                    vh.thumbnail.setImageResource(R.drawable.ic_navigation);
//                    break;
//            }
        }

//        vh.dateEnd.setText(makeAgo(trackDTO.getDateCreated()));
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }


    public void blink(ImageView image){
        Animation animation1 = AnimationUtils.loadAnimation(context, R.anim.blink_recording);
        image.startAnimation(animation1);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.name) TextView name;
        @Bind(R.id.timeEnd) TextView dateEnd;
        @Bind(R.id.thumbnail) ImageView thumbnail;
        @Bind(R.id.distance_and_time) TextView distanceTimeTv;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClicked(tracks.get(getLayoutPosition()));
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(TrackDTO trackDTO);
    }

}