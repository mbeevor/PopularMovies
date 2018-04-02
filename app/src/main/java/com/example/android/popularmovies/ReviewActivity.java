package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.android.popularmovies.adapters.ReviewAdapter;
import com.example.android.popularmovies.model.Review;
import com.example.android.popularmovies.tasks.GetReviewDataTask;
import com.example.android.popularmovies.tasks.ReviewAsyncTaskListener;
import com.example.android.popularmovies.utlities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 23/03/2018.
 */

public class ReviewActivity extends AppCompatActivity {

    List<Review> reviewList;
    public String movieId;
      ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        // get data from DetailActivity
        Intent intent = getIntent();
        movieId = intent.getExtras().getString("movieId");

        reviewList = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(this, reviewList);
        ListView listView = findViewById(R.id.review_listview);
        listView.setAdapter(reviewAdapter);

        loadReviewData(movieId);

    }

    public void loadReviewData(String movieId) {

        URL getReviewUrl = NetworkUtils.reviewUrl(movieId);
        Log.v("ReviewUrl is  ", getReviewUrl.toString());
        new GetReviewDataTask(new GetReviewDataListener())
                .execute(getReviewUrl);

    }

    public class GetReviewDataListener implements ReviewAsyncTaskListener {

        @Override
        public void onTaskComplete(List<Review> list) {

            reviewList = list;

            if (reviewList != null) {
                reviewAdapter.updateReviewData(list);
            }

        }
    }
}
