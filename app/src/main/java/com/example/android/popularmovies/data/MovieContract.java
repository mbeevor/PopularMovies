package com.example.android.popularmovies.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by mbeev on 08/04/2018.
 */

public class MovieContract {

    // constants for content authority
    public static final String CONTENT_AUTHORITY = "com.example.android.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String MOVIE_PATH = "movies";

    // Empty constructor to prevent accidental instantiation
    private MovieContract() {}

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, MOVIE_PATH);

        // MIME type for list of movies
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + MOVIE_PATH;

        // MIME type for single movie
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + MOVIE_PATH;


        // Name of database for movies
        public static final String TABLE_NAME = "movies";

        // unique ID for each item in table
        public static final String _ID = BaseColumns._ID;

        // Database columns
        public static final String MOVIE_TITLE = "movieTitle";
        public static final String POSTER_IMAGE = "posterImage";
        public static final String MOVIE_ID = "movieId";
        public static final String BACKDROP_IMAGE = "backdropImage";
        public static final String MOVIE_OVERVIEW = "movieOverview";
        public static final String MOVIE_RELEASE_DATE = "movieReleaseDate";
        public static final String MOVIE_RATING = "movieRating";

    }
}
