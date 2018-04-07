package com.example.android.popularmovies.tasks;

import android.os.AsyncTask;

import com.example.android.popularmovies.model.Trailer;
import com.example.android.popularmovies.utlities.NetworkUtils;
import com.example.android.popularmovies.utlities.TrailerUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by mbeev on 06/04/2018.
 */

public class GetTrailerDataTask extends AsyncTask<URL, Void, List<Trailer>> {

    public List<Trailer> trailerList;
    public TrailerAsyncTaskListener delegate = null;

    public GetTrailerDataTask(TrailerAsyncTaskListener asyncTaskListener) {
        delegate = asyncTaskListener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Trailer> doInBackground(URL... urls) {

        try {

            if (urls != null) {

                URL trailerUrl = urls[0];
                String movieId;

                movieId = NetworkUtils.getResponseFromHttpUrl(trailerUrl);
                if (movieId != null) {

                    trailerList = TrailerUtils.getSimpleReviewQueryStringFromJson(movieId);
                    return trailerList;
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return trailerList;
    }

    @Override
    protected void onPostExecute(final List<Trailer> list) {

        delegate.onTaskComplete(trailerList);


    }
}
