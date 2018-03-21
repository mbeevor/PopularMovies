package com.example.android.popularmovies.tools;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.utlities.NetworkUtils;
import com.example.android.popularmovies.utlities.QueryUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by Matthew on 21/03/2018.
 */



public class GetMovieDataTask extends AsyncTask<URL, Void, List<Movie>> {

    private List<Movie> moviesList;
    private Context context;
    private AsyncTaskListener<Movie> listener;

    public GetMovieDataTask(Context context, AsyncTaskListener<Movie> listener) {

        this.context = context;
        this.listener = listener;
    }


    // show progress bar whilst AsyncTask is running
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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
    protected void onPostExecute(List<Movie> moviesList) {

            /* on completion of AsyncTask, hide the progress bar and
            * either show the movie data,
            * or an error message if there is no data
             */
        listener.onTaskComplete(moviesList);

    }
}

