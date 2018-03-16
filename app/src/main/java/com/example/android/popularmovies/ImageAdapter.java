package com.example.android.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.data.PopularMoviesPreferences;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageAdapterViewHolder> {

    private List<Movie> moviesList;


    // default constructor
    public ImageAdapter() {

    }

    // create viewHolder class that extends the RecylcerView ViewHolder
    public class ImageAdapterViewHolder extends RecyclerView.ViewHolder {

        public ImageView movieImageView;

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

        final Movie movie = moviesList.get(position);
        ImageAdapterViewHolder imageAdapterViewHolder = (ImageAdapterViewHolder) holder;
        Context context = imageAdapterViewHolder.movieImageView.getContext();

        Picasso.with(context)
                .load(PopularMoviesPreferences.IMAGE_BASE_URL)
                .into(imageAdapterViewHolder.movieImageView);
//                TODO: add full poster path to URL

    }

    // override getItemCount, and set to 0 if there are no items
    @Override
    public int getItemCount() {

        if (moviesList == null) {
            return 0;
        }
        return moviesList.size();
    }

    // notify the app that data has changed to refresh the view
    public void updateMovieData(List<Movie> movieData) {
        moviesList = movieData;
        notifyDataSetChanged();
    }

}