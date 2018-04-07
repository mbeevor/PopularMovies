package com.example.android.popularmovies.tasks;

import com.example.android.popularmovies.model.Trailer;

import java.util.List;

/**
 * Created by mbeev on 06/04/2018.
 */

public interface TrailerAsyncTaskListener {

    void onTaskComplete(List<Trailer> list);

}
