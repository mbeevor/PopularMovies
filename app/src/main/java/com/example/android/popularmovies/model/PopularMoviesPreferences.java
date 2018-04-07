package com.example.android.popularmovies.model;

import com.example.android.popularmovies.BuildConfig;

/**
 * Created by Matthew on 15/03/2018.
 */

public class PopularMoviesPreferences {

    private static final String API_KEY = BuildConfig.API_KEY;

    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String API_BUILDER = "?api_key=";
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";
    private static final String TRAILER_BASE_URL = "https://www.youtube.com/watch?v=";

    private static final String POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";
    private static final String REVIEWS = "/reviews";
    private static final String VIDEOS = "/videos";


    public static String getBaseUrl() {
        return  BASE_URL;
    }

    public static String getApiBuilder() {

        return API_BUILDER;
    }

    public static String getImageBaseUrl() {
        return  IMAGE_BASE_URL;
    }

    public static String getTrailerBaseUrl() {
        return TRAILER_BASE_URL;
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

    public static String getReviews() {

        return REVIEWS;
    }

    public static String getVideos() {
        return VIDEOS;
    }

}
