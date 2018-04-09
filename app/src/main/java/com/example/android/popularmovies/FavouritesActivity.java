package com.example.android.popularmovies;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.adapters.MovieCursorAdapter;
import com.example.android.popularmovies.data.MovieContract;

/**
 * Created by Matthew on 09/04/2018.
 */

public class FavouritesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView recyclerView;
    private TextView emptyTextView;
    private ProgressBar progressBar;
    private static final int MOVIE_LOADER = 0;
    MovieCursorAdapter movieCursorAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.favourites);

        // find and assign IDs to views
        recyclerView = findViewById(R.id.recyclerview_grid);
        emptyTextView = findViewById(R.id.empty_view);
        progressBar = findViewById(R.id.progress_bar);

        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(this, setGridColumns());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        MovieCursorAdapter movieCursorAdapter = new MovieCursorAdapter(this, null);
        recyclerView.setAdapter(movieCursorAdapter);

        getLoaderManager().initLoader(MOVIE_LOADER, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] projection = {
                MovieContract.MovieEntry._ID,
                MovieContract.MovieEntry.MOVIE_TITLE,
                MovieContract.MovieEntry.POSTER_IMAGE,
                MovieContract.MovieEntry.MOVIE_ID,
                MovieContract.MovieEntry.BACKDROP_IMAGE,
                MovieContract.MovieEntry.MOVIE_OVERVIEW,
                MovieContract.MovieEntry.MOVIE_RELEASE_DATE,
                MovieContract.MovieEntry.MOVIE_RATING

        };
        return new CursorLoader(this, MovieContract.MovieEntry.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        movieCursorAdapter.swapCursor(cursor);
        if (cursor != null) {
            showMovieDataView();
        } else {
            showErrorView();
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        movieCursorAdapter.swapCursor(null);

    }



    private void showMovieDataView() {

        // hide the error message and show the recycler view
        progressBar.setVisibility(View.INVISIBLE);
        emptyTextView.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorView() {

        // hide the recycler view and show the error message text view
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        emptyTextView.setVisibility(View.VISIBLE);
    }

    // method to calculate size of Grid based on device configuration
    public int setGridColumns() {

        int gridColumns = 0;

        switch (getResources().getConfiguration().orientation) {

            case Configuration.ORIENTATION_PORTRAIT:
                gridColumns = 2;
                break;

            case Configuration.ORIENTATION_LANDSCAPE:
                gridColumns = 4;
                break;
        }

        return gridColumns;
    }
}
