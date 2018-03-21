package com.example.android.popularmovies;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
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

import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.PopularMoviesPreferences;
import com.example.android.popularmovies.utlities.NetworkUtils;
import com.example.android.popularmovies.utlities.QueryUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private TextView emptyTextView;
    private ProgressBar progressBar;
    private MovieAdapter movieListAdapter;
    private List<Movie> moviesList;
    private String searchUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set default search to popular movies if not already selected
        if (searchUrl == null || searchUrl.isEmpty()) {
            searchUrl = PopularMoviesPreferences.getPopular();
        }

        // find and assign IDs to views
        recyclerView = findViewById(R.id.recyclerview_grid);
        emptyTextView = findViewById(R.id.empty_view);
        progressBar = findViewById(R.id.progress_bar);


        // create GridLayoutManager
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(this, setGridColumns());
        recyclerView.setLayoutManager(gridLayoutManager);

        // set recyclerView to have a fixed size so that all items in the list are the same size.
        recyclerView.setHasFixedSize(true);

        // set recyclerView to image adapter, passing in the OnItemClickHandler and Overriding what to do when clicked
        movieListAdapter = new MovieAdapter(getApplicationContext(), new MovieAdapter.OnItemClickHandler() {
            @Override
            public void onItemClick(View item, int position) {

                Movie movie = new Movie(moviesList.get(position).getMovieTitle(),
                        moviesList.get(position).getPosterImage(),
                        moviesList.get(position).getBackdropImage(),
                        moviesList.get(position).getMovieOverview(),
                        moviesList.get(position).getMovieReleaseDate(),
                        moviesList.get(position).getMovieRating());
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

        // hide empty text view
        emptyTextView.setVisibility(View.INVISIBLE);

        // call loadMovieData method
        loadMovieData(searchUrl);
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
            loadMovieData(searchUrl);
            Toast.makeText(this, R.string.show_most_popular, Toast.LENGTH_SHORT).show();
        }

        // update Search URL to show top rated results
        if (id == R.id.top_rated) {
            searchUrl = PopularMoviesPreferences.getTopRated();
            loadMovieData(searchUrl);
            Toast.makeText(this, R.string.show_top_rated, Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);

    }


    private void loadMovieData(String searchUrl) {

        // Return results based on onOptionsItem selected - default is popular
        URL getSearchUrl = NetworkUtils.buildUrl(searchUrl);
        new GetMovieDataTask().execute(getSearchUrl);

    }

    private void showMovieDataView() {

        // hide the error message and show the recycler view
        emptyTextView.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorView() {

        // hide the recycler view and show the error message text view
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

    public class GetMovieDataTask extends AsyncTask<URL, Void, List<Movie>> {

        // show progress bar whilst AsyncTask is running
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(URL... params) {

            try {

                if (params != null) {

                    URL queryUrl = params[0];
                    String queryResult;

                    queryResult = NetworkUtils.getResponseFromHttpUrl(queryUrl);
                    if (queryResult != null) {

                        moviesList = QueryUtils.getSimpleMovieQueryStringFromJson(queryResult);
                        return moviesList;
                    }

                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return moviesList;
        }

        @Override
        protected void onPostExecute(List<Movie> movieData) {

            /* on completion of AsyncTask, hide the progress bar and
            * either show the movie data,
            * or an error message if there is no data
             */
            progressBar.setVisibility(View.INVISIBLE);

            if (movieData != null) {
                showMovieDataView();
                movieListAdapter.updateMovieData(movieData);
            } else {
                showErrorView();
            }

        }
    }

}