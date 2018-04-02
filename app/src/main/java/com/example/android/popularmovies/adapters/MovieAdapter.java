package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.model.PopularMoviesPreferences;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private List<Movie> moviesList;
    private Context context;
    private OnItemClickHandler clickHandler;

    // interface for Handler
    public interface OnItemClickHandler {
        void onItemClick(View item, int position);
    }

    // default constructor with click listener
    public MovieAdapter(Context thisContext, OnItemClickHandler handler) {

        clickHandler = handler;
        context = thisContext;
        /* Returns true if this adapter publishes a unique long value that can act as a key for the item at a given position in the data set. If that item is relocated in the data set, the ID returned for that item should be the same.
        *@return true if this adapter's items have stable IDs
        */
        setHasStableIds(true);

    }

    @Override
    public long getItemId(int position) {
        return position;
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

        if (moviesList != null) {

            // get the data model based on position
            Movie movie = moviesList.get(position);

            // set item views based on views and data model
            String moviePoster = movie.getPosterImage();
            MovieAdapterViewHolder movieAdapterViewHolder = holder;
            Context context = movieAdapterViewHolder.posterImageView.getContext();

            String posterImageUrl = PopularMoviesPreferences.getImageBaseUrl() + moviePoster;

            Picasso.with(context)
                    .load(posterImageUrl)
                    .into(movieAdapterViewHolder.posterImageView);

        }
    }

    // override getItemCount, and set to 0 if there are no items
    @Override
    public int getItemCount() {

        if (moviesList == null) {
            return 0;
        } else {
            return moviesList.size();
        }
    }

    // notify the app that data has changed to refresh the view
    public void updateMovieData(List<Movie> movieData) {

        if (movieData != null )
        moviesList = new ArrayList<>(movieData);
        notifyDataSetChanged();
    }

    // create viewHolder class that extends the RecyclerView ViewHolder
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView posterImageView;

        public MovieAdapterViewHolder(final View itemView) {

            super(itemView);
            posterImageView = itemView.findViewById(R.id.movie_icon_iv);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

            if (moviesList != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    clickHandler.onItemClick(view, position);
                }
            }


        }
    }
}


