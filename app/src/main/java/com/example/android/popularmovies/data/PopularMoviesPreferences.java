package com.example.android.popularmovies.data;

/**
 * Created by Matthew on 15/03/2018.
 */

public class PopularMoviesPreferences {


    public static final String API_KEY = "4f1608301436c9fb197df0d1767fc7b1";
    public static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    public static final String POPULAR = "popular?api_key=";
    public static final String TOP_RATED = "top_rated?api_key=";
    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";

    public static String getBaseUrl() {
        return  BASE_URL;
    }

    public static final String getPopular() {
        return POPULAR;
    }

    public static String getApiKey() {
        return  API_KEY;
    }

}
