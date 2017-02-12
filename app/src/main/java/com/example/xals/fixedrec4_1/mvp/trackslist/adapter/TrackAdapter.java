package com.example.xals.fixedrec4_1.mvp.trackslist.adapter;

import android.content.Context;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xals.fixedrec4_1.R;
import com.example.xals.fixedrec4_1.business.model.TrackModel;
import com.example.xals.fixedrec4_1.repository.dto.TrackDTO;
import com.example.xals.fixedrec4_1.util.Convert;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TrackAdapter extends RecyclerView.Adapter<TracksViewHolder>  {

    private Context context;
    private OnItemClickListener listener;
    private LayoutInflater inflater;

    private SortedList<TrackModel> tracks;

    public TrackAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        tracks = new SortedList<TrackModel>(TrackModel.class, new SortedListAdapterCallback<TrackModel>(this) {
            @Override
            public int compare(TrackModel o1, TrackModel o2) {
                return o1.getDateCreated().compareTo(o2.getDateCreated());
            }

            @Override
            public boolean areContentsTheSame(TrackModel oldItem, TrackModel newItem) {
                oldItem.getDateUpdated();
                newItem.getDateUpdated();
                if (oldItem.getDateUpdated() != null && newItem.getDateUpdated() != null) {
                    if (oldItem.getDateUpdated().compareTo(newItem.getDateUpdated()) == 0) {
                        return true;
                    }
                }

                return oldItem.isRunning() == newItem.isRunning();
            }

            @Override
            public boolean areItemsTheSame(TrackModel item1, TrackModel item2) {
                boolean same = TextUtils.equals(item1.getUuid(), item2.getUuid());
                Log.d("TrackAdapter", "same:" + same);
                return same;
            }
        });
    }

    @Override
    public TracksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_track_layout, parent, false);
        return new TracksViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TracksViewHolder vh, int position) {
        TrackModel model = tracks.get(position);
        vh.bind(model, listener);
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public TrackModel get(int position) {
        return tracks.get(position);
    }

    public int add(TrackModel item) {
        return tracks.add(item);
    }

    public int indexOf(TrackModel item) {
        return tracks.indexOf(item);
    }

    public void updateItem( TrackModel item) {
        tracks.updateItemAt(indexOf(item), item);
    }

    public void addAll(List<TrackModel> items) {
        tracks.beginBatchedUpdates();
        for (TrackModel item : items) {
            tracks.add(item);
        }
        tracks.endBatchedUpdates();
    }


    public interface OnItemClickListener {
        void onItemClicked(TrackModel model);
    }
}