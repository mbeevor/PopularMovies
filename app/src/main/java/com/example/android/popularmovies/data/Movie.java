package com.example.android.popularmovies.data;

/**
 * Created by Matthew on 15/03/2018.
 */

public class Movie {

    private String movieTitle;
    private String posterImage;

    // constructor for creating a movie
    public Movie(String title, String imageUrl) {

        movieTitle = title;
        posterImage = imageUrl;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getPosterImage() {
        return posterImage;
    }

}
