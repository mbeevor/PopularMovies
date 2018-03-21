package com.example.android.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Matthew on 15/03/2018.
 */

public class Movie implements Parcelable {

    private String movieTitle;
    private String posterImage;
    private String backdropImage;
    private String movieOverview;
    private String movieReleaseDate;
    private String movieRating;



    // main constructor for creating a movie
    public Movie(String title, String imageUrl, String backdrop, String overview, String releaseDate, String voteAverage) {

        movieTitle = title;
        posterImage = imageUrl;
        backdropImage = backdrop;
        movieOverview = overview;
        movieReleaseDate = releaseDate;
        movieRating = voteAverage;
    }

    // parcelable constructor
    public Movie(Parcel parcel) {

        movieTitle = parcel.readString();
        posterImage = parcel.readString();
        backdropImage = parcel.readString();
        movieOverview = parcel.readString();
        movieReleaseDate = parcel.readString();
        movieRating = parcel.readString();

    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public String getBackdropImage() {
        return backdropImage;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public String getMovieRating() {
        return movieRating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(movieTitle);
        dest.writeString(posterImage);
        dest.writeString(backdropImage);
        dest.writeString(movieOverview);
        dest.writeString(movieReleaseDate);
        dest.writeString(movieRating);

    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {


        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[0];
        }
    };

}
