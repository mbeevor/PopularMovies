package com.example.android.popularmovies.data;

import com.example.android.popularmovies.BuildConfig;

/**
 * Created by Matthew on 15/03/2018.
 */

public class PopularMoviesPreferences {

    private static final String API_KEY = BuildConfig.API_KEY;

    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";

    private static final String POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";


    public static String getBaseUrl() {
        return  BASE_URL;
    }

    public static String getImageBaseUrl() {
        return  IMAGE_BASE_URL;
    }

    public static final String getPopular() {
        return POPULAR;
    }

    public static final String getTopRated() {
        return TOP_RATED;
    }

    public static String getApiKey() {
        return  API_KEY;
    }

}
