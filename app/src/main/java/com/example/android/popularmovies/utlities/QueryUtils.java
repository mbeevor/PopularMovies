package com.example.android.popularmovies.utlities;

import android.text.TextUtils;

import com.example.android.popularmovies.data.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 15/03/2018.
 */

public class QueryUtils {

    private static List<Movie> extractMovies(String movieJson) {

        if (TextUtils.isEmpty(movieJson)) {
            return null;
        }

        List<Movie> movies = new ArrayList<>();

        try {

            JSONObject jsonObject = new JSONObject(movieJson);

            JSONArray results = jsonObject.getJSONArray("results");

            for (int i = 0; 1 <results.length(); i++) {
                JSONObject currentMovie = results.getJSONObject(i);

                String movieTitle = currentMovie.getString("title");
                String posterImage = currentMovie.getString("poster_path");

                Movie movie = new Movie(movieTitle, posterImage);
                movies.add(movie);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }
}
