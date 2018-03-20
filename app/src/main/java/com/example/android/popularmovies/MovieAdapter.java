package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.PopularMoviesPreferences;
import com.example.android.popularmovies.utlities.QueryUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    public List<Movie> moviesList;
    private Context context;
    private OnItemClickListener clickListener;


    // interface for listener
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        clickListener = listener;
    }

    // default constructor with click listener
    public MovieAdapter(Context currentContext, List<Movie> movies) {

        context = currentContext;
        moviesList = movies;

    }

    // method to access context object in recyclerview
    private Context getContext() {
        return context;
    }

    // create viewHolder class that extends the RecylcerView ViewHolder
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder {

        public TextView movieTitleView;
        public ImageView posterImageView;

        public MovieAdapterViewHolder(final View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.movie_icon_iv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            clickListener.onItemClick(itemView, position);
                        }
                    }
                }
            });
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