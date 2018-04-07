package com.example.android.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Matthew on 23/03/2018.
 */

public class Review implements Parcelable {

    private String reviewAuthor;
    private String reviewContent;


    // main constructor for creating a review
    public Review(String author, String content) {

        reviewAuthor = author;
        reviewContent = content;

    }

    // parcelable constructor
    private Review(Parcel parcel) {

        reviewAuthor = parcel.readString();
        reviewContent = parcel.readString();

    }


    public String getReviewAuthor() {
        return reviewAuthor;
    }

    public String getReviewContent() {
        return reviewContent;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(reviewAuthor);
        dest.writeString(reviewContent);

    }

    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {


        @Override
        public Review createFromParcel(Parcel parcel) {
            return new Review(parcel);
        }

        @Override
        public Review[] newArray(int i) {
            return new Review[0];
        }
    };

}