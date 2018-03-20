package com.example.android.popularmovies;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    public List<Movie> moviesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find and assign IDs to views
        recyclerView = findViewById(R.id.recyclerview_grid);
        emptyTextView = findViewById(R.id.empty_view);
        progressBar = findViewById(R.id.progress_bar);


        // create GridLayoutManager
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(this, setGridColumns());
        recyclerView.setLayoutManager(gridLayoutManager);

        // set recyclerView to have a fixed size so that all items in the list are the same size.
        recyclerView.setHasFixedSize(true);

        // set recyclerView to image adapter
        movieListAdapter = new MovieAdapter(this, moviesList);
        recyclerView.setAdapter(movieListAdapter);

        // set on item click listener to adapter
        movieListAdapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {

                String posterImage = "poster image here";

                Intent detailActivityIntent = new Intent(getApplicationContext(), DetailActivity.class);
                detailActivityIntent.putExtra(posterImage, posterImage);
                startActivity(detailActivityIntent);
            }
        });

        // hide empty text view
        emptyTextView.setVisibility(View.INVISIBLE);

        // call loadMovieData method
        loadMovieData();
    }

    private void loadMovieData() {

        /* TODO: update this to a separate method and replace 'getPopular' with variable to be determined
        * by selection of popular or top-rated
         */
        URL getSearchUrl = NetworkUtils.buildUrl(PopularMoviesPreferences.getTopRated());
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

            URL queryUrl = params[0];
            String queryResult;

            try {

                queryResult = NetworkUtils.getResponseFromHttpUrl(queryUrl);
                return QueryUtils.getSimpleMovieQueryStringFromJson(queryResult);


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
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
