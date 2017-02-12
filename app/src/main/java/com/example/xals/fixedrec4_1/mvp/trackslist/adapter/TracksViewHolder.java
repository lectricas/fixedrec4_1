package com.example.xals.fixedrec4_1.mvp.trackslist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xals.fixedrec4_1.R;
import com.example.xals.fixedrec4_1.business.model.TrackModel;
import com.example.xals.fixedrec4_1.repository.dto.TrackDTO;
import com.example.xals.fixedrec4_1.util.Convert;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TracksViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.comment) TextView dateEnd;
    @BindView(R.id.thumbnail)
    ImageView thumbnail;
    @BindView(R.id.distance_and_time)
    TextView distanceTimeTv;

    public TracksViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(TrackModel model, TrackAdapter.OnItemClickListener listener) {
        if (model.isRunning()){
            name.setText(Convert.formatDateAndTime(model.getDateCreated()));
            thumbnail.setImageResource(R.drawable.ic_action_record);
            distanceTimeTv.setText(itemView.getContext().getString(R.string.recording_now));
            blink(thumbnail);
        }else {
            name.setText(Convert.formatDateAndTime(model.getDateClosed()));
            thumbnail.setImageResource(R.drawable.ic_satellite);
            thumbnail.setAnimation(null);
            String distance = Convert.getKmIfNeeded(model.getDistance(), itemView.getContext());
            String totalTime = Convert.getDateDiff(model.getDateCreated(),
                    model.getDateClosed(), itemView.getContext());
            distanceTimeTv.setText(distance + "(" + totalTime + ")");
//            switch (model.getType()) {
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

//        vh.dateEnd.setText(makeAgo(model.getDateCreated()));

        itemView.setOnClickListener(v -> listener.onItemClicked(model));
    }

    public void blink(ImageView image){
        Animation animation1 = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.blink_recording);
        image.startAnimation(animation1);
    }
}
