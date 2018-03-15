package com.example.android.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.data.PopularMoviesPreferences;
import com.example.android.popularmovies.utlities.NetworkUtils;

import java.net.URL;


public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private TextView emptyTextView;
    private ProgressBar progressBar;
    private ImageAdapter imageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find and assign IDs to views
        recyclerView = findViewById(R.id.recyclerview_grid);
        emptyTextView = findViewById(R.id.empty_view);
        progressBar = findViewById(R.id.progress_bar);


        // create GridLayoutManager with default span size and reverselayout to false
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        // set recyclerView to have a fixed size so that all items in the list are the same size.
        recyclerView.setHasFixedSize(true);

        // set recyclerView to image adapter
        imageAdapter = new ImageAdapter();
        recyclerView.setAdapter(imageAdapter);

        loadMovieData();
    }


    private void loadMovieData() {

        // adjust view to show recyclerview
        showMovieDataView();

        String apiKey = PopularMoviesPreferences.getApiKey();
        new GetMovieDataTask().execute(apiKey);

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

    public class GetMovieDataTask extends AsyncTask<String, Void, String[]> {

        // show progress bar whilst AsyncTask is running
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... strings) {

            if (strings.length == 0) {
                return null;
            }

            String movie = strings[0];
            URL queryUrl = NetworkUtils.buildUrl(movie);

            try {

                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(queryUrl);
            }
        }

        @Override
        protected void onPostExecute(String[] movieData) {

            /* on completion of AsyncTask, hide the progress bar and
            * either show the movie data,
            * or an error message if there is no data
             */
            progressBar.setVisibility(View.INVISIBLE);

            if (movieData != null) {
                showMovieDataView();
                imageAdapter.setMovieData(movieData);
            } else {
                showErrorView();
            }

        }
    }
}
