package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

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

    private List<Review> reviewsList;
    private ListView listView;
    private TextView noReviewView;
    public String movieId;
    private ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        // assign views to IDs
        listView = findViewById(R.id.review_listview);
        noReviewView = findViewById(R.id.no_reviews_tv);

        // get data from DetailActivity
        Intent intent = getIntent();
        movieId = intent.getExtras().getString("movieId");

        reviewsList = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(this, reviewsList);
        listView.setAdapter(reviewAdapter);

        loadReviewData(movieId);

    }

    public void loadReviewData(String movieId) {

        URL getReviewUrl = NetworkUtils.reviewUrl(movieId);
        new GetReviewDataTask(new GetReviewDataListener())
                .execute(getReviewUrl);

    }

    private void showReviews() {

        // hide the error message and show the list view
        noReviewView.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
    }

    private void showNoReviews() {

        // show the error message and hide the list view
        noReviewView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.INVISIBLE);
    }

    public class GetReviewDataListener implements ReviewAsyncTaskListener {

        @Override
        public void onTaskComplete(List<Review> list) {

            reviewsList = list;

            // add reviews to adapter
            if (reviewsList != null) {
                reviewAdapter.addAll(reviewsList);
            }

            // if adapter is empty, hide and show error view
            if (reviewAdapter.getCount() == 0) {
                showNoReviews();
            } else {
                showReviews();
            }


        }
    }
}
