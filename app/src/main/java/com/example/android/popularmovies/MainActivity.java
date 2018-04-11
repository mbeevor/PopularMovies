package com.example.android.popularmovies;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.adapters.MovieAdapter;
import com.example.android.popularmovies.adapters.MovieCursorAdapter;
import com.example.android.popularmovies.data.MovieContract;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.model.PopularMoviesPreferences;
import com.example.android.popularmovies.tasks.AsyncTaskListener;
import com.example.android.popularmovies.tasks.GetMovieDataTask;
import com.example.android.popularmovies.utlities.NetworkUtils;

import java.net.URL;
import java.util.List;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    private RecyclerView recyclerView;
    private TextView emptyTextView;
    private ProgressBar progressBar;
    private MovieAdapter movieListAdapter;
    public List<Movie> moviesList;
    private String searchUrl;
    public String appTitle;
    private static final int MOVIE_LOADER = 0;
    private MovieCursorAdapter movieCursorAdapter;


    @Override
    // save SearchUrl to ensure user selected results are shown
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("searchUrl", searchUrl);
        savedInstanceState.putString("appTitle", appTitle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // keep selected search option, or default to popular
        if (savedInstanceState != null) {
            searchUrl = savedInstanceState.getString("searchUrl");
            appTitle = savedInstanceState.getString("appTitle");

        } else {
            searchUrl = PopularMoviesPreferences.getPopular();
            appTitle = getString(R.string.popular);
        }

        // assign title of app according to selection
        setTitle(appTitle);

        // find and assign IDs to views
        recyclerView = findViewById(R.id.recyclerview_grid);
        emptyTextView = findViewById(R.id.empty_view);
        progressBar = findViewById(R.id.progress_bar);


        // create GridLayoutManager
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(this, setGridColumns());
        recyclerView.setLayoutManager(gridLayoutManager);

        // set recyclerView to have a fixed size so that all items in the list are the same size.
        recyclerView.setHasFixedSize(true);

        // check searchUrl is not equal to null, and create new movie adapter
        if (searchUrl != null) {
            // set recyclerView to image adapter, passing in the OnItemClickHandler and Overriding what to do when clicked
            movieListAdapter = new MovieAdapter
                    (getApplicationContext(), new MovieAdapter.OnItemClickHandler() {

                        @Override
                        public void onItemClick(View item, int position) {

                            Movie moviePosition = moviesList.get(position);
                            Movie movie = new Movie(
                                    moviePosition.getMovieTitle(),
                                    moviePosition.getPosterImage(),
                                    moviePosition.getMovieId(),
                                    moviePosition.getBackdropImage(),
                                    moviePosition.getMovieOverview(),
                                    moviePosition.getMovieReleaseDate(),
                                    moviePosition.getMovieRating());
                            Intent detailActivityIntent = new Intent(getApplicationContext(), DetailActivity.class);
                            detailActivityIntent.putExtra("movie", movie);
                            startActivity(detailActivityIntent);

                        }
                    });
            recyclerView.setAdapter(movieListAdapter);

            recyclerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadMovieData(searchUrl);
                }
            });

            // hide empty text view and show progress bar
            progressBar.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.INVISIBLE);

            loadMovieData(searchUrl);

        } else {
            // launch loader manager to display favourites
            getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_preference, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // update Search URL to show most popular results
        if (id == R.id.by_popular) {
            searchUrl = PopularMoviesPreferences.getPopular();
            appTitle = getString(R.string.popular);
            setTitle(appTitle);
            loadMovieData(searchUrl);
            Toast.makeText(this, R.string.show_most_popular, Toast.LENGTH_SHORT).show();
        }

        // update Search URL to show top rated results
        if (id == R.id.top_rated) {
            searchUrl = PopularMoviesPreferences.getTopRated();
            appTitle = getString(R.string.top_rated);
            setTitle(appTitle);
            loadMovieData(searchUrl);
            Toast.makeText(this, R.string.show_top_rated, Toast.LENGTH_SHORT).show();

        }

        // update Search URL to null and launch new loader
        if (id == R.id.favourites) {
            searchUrl = null;
            appTitle = getString(R.string.favourites);
            setTitle(appTitle);
            getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        }

        return super.onOptionsItemSelected(item);

    }


    private void loadMovieData(String searchUrl) {

        // Return results based on onOptionsItem selected - default is popular
        URL getSearchUrl = NetworkUtils.queryUrl(searchUrl);
        new GetMovieDataTask(new GetMovieDataListener())
                .execute(getSearchUrl);

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

    public class GetMovieDataListener implements AsyncTaskListener {

        @Override
        public void onTaskComplete(List<Movie> list) {

            moviesList = list;

            if (moviesList != null) {
                showMovieDataView();
                movieListAdapter.updateMovieData(moviesList);
            } else {
                showErrorView();
            }


        }
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

        if (movieCursorAdapter == null) {
            movieCursorAdapter = new MovieCursorAdapter(cursor);
            recyclerView.setAdapter(movieCursorAdapter);
            showMovieDataView();
        } else {
            showErrorView();
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        movieCursorAdapter = null;
        movieCursorAdapter.swapCursor(null);

    }


}