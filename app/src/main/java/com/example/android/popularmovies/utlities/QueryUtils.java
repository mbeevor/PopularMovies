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

    private static final String TITLE = "title";
    private static final String RESULTS = "results";
    private static final String POSTER_PATH = "poster_path";

    public static List<Movie> getSimpleMovieQueryStringFromJson(String movieJson) {

        if (TextUtils.isEmpty(movieJson)) {
            return null;
        }

        // create list of movies using the JSONArray and the movie constructor
        List<Movie> movies = new ArrayList<>();

        try {

            JSONObject moviesJson = new JSONObject(movieJson);

            JSONArray moviesArray = moviesJson.getJSONArray(RESULTS);

            for (int i = 0; 1 <moviesArray.length(); i++) {
                JSONObject currentMovie = moviesArray.getJSONObject(i);

                String movieTitle = currentMovie.getString(TITLE);
                String posterImage = currentMovie.getString(POSTER_PATH);

                Movie movie = new Movie(movieTitle, posterImage);
                movies.add(movie);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }
}
