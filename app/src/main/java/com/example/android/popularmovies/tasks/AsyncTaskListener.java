package com.example.android.popularmovies.tasks;

import com.example.android.popularmovies.model.Movie;

import java.util.List;

/**
 * Created by Matthew on 23/03/2018.
 */

public interface AsyncTaskListener {

    void onTaskComplete(List<Movie> list);
}
