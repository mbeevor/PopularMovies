package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.PopularMoviesPreferences;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    public List<Movie> moviesList;

    // default constructor
    public MovieAdapter(List<Movie> objects) {

        moviesList = objects;

    }

    // create viewHolder class that extends the RecylcerView ViewHolder
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder {

        public ImageView posterImageView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            posterImageView = view.findViewById(R.id.movie_icon_iv);
            // TODO: add on-click listener for each item here
        }

    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutForListItem, parent, false);
        MovieAdapterViewHolder holder = new MovieAdapterViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {

        Movie movie = moviesList.get(position);
        String moviePoster = movie.getPosterImage();
        MovieAdapterViewHolder imageAdapterViewHolder = holder;
        Context context = imageAdapterViewHolder.posterImageView.getContext();

        String posterImageUrl = PopularMoviesPreferences.getImageBaseUrl() + moviePoster;

        Picasso.with(context)
                .load(posterImageUrl)
                .into(imageAdapterViewHolder.posterImageView);

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