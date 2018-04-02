package com.example.android.popularmovies.tasks;

import android.os.AsyncTask;

import com.example.android.popularmovies.model.Review;
import com.example.android.popularmovies.utlities.NetworkUtils;
import com.example.android.popularmovies.utlities.ReviewUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by Matthew on 23/03/2018.
 */

public class GetReviewDataTask extends AsyncTask<URL, Void, List<Review>> {

    public List<Review> reviewList;
    public ReviewAsyncTaskListener delegate = null;

    public GetReviewDataTask(ReviewAsyncTaskListener asyncTaskListener) {
        delegate = asyncTaskListener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Review> doInBackground(URL... urls) {

        try {

            if (urls != null) {

                URL reviewsUrl = urls[0];
                String movieId;

                movieId = NetworkUtils.getResponseFromHttpUrl(reviewsUrl);
                if (movieId != null) {

                    reviewList = ReviewUtils.getSimpleReviewQueryStringFromJson(movieId);
                    return reviewList;
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return reviewList;
    }

    @Override
    protected void onPostExecute(final List<Review> list) {

        delegate.onTaskComplete(reviewList);


    }
}
