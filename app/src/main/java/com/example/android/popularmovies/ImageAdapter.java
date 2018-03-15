package com.example.android.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageAdapterViewHolder> {

    public String[] movieImageData;


    // empty default constructor
    public ImageAdapter() {

    }

    // create viewHolder class that extends the RecylcerView ViewHolder
    public class ImageAdapterViewHolder extends RecyclerView.ViewHolder {

        public final ImageView movieImageView;

        public ImageAdapterViewHolder(View view) {
            super(view);
            movieImageView = view.findViewById(R.id.movie_icon_iv);
            // TODO: add on-click listener for each item here
        }

    }

    @Override
    public ImageAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutForListItem, parent, false);
        ImageAdapterViewHolder holder = new ImageAdapterViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ImageAdapterViewHolder holder, int position) {

        String movieThumbnails = movieImageData[position];
        Uri url = Uri.parse(movieThumbnails.toString());
        Context context = holder.movieImageView.getContext();

        Picasso.with(context)
                .load(url)
                .into(holder.movieImageView);

    }

    // override getItemCount, and set to 0 if there are no items
    @Override
    public int getItemCount() {

        if (movieImageData == null) {
            return 0;
        }
        return movieImageData.length;
    }

    // notify the app that data has changed to refresh the view
    public void setMovieData(String[] movieData) {
        movieImageData = movieData;
        notifyDataSetChanged();
    }

}