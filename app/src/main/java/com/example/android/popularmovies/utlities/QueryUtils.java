package com.example.android.popularmovies.utlities;

import android.text.TextUtils;

import com.example.android.popularmovies.model.Movie;

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
    private static final String MOVIE_ID = "id";
    private static final String BACKDROP_PATH = "backdrop_path";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";
    private static final String VOTE_AVERAGE = "vote_average";

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
                String movieId = currentMovie.getString(MOVIE_ID);
                String backdropImage = currentMovie.getString(BACKDROP_PATH);
                String movieOverview = currentMovie.getString(OVERVIEW);
                String movieReleaseDate = currentMovie.getString(RELEASE_DATE);
                String movieRating = currentMovie.getString(VOTE_AVERAGE);

                Movie movie = new Movie(movieTitle, posterImage, movieId, backdropImage, movieOverview, movieReleaseDate, movieRating);
                movies.add(movie);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }
}
