package com.example.android.popularmovies.tasks;

import com.example.android.popularmovies.model.Review;

import java.util.List;

/**
 * Created by Matthew on 23/03/2018.
 */

public interface ReviewAsyncTaskListener {

    void onTaskComplete(List<Review> list);


}
