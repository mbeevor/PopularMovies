package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.MovieContract;
import com.example.android.popularmovies.model.PopularMoviesPreferences;
import com.squareup.picasso.Picasso;

/**
 * Created by Matthew on 09/04/2018.
 */

public class MovieCursorAdapter extends CursorRecyclerViewAdapter<MovieCursorAdapter.ViewHolder> {


    public MovieCursorAdapter(Context thisContext, Cursor data) {
        super(thisContext, data);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView movieIconIv;

        public ViewHolder(View itemView) {

            super(itemView);
            movieIconIv = itemView.findViewById(R.id.movie_icon_iv);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(layoutForListItem, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {

        ViewHolder viewHolder = holder;
        Context context = viewHolder.movieIconIv.getContext();

        // extract image view from cursor
        String moviePoster = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.POSTER_IMAGE));
        String posterImageUrl = PopularMoviesPreferences.getImageBaseUrl() + moviePoster;

        // populate gridlayout
        Picasso.with(context)
                .load(posterImageUrl)
                .into(viewHolder.movieIconIv);
    }
}
