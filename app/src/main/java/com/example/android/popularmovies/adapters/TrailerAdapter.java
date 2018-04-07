package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.Review;
import com.example.android.popularmovies.model.Trailer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbeev on 06/04/2018.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder>  {

    private Context context;
    private List<Trailer> trailerList;
    public OnTrailerClickHandler onTrailerClickHandler;


    public interface OnTrailerClickHandler {

        void OnTrailerClick(View item, int position);
    }

    public TrailerAdapter(Context thisContext, OnTrailerClickHandler handler) {

        context = thisContext;
        onTrailerClickHandler = handler;
        setHasStableIds(true);
    }


    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutForReview = R.layout.trailer_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutForReview, parent, false);
        TrailerAdapterViewHolder holder = new TrailerAdapterViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(TrailerAdapterViewHolder holder, int position) {

        if (trailerList != null) {

            // get the data model based on position
            Trailer trailer = trailerList.get(position);

            // get data and assign to view holder
            String trailerNameString = trailer.getTrailerName();

            TrailerAdapterViewHolder trailerAdapterViewHolder = holder;
            trailerAdapterViewHolder.trailerName.setText(trailerNameString);
        }

    }

    @Override
    public int getItemCount() {

        if (trailerList == null) {
            return 0;
        } else {
            return trailerList.size();
        }
    }

    // notify the app that data has changed to refresh the view
    public void updateTrailerData(List<Trailer> trailerData) {

        if (trailerData != null )
            trailerList = new ArrayList<>(trailerData);
        notifyDataSetChanged();
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView trailerName;

        public TrailerAdapterViewHolder(View itemView) {

            super(itemView);
            trailerName = itemView.findViewById(R.id.trailer_name);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            if (trailerList != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onTrailerClickHandler.OnTrailerClick(view, position);
                }
            }

        }
    }
}