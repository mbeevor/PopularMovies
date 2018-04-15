package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.MovieContract;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.model.PopularMoviesPreferences;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Matthew on 09/04/2018.
 */

public class MovieCursorAdapter extends CursorRecyclerViewAdapter<MovieCursorAdapter.MovieCursorViewHolder> {

    private OnItemClickHandler clickHandler;

    // interface for Handler
    public interface OnItemClickHandler {
        void onItemClick(View item, int position);
    }

    public MovieCursorAdapter(Cursor data, OnItemClickHandler onItemClickHandler) {
        super(data);
        clickHandler = onItemClickHandler;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public MovieCursorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(layoutForListItem, parent, false);
        MovieCursorViewHolder viewHolder = new MovieCursorViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieCursorViewHolder holder, Cursor cursor) {

        MovieCursorViewHolder viewHolder = holder;
        Context context = viewHolder.movieIconIv.getContext();

        // extract image view from cursor
        String moviePoster = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.POSTER_IMAGE));
        String posterImageUrl = PopularMoviesPreferences.getImageBaseUrl() + moviePoster;

        // populate gridlayout
        Picasso.with(context)
                .load(posterImageUrl)
                .into(viewHolder.movieIconIv);
    }

    @Override
    public void swapCursor(Cursor newCursor) {
        super.swapCursor(newCursor);
    }

    public class MovieCursorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView movieIconIv;

        public MovieCursorViewHolder(final View itemView) {

            super(itemView);
            movieIconIv = itemView.findViewById(R.id.movie_icon_iv);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                clickHandler.onItemClick(view, position);
            }
        }


    }
}
