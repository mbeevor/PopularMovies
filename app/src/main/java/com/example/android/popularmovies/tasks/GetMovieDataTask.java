package com.example.android.popularmovies.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.utlities.NetworkUtils;
import com.example.android.popularmovies.utlities.QueryUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by Matthew on 23/03/2018.
 */

public class GetMovieDataTask extends AsyncTask<URL, Void, List<Movie>> {


    public List<Movie> moviesList;
    public AsyncTaskListener delegate = null;

    public GetMovieDataTask(AsyncTaskListener asyncTaskListener) {
        delegate = asyncTaskListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Movie> doInBackground(URL... urls) {

        try {

            if (urls != null) {

                URL queryUrl = urls[0];
                String typeOfQuery;

                typeOfQuery = NetworkUtils.getResponseFromHttpUrl(queryUrl);
                if (typeOfQuery != null) {

                    moviesList = QueryUtils.getSimpleMovieQueryStringFromJson(typeOfQuery);
                    return moviesList;
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return moviesList;
    }

    @Override
    protected void onPostExecute(final List<Movie> list) {

        delegate.onTaskComplete(moviesList);


    }
}
